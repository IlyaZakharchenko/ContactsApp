package com.example.contactsapp.model

import com.example.contactsapp.base.Item

data class ContactModel(
    val id: String?,
    val name: String,
    val phoneNumber: String,
    val imageUrl: String?
): Item