package com.example.contactsapp.contractdetailscreen.ui

import com.example.contactsapp.base.BaseViewModel
import com.example.contactsapp.base.Event
import com.example.contactsapp.contactlistscreen.ContactListScreen
import com.example.contactsapp.contractdetailscreen.DataEvent
import com.example.contactsapp.contractdetailscreen.STATUS
import com.example.contactsapp.contractdetailscreen.UiEvent
import com.example.contactsapp.contractdetailscreen.ViewState
import com.example.contactsapp.contractdetailscreen.data.EditContactInteractor
import com.example.contactsapp.model.ContactModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router

class EditContactViewModel(
    private val contactId: String,
    private val router: Router,
    private val interactor: EditContactInteractor
): BaseViewModel<ViewState>() {
    override fun initialViewState() = ViewState(STATUS.LOAD, null)

    init {
        processDataEvent(DataEvent.RequestContact(contactId))
    }

    override fun reduce(
        event: Event,
        previousState: ViewState
    ): ViewState? {
        when(event) {
            is UiEvent.OnSaveContactClick -> {
                processDataEvent(DataEvent.UpdateContact(event.contact))
            }
            is DataEvent.UpdateContact -> {
                interactor.updateContact(event.contact)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processDataEvent(DataEvent.ContactSaveSuccess)
                        },
                        {
                            processDataEvent(DataEvent.ContactSaveFail)
                        }
                    )
            }
            is DataEvent.RequestContact -> {
                interactor.getContact(contactId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processDataEvent(DataEvent.ContactRequestSuccess(it))
                        },
                        {
                            processDataEvent(DataEvent.ContactRequestFail)
                        }
                    )
            }
            is DataEvent.ContactSaveSuccess -> {
                router.newRootScreen(ContactListScreen())
            }
            is DataEvent.ContactSaveFail -> {
                return previousState.copy(status = STATUS.ERROR)
            }
            is DataEvent.ContactRequestSuccess -> {
                return previousState.copy(status = STATUS.CONTENT, contact = event.contact)
            }
            is DataEvent.ContactRequestFail -> {
                return previousState.copy(status = STATUS.ERROR)
            }
        }
        return null
    }
}