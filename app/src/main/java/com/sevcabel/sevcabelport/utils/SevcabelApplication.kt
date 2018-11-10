package com.sevcabel.sevcabelport.utils

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.sevcabel.sevcabelport.news.News
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk

class SevcabelApplication : Application(), ChildEventListener {


    init {
        instance = this

    }

    companion object {
        private var instance: SevcabelApplication? = null
        private lateinit var userID: String
        private var isAdmin: Boolean = false
        var newsList: MutableList<News> = mutableListOf()


        fun getContext(): Context {
            return instance!!.applicationContext
        }

        fun getUserId(): String {
            return userID
        }

        fun setUserID(userID: String) {
            this.userID = userID
        }

        fun isAdmin(): Boolean {
            return isAdmin
        }

        fun setAdmin(isAdmin: Boolean) {
            this.isAdmin = isAdmin
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
        FirebaseApp.initializeApp(this)
        val database = FirebaseDatabase.getInstance()
        val newsData = database.getReference("news")
        newsData.addChildEventListener(this)

    }


    override fun onCancelled(p0: DatabaseError) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
        SevcabelApplication.newsList.add(dataSnapshot.getValue(News::class.java)!!)
    }

    override fun onChildRemoved(p0: DataSnapshot) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
