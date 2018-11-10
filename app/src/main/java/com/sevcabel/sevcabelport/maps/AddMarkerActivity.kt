package com.sevcabel.sevcabelport.maps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sevcabel.sevcabelport.R
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import kotlinx.android.synthetic.main.activity_add_marker.*

class AddMarkerActivity : AppCompatActivity() {

    var t : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_marker)

        radioGroup.check(R.id.event)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.event -> t = 0
                R.id.food -> t = 1
            }
        }

        button.setOnClickListener {
            val marker : Marker = SevcabelApplication.getMap().addMarker(MarkerOptions().position(LatLng(59.924331, 30.241246)).draggable(SevcabelApplication.getAdmin()))
            SevcabelApplication.getMarkers().add(MyMarker(0, etTitle.text.toString(), etText.text.toString(), t, marker))
            finish()
        }
    }
}
