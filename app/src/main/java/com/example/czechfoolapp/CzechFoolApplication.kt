package com.example.czechfoolapp

import android.app.Application
import com.example.czechfoolapp.di.AppContainer
import com.example.czechfoolapp.di.DefaultAppContainer

class CzechFoolApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}