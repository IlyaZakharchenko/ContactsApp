package com.example.contactsapp.contractdetailscreen

import com.example.contactsapp.contractdetailscreen.ui.ContactDetailFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class AddContactScreen : SupportAppScreen() {
    override fun getFragment() = ContactDetailFragment.newInstance()
}

class EditContactScreen(private val id: String) : SupportAppScreen() {
    override fun getFragment() = ContactDetailFragment.newInstance(id)
}