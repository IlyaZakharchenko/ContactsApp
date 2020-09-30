package com.example.contactsapp.data.local

import androidx.room.*
import com.example.contactsapp.CONTACT_TABLE
import io.reactivex.Single

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(entity: ContactEntity): Single<Unit>

    @Query("SELECT * FROM $CONTACT_TABLE")
    fun read(): Single<List<ContactEntity>>

    @Query("SELECT * FROM $CONTACT_TABLE WHERE id = :id")
    fun getContactById(id: String): Single<ContactEntity>

    @Update
    fun update(entity: ContactEntity): Single<Unit>

    @Delete
    fun delete(entity: ContactEntity): Single<Unit>
}