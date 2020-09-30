package com.example.contactsapp.data

import com.example.contactsapp.data.local.ContactEntity
import com.example.contactsapp.model.ContactModel
import java.util.*

fun ContactEntity.mapToModel() = ContactModel(id, name, phoneNumber, imageUrl)

fun ContactModel.mapToEntity() = ContactEntity(id ?: UUID.randomUUID().toString(), name, phoneNumber, imageUrl)