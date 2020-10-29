package com.android.cleanRetrofit.presentation

import android.app.Application
import com.android.cleanRetrofit.presentation.di.AppComponent
import com.android.cleanRetrofit.presentation.di.DaggerAppComponent


class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .build()
    }
}