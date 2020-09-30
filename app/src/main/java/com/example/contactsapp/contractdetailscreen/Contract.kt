package com.example.contactsapp.contractdetailscreen

import com.example.contactsapp.base.Event
import com.example.contactsapp.model.ContactModel

data class ViewState(
    val status: STATUS,
    val contact: ContactModel?
)

sealed class UiEvent : Event {
    data class OnSaveContactClick(val contact: ContactModel) : UiEvent()
}

sealed class DataEvent : Event {
    data class RequestContact(val id: String) : DataEvent()
    data class SaveContact(val contact: ContactModel) : DataEvent()
    data class UpdateContact(val contact: ContactModel) : DataEvent()
    object ContactSaveSuccess : DataEvent()
    object ContactSaveFail : DataEvent()
    data class ContactRequestSuccess(val contact: ContactModel) : DataEvent()
    object ContactRequestFail : DataEvent()

}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}