package com.alexteddy.bookshelf

import android.app.Application
import com.alexteddy.bookshelf.data.AppContainer
import com.alexteddy.bookshelf.data.DefaultAppContainer

class BooksApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}