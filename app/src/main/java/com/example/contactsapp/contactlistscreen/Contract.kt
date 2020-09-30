package com.example.contactsapp.contactlistscreen

import com.example.contactsapp.base.Event
import com.example.contactsapp.model.ContactModel

data class ViewState(
    val status: STATUS,
    val contactList: List<ContactModel>
)

sealed class UiEvent() : Event {
    data class OnContactClick(val index: Int) : UiEvent()
    object OnAddContactClick : UiEvent()
}

sealed class DataEvent() : Event {
    object RequestContacts : DataEvent()
    data class SuccessContactsRequest(val contactList: List<ContactModel>) : DataEvent()
    object ErrorContactsRequest : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}