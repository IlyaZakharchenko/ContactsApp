package com.example.contactsapp.contactlistscreen

import com.example.contactsapp.contactlistscreen.ui.ContactListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ContactListScreen : SupportAppScreen() {
    override fun getFragment() = ContactListFragment.newInstance()
}