package com.example.contactsapp.data

import com.example.contactsapp.data.local.ContactDao
import com.example.contactsapp.data.local.ContactEntity
import com.example.contactsapp.model.ContactModel
import io.reactivex.Single

class ContactRepositoryImpl(private val dao: ContactDao) : ContactRepository {
    override fun saveContact(model: ContactModel) = dao.create(model.mapToEntity())

    override fun updateContact(model: ContactModel) = dao.update(model.mapToEntity())

    override fun deleteContact(model: ContactModel) = dao.delete(model.mapToEntity())

    override fun getAllContacts(): Single<List<ContactModel>> =
        dao.read()
            .map {
                it.map(ContactEntity::mapToModel)
            }

    override fun getContactById(id: String) = dao.getContactById(id).map(ContactEntity::mapToModel)
}