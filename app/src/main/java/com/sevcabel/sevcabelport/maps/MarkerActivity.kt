package com.sevcabel.sevcabelport.maps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sevcabel.sevcabelport.R
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import kotlinx.android.synthetic.main.activity_marker.*

class MarkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)

        tvTitle.text = SevcabelApplication.getMyMarker().title
        tvText.text = SevcabelApplication.getMyMarker().text
        tvType.text = SevcabelApplication.getMyMarker().type.toString()
    }
}
