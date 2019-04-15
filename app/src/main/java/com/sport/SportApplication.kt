package com.sport

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import timber.log.Timber

class SportApplication : Application() {

    var m = 0;

    override fun onCreate() {
        super.onCreate()
        context = this

        /**
         * 仅在Debug时初始化Timber
         */
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}