package com.example.czechfoolapp

import android.app.Application
import com.example.czechfoolapp.data.AppContainer
import com.example.czechfoolapp.data.DefaultAppContainer

class CzechFoolApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}