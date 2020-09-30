package com.example.contactsapp.contractdetailscreen.di

import com.example.contactsapp.CONTACT_APP_QUALIFIER
import com.example.contactsapp.base.BaseViewModel
import com.example.contactsapp.contractdetailscreen.ViewState
import com.example.contactsapp.contractdetailscreen.data.AddContactInteractor
import com.example.contactsapp.contractdetailscreen.data.EditContactInteractor
import com.example.contactsapp.contractdetailscreen.ui.AddContactViewModel
import com.example.contactsapp.contractdetailscreen.ui.EditContactViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


const val EDIT_CONTACT_VIEW_MODEL = "EDIT_CONTACT_VIEW_MODEL"
const val ADD_CONTACT_VIEW_MODEL = "ADD_CONTACT_VIEW_MODEL"
val contactDetailModule = module {
    single<AddContactInteractor> {
        AddContactInteractor(get())
    }

    single<EditContactInteractor> {
        EditContactInteractor(get())
    }

    viewModel<BaseViewModel<ViewState>>(named(EDIT_CONTACT_VIEW_MODEL)) { (contactId: String) ->
        EditContactViewModel(contactId, get(named(CONTACT_APP_QUALIFIER)), get())
    }

    viewModel<BaseViewModel<ViewState>>(named(ADD_CONTACT_VIEW_MODEL)) {
        AddContactViewModel(get(named(CONTACT_APP_QUALIFIER)), get())
    }
}