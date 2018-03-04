package com.pandulapeter.khameleon

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.google.firebase.database.FirebaseDatabase
import com.pandulapeter.khameleon.injection.calendarModule
import com.pandulapeter.khameleon.injection.chatModule
import com.pandulapeter.khameleon.injection.songsModule
import com.pandulapeter.khameleon.injection.userModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

class KhameleonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        startKoin(this, listOf(userModule, chatModule, calendarModule, songsModule))
    }
}