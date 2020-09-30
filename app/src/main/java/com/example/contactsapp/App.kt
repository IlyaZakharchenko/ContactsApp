package com.example.contactsapp

import android.app.Application
import com.example.contactsapp.contactlistscreen.di.contactListModule
import com.example.contactsapp.contractdetailscreen.di.contactDetailModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(navModule, dataModule, contactListModule, contactDetailModule)
        }
    }
}