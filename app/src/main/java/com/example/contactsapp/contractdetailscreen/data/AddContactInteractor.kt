package com.example.contactsapp.contractdetailscreen.data

import com.example.contactsapp.data.ContactRepository
import com.example.contactsapp.model.ContactModel

class AddContactInteractor(private val repository: ContactRepository) {

    fun createContact(model: ContactModel) = repository.saveContact(model)
}