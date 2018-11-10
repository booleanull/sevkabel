package com.sevcabel.sevcabelport.utils

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.sevcabel.sevcabelport.R
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

        lateinit var newsData: DatabaseReference
        lateinit var database: FirebaseDatabase
        private val markers: MutableList<MyMarker> = mutableListOf()
        private val marks: MutableList<Marker> = mutableListOf()
        private var admin: Boolean = false
        private lateinit var myMarker: MyMarker
        private lateinit var map: GoogleMap

        fun getContext(): Context {
            return instance!!.applicationContext
        }

        fun getMap(): GoogleMap {
            return map
        }

        fun setMap(map: GoogleMap) {
            this.map = map
        }

        fun getMarkers(): MutableList<MyMarker> {
            return markers
        }

        fun getMarks(): MutableList<Marker> {
            return marks
        }

        fun getAdmin(): Boolean {
            return admin
        }

        fun setAdmin(admin: Boolean) {
            this.admin = admin
        }

        fun getMyMarker(): MyMarker {
            return myMarker
        }

        fun setMyMarker(myMarker: MyMarker) {
            this.myMarker = myMarker
        }

        fun getUserId(): String {
            return userID
        }

        fun setUserID(userID: String) {
            this.userID = userID
        }

        fun updateMarkers() {
            map.clear()
            var ja: Int = 0
            for (i in markers) {
                var options = MarkerOptions().draggable(false).position(LatLng(i.markerPositionX, i.markerPositionY))
                var b : Bitmap
                if (i.type == 0) {
                    b = BitmapFactory.decodeResource(getContext().resources, R.drawable.eve)
                }
                else {
                    b = BitmapFactory.decodeResource(getContext().resources, R.drawable.fc)
                }
                b = Bitmap.createScaledBitmap(b, 96, 96, true)
                options.icon(BitmapDescriptorFactory.fromBitmap(b))
                marks[ja] = map.addMarker(options)
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
        database = FirebaseDatabase.getInstance()
        newsData = database.getReference("news")
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
