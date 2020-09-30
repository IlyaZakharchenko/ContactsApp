package com.example.contactsapp.contractdetailscreen.data

import com.example.contactsapp.data.ContactRepository
import com.example.contactsapp.model.ContactModel

class EditContactInteractor(private val repository: ContactRepository) {

    fun updateContact(model: ContactModel) = repository.updateContact(model)

    fun getContact(id: String) = repository.getContactById(id)
}