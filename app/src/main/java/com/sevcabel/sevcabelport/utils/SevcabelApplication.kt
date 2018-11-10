package com.sevcabel.sevcabelport.utils

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.sevcabel.sevcabelport.maps.MyMarker
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
        var newsList: MutableList<News> = mutableListOf()
        private val markers : MutableList<MyMarker> = mutableListOf()
        private val marks : MutableList<Marker> = mutableListOf()
        private var admin : Boolean = false
        private lateinit var myMarker : MyMarker
        private lateinit var map : GoogleMap

        fun getContext(): Context {
            return instance!!.applicationContext
        }

        fun getMap() : GoogleMap {
            return map
        }

        fun setMap(map: GoogleMap) {
            this.map = map
        }

        fun getMarkers() : MutableList<MyMarker> {
            return markers
        }

        fun getMarks() : MutableList<Marker> {
            return marks
        }

        fun getAdmin() : Boolean {
            return admin
        }

        fun setAdmin(admin: Boolean) {
            this.admin = admin
        }

        fun getMyMarker() : MyMarker {
            return myMarker
        }

        fun setMyMarker(myMarker: MyMarker) {
            this.myMarker = myMarker
        }


        fun getUserId(): String{
            return userID
        }

        fun setUserID(userID: String) {
            this.userID = userID
        }

        fun updateMarkers() {
            map.clear()
            var ja : Int = 0
            for(i in markers) {
                marks[ja] = map.addMarker(MarkerOptions().draggable(false).position(LatLng(i.markerPositionX, i.markerPositionY)))
                ja++
            }
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
