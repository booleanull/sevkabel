package com.sevcabel.sevcabelport.utils

import android.app.Application
import android.content.Context
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk

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

    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                // VKAccessToken is invalid
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(this)
    }
}
