package com.example.contactsapp.contractdetailscreen.ui

import com.example.contactsapp.base.BaseViewModel
import com.example.contactsapp.base.Event
import com.example.contactsapp.contactlistscreen.ContactListScreen
import com.example.contactsapp.contractdetailscreen.DataEvent
import com.example.contactsapp.contractdetailscreen.STATUS
import com.example.contactsapp.contractdetailscreen.UiEvent
import com.example.contactsapp.contractdetailscreen.ViewState
import com.example.contactsapp.contractdetailscreen.data.AddContactInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router

class AddContactViewModel(
    private val router: Router,
    private val interactor: AddContactInteractor
) : BaseViewModel<ViewState>() {
    override fun initialViewState() = ViewState(STATUS.CONTENT, null)

    override fun reduce(
        event: Event,
        previousState: ViewState
    ): ViewState? {
        when (event) {
            is UiEvent.OnSaveContactClick -> {
                processDataEvent(DataEvent.SaveContact(event.contact))
            }
            is DataEvent.SaveContact -> {
                interactor.createContact(event.contact)
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
            is DataEvent.ContactSaveSuccess -> {
                router.newRootScreen(ContactListScreen())
            }
            is DataEvent.ContactSaveFail -> {
                return previousState.copy(status = STATUS.ERROR)
            }
        }
        return null
    }
}