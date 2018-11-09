package com.sevcabel.sevcabelport.utils

import android.app.Application
import android.content.Context

class SevcabelApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: SevcabelApplication? = null

        fun getContext(): Context {
            return instance!!.applicationContext
        }
    }
}
