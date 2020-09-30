package com.example.contactsapp

import androidx.room.Room
import com.example.contactsapp.data.ContactRepository
import com.example.contactsapp.data.ContactRepositoryImpl
import com.example.contactsapp.data.local.ContactDao
import com.example.contactsapp.data.local.ContactDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

const val CONTACT_APP_QUALIFIER = "CONTACT_APP_QUALIFIER"
val navModule = module {
    single<Cicerone<Router>>(named(CONTACT_APP_QUALIFIER)) {
        Cicerone.create()
    }

    single<Router>(named(CONTACT_APP_QUALIFIER)) {
        get<Cicerone<Router>>(named(CONTACT_APP_QUALIFIER)).router
    }

    single<NavigatorHolder>(named(CONTACT_APP_QUALIFIER)) {
        get<Cicerone<Router>>(named(CONTACT_APP_QUALIFIER)).navigatorHolder
    }
}

const val CONTACT_TABLE = "CONTACT_TABLE"
val dataModule = module {
    single<ContactDatabase> {
        Room
            .databaseBuilder(androidContext(), ContactDatabase::class.java, CONTACT_TABLE)
            .build()
    }

    single<ContactDao> {
        get<ContactDatabase>().contactDao()
    }

    single<ContactRepository> {
        ContactRepositoryImpl(get())
    }
}