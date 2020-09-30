package com.example.contactsapp.data

import com.example.contactsapp.model.ContactModel
import io.reactivex.Single

interface ContactRepository {
    fun saveContact(model: ContactModel): Single<Unit>

    fun updateContact(model: ContactModel): Single<Unit>

    fun deleteContact(model: ContactModel): Single<Unit>

    fun getAllContacts(): Single<List<ContactModel>>

    fun getContactById(id: String): Single<ContactModel>
}