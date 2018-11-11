package com.sevcabel.sevcabelport.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.sevcabel.sevcabelport.R
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        button.setOnClickListener {
            Toast.makeText(this, getString(R.string.req), Toast.LENGTH_LONG).show()
            finish()
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
