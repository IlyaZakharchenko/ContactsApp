package com.example.contactsapp.contactlistscreen.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsapp.R
import com.example.contactsapp.contactlistscreen.STATUS
import com.example.contactsapp.contactlistscreen.UiEvent
import com.example.contactsapp.contactlistscreen.ViewState
import com.example.contactsapp.setData
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.fragment_contact_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactListFragment : Fragment(R.layout.fragment_contact_list) {

    private val viewModel: ContactListViewModel by viewModel()

    private val adapter = ListDelegationAdapter(
        contactAdapterDelegate {
            viewModel.processUiEvent(UiEvent.OnContactClick(it))
        }
    )

    companion object {
        fun newInstance() = ContactListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        rvContacts.layoutManager = LinearLayoutManager(requireContext())
        rvContacts.adapter = adapter

        btnAddContact.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnAddContactClick)
        }
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.LOAD -> {
            }
            STATUS.CONTENT -> {
                adapter.setData(viewState.contactList)
            }
            STATUS.ERROR -> {
                Toast.makeText(requireContext(), "Error loading contacts", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}