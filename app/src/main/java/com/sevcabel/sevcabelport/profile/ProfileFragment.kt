package com.sevcabel.sevcabelport.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import com.sevcabel.sevcabelport.R
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

const val TAG: String = "ProfileFragment"

class ProfileFragment : Fragment() {
    val userID: String = SevcabelApplication.getUserId()
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    var surname1: String? = null
    var lastname1: String? = null
    var url: String? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setUserInfo()
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Picasso.get()
                .load(url)
                .into(avatar_image)
        name_text.text = ("$surname1 $lastname1")
    }

    private fun setUserInfo() {
        database = FirebaseDatabase.getInstance()
        myRef = database.reference
        myRef!!.addListenerForSingleValueEvent(object : ValueEventListener {


            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, userID)
                    url = dataSnapshot.child("photo").getValue(String::class.java)
                    surname1 = dataSnapshot.child("surname").getValue(String::class.java)
                    lastname1 = dataSnapshot.child("lastname").getValue(String::class.java)
                    Picasso.get()
                            .load(url)
                            .into(avatar_image)
                    name_text.text = ("$surname1 $lastname1")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
            }
        })

        return

    }
}
