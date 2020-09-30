package com.example.contactsapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.contactsapp.CONTACT_TABLE

@Entity(tableName = CONTACT_TABLE)
data class ContactEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?
)