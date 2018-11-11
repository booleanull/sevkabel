package com.sevcabel.sevcabelport.maps

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.sevcabel.sevcabelport.R
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import kotlinx.android.synthetic.main.activity_add_marker.*

class AddMarkerActivity : AppCompatActivity() {

    var t: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_marker)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        radioGroup.check(R.id.event)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.event -> t = 0
                R.id.food -> t = 1
            }
        }

        button.setOnClickListener {
            if (((etTitle.text.toString().length > etTitle.minCharacters) && (etTitle.text.toString().length < etTitle.maxCharacters))
                    || (etText.text.toString().length > etText.minCharacters) && (etText.text.toString().length < etText.maxCharacters)) {

                var id = 0

                try {
                    id = SevcabelApplication.getMarkers().get(SevcabelApplication.getMarkers().size - 1).id + 1
                } catch (e: Exception) {

                }

                val database = FirebaseDatabase.getInstance()
                val rep = database.reference.child("markers")

                val myMarker = MyMarker(id, etTitle.text.toString(), etText.text.toString(), t, 59.924331, 30.241246)
                rep.child(myMarker.id.toString()).setValue(myMarker)
                finish()
            } else
                Toast.makeText(this, getString(R.string.valid), Toast.LENGTH_LONG).show()
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
