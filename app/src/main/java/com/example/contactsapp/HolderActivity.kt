package com.example.contactsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactsapp.contactlistscreen.ContactListScreen
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class HolderActivity : AppCompatActivity() {
    private val router: Router by inject(named(CONTACT_APP_QUALIFIER))
    private val navigatorHolder: NavigatorHolder by inject(named(CONTACT_APP_QUALIFIER))
    private val navigator =
        SupportAppNavigator(this, supportFragmentManager, R.id.fragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holder)
        router.navigateTo(ContactListScreen())
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}