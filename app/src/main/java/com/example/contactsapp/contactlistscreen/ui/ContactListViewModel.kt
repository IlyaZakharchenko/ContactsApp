package com.example.contactsapp.contactlistscreen.ui

import com.example.contactsapp.base.BaseViewModel
import com.example.contactsapp.base.Event
import com.example.contactsapp.contactlistscreen.DataEvent
import com.example.contactsapp.contactlistscreen.STATUS
import com.example.contactsapp.contactlistscreen.UiEvent
import com.example.contactsapp.contactlistscreen.ViewState
import com.example.contactsapp.contactlistscreen.data.ContactListInteractor
import com.example.contactsapp.contractdetailscreen.AddContactScreen
import com.example.contactsapp.contractdetailscreen.EditContactScreen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router

class ContactListViewModel(
    private val router: Router,
    private val interactor: ContactListInteractor
) : BaseViewModel<ViewState>() {

    init {
        processDataEvent(DataEvent.RequestContacts)
    }

    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, emptyList())

    override fun reduce(
        event: Event,
        previousState: ViewState
    ): ViewState? {
        when (event) {
            is UiEvent.OnContactClick -> {
                val contactId = previousState.contactList[event.index].id
                contactId?.let { router.navigateTo(EditContactScreen(it)) }
            }
            is UiEvent.OnAddContactClick -> {
                router.navigateTo(AddContactScreen())
            }
            is DataEvent.RequestContacts -> {
                interactor.getAllContacts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processDataEvent(DataEvent.SuccessContactsRequest(it))
                        },
                        {
                            processDataEvent(DataEvent.ErrorContactsRequest)
                        }
                    )
            }
            is DataEvent.SuccessContactsRequest ->
                return previousState.copy(
                    status = STATUS.CONTENT,
                    contactList = event.contactList
                )
            is DataEvent.ErrorContactsRequest ->
                return previousState.copy(
                    status = STATUS.ERROR
                )
        }
        return null
    }
}