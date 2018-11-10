package com.sevcabel.sevcabelport.maps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.sevcabel.sevcabelport.R
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import com.sevcabel.sevcabelport.utils.getString
import kotlinx.android.synthetic.main.activity_marker.*

class MarkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if(SevcabelApplication.getMyMarker().type == 0)
            supportActionBar!!.title = R.string.event.getString()
        else
            supportActionBar!!.title = R.string.food.getString()

        tvText.text = SevcabelApplication.getMyMarker().text
        tvTitle.text = SevcabelApplication.getMyMarker().title

        floatingActionButton.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val rep = database.reference.child("markers")
            rep.child(SevcabelApplication.getMyMarker().id.toString()).removeValue()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                this.finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}
