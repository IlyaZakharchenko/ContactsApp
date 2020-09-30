package com.example.contactsapp.contactlistscreen.data

import com.example.contactsapp.data.ContactRepository

class ContactListInteractor(private val repository: ContactRepository) {

    fun getAllContacts() = repository.getAllContacts()
}