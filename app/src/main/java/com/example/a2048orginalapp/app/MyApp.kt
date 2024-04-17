package com.example.a2048orginalapp.app

import android.app.Application
import com.example.a2048orginalapp.domain.AppRepositoryImpl

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppRepositoryImpl.init(this)

    }
}