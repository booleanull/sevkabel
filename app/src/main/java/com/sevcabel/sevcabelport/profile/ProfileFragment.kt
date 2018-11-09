package com.sevcabel.sevcabelport.profile

import android.net.Uri
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.firebase.database.*
import com.sevcabel.sevcabelport.R
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import java.net.URL

const val TAG:String = "ProfileFragment"

class ProfileFragment : Fragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val avatarView: ImageView = avatar_image
        setAvatar(avatarView)
    }

    private fun setAvatar(avatarView: ImageView){
        database = FirebaseDatabase.getInstance()
        myRef = database.reference
        myRef!!.addListenerForSingleValueEvent( object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userID: String = SevcabelApplication.getUserId()
                    Log.d(TAG, userID)
                    val urlString = dataSnapshot.child("user").child(userID).child("photo").getValue(String::class.java)
                    Log.d(TAG, urlString)
                    Picasso.get()
                            .load(Uri.parse(urlString))
                            .into(avatarView)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
            }
        })

    }
}
