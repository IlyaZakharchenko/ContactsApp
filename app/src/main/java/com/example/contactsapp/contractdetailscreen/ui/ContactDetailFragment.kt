package com.example.contactsapp.contractdetailscreen.ui

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.contactsapp.R
import com.example.contactsapp.base.BaseViewModel
import com.example.contactsapp.contractdetailscreen.STATUS
import com.example.contactsapp.contractdetailscreen.UiEvent
import com.example.contactsapp.contractdetailscreen.ViewState
import com.example.contactsapp.contractdetailscreen.di.ADD_CONTACT_VIEW_MODEL
import com.example.contactsapp.contractdetailscreen.di.EDIT_CONTACT_VIEW_MODEL
import com.example.contactsapp.model.ContactModel
import kotlinx.android.synthetic.main.fragment_contact_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ContactDetailFragment : Fragment(R.layout.fragment_contact_detail) {

    private var currentImageUrl: String? = null

    private val viewModel: BaseViewModel<ViewState> by viewModel(named(qualifier)) {
        parametersOf(arguments?.getString(CONTACT_ID_KEY))
    }

    companion object {
        private lateinit var qualifier: String
        private const val CONTACT_ID_KEY = "CONTACT_ID_KEY"
        private const val GALLERY_REQUEST_CODE = 100
        private const val CAMERA_REQUEST_CODE = 110
        private const val CAMERA_PERMISSION_REQUEST_CODE = 111

        fun newInstance(): ContactDetailFragment {
            qualifier = ADD_CONTACT_VIEW_MODEL
            return ContactDetailFragment()
        }

        fun newInstance(id: String): ContactDetailFragment {
            qualifier = EDIT_CONTACT_VIEW_MODEL
            val fragment = ContactDetailFragment()
            val args = Bundle()
            args.putString(CONTACT_ID_KEY, id)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        btnSave.setOnClickListener {
            val contact = ContactModel(
                arguments?.getString(CONTACT_ID_KEY),
                etName.text.toString(),
                etPhoneNumber.text.toString(),
                currentImageUrl
            )
            viewModel.processUiEvent(UiEvent.OnSaveContactClick(contact))
        }

        ivAvatar.setOnClickListener {
            openImageDialog()
        }
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.LOAD -> {
            }
            STATUS.CONTENT -> {
                viewState.contact?.apply {
                    etName.setText(name, TextView.BufferType.EDITABLE)
                    etPhoneNumber.setText(phoneNumber, TextView.BufferType.EDITABLE)
                    currentImageUrl = imageUrl
                }
                setImage()
            }
            STATUS.ERROR -> {
                Toast.makeText(requireContext(), "Error saving contact", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setImage() {
        Glide.with(this)
            .load(
                currentImageUrl ?: ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_empty_avatar,
                    requireActivity().theme
                )
            )
            .circleCrop()
            .into(ivAvatar)
    }

    private fun openImageDialog() {
        val items = arrayOf("Camera", "Gallery", "Cancel")
        val title = "Choose image from:"
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setItems(items) { dialogInterface, index ->
                when (index) {
                    0 -> {
                        checkCameraPermission()
                    }
                    1 -> {
                        val galleryIntent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
                    }
                    2 -> {
                        dialogInterface.dismiss()
                    }
                }
            }
            .create()
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    currentImageUrl = data?.data.toString()
                    setImage()
                }
                CAMERA_REQUEST_CODE -> {
                    setImage()
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                val photoFile: File? = createImageFile()
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
                }
            }
        }
    }

    private fun checkCameraPermission() {
        when (ContextCompat.checkSelfPermission(requireContext(), CAMERA)) {
            PERMISSION_GRANTED -> dispatchTakePictureIntent()
            else -> ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentImageUrl = absolutePath
            if (!exists()) {
                createNewFile()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                Toast
                    .makeText(
                        requireContext(),
                        "Camera permission required to take photo",
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
        }
    }
}