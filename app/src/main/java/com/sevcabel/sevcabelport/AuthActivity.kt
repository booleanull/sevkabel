package com.sevcabel.sevcabelport

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.*
import com.vk.sdk.util.VKUtil
import kotlinx.android.synthetic.main.activity_auth.*
import java.util.*
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.VKRequest.VKRequestListener





const val TAG:String = "AuthActivity"

class AuthActivity : AppCompatActivity() {

    private var scope: Array<String> = arrayOf(
            VKScope.EMAIL,
            VKScope.PHOTOS
    )
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val fingerprints: Array<String> = VKUtil.getCertificateFingerprint(this, this.packageName)
        Log.d(TAG, Arrays.toString(fingerprints))

        val signInButton: Button = this.sign_in_button
        signInButton.setOnClickListener{_ ->
            VKSdk.login(this, *scope)
        }

    }

    fun writeVKInfoToDatabase(){


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        database = FirebaseDatabase.getInstance()
                        myRef = database.reference
                        val request = VKApi.users().get()
                        request.executeWithListener(object : VKRequestListener() {
                            override fun onComplete(response: VKResponse?) {
                                val names: String = request.parseModel.toString()
                                myRef.child("users").child("us").setValue(names)

                            }

                            override fun onError(error: VKError?) {
                                //Do error stuff
                            }

                            override fun attemptFailed(request: VKRequest?, attemptNumber: Int, totalAttempts: Int) {
                                //I don't really believe in progress
                            }
                        })

                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                    override fun onError(error: VKError) {
                        Toast.makeText(applicationContext, "Ошибка", Toast.LENGTH_LONG).show()
                    }
                })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}
