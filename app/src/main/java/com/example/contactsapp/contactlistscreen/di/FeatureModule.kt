package com.example.contactsapp.contactlistscreen.di

import com.example.contactsapp.CONTACT_APP_QUALIFIER
import com.example.contactsapp.contactlistscreen.data.ContactListInteractor
import com.example.contactsapp.contactlistscreen.ui.ContactListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val contactListModule = module {
    single<ContactListInteractor> {
        ContactListInteractor(get())
    }

    viewModel<ContactListViewModel> {
        ContactListViewModel(get(named(CONTACT_APP_QUALIFIER)), get())
    }
}