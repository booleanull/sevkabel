package com.sevcabel.sevcabelport

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.database.*
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.util.VKUtil
import kotlinx.android.synthetic.main.activity_auth.*
import java.util.*
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKApiUser
import com.vk.sdk.api.model.VKList
import org.json.JSONArray
import org.json.JSONObject


const val TAG: String = "AuthActivity"

class AuthActivity : AppCompatActivity() {

    private var scope: Array<String> = arrayOf(
            VKScope.EMAIL,
            VKScope.PHOTOS
    )
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val userReference: DatabaseReference = database.getReference("user")
    lateinit var signInButton: Button
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val fingerprints: Array<String> = VKUtil.getCertificateFingerprint(this, this.packageName)
        Log.d(TAG, Arrays.toString(fingerprints))

        progressBar = this.progress_bar
        signInButton = this.sign_in_button
        signInButton.setOnClickListener { _ ->
            VKSdk.login(this, *scope)
        }

    }

    override fun onStart() {
        super.onStart()
        if (VKSdk.isLoggedIn()) {
            progressBar.visibility = View.VISIBLE
            val userID: String = VKSdk.getAccessToken().userId
            signInButton.visibility = View.GONE

            SevcabelApplication.setUserID(userID)
            startActivityWithIsAdminIntent(userID)
        }

    }
    fun writeVKInfoToDatabase() {
        val email: String = VKSdk.getAccessToken().email
        val userID: String = VKSdk.getAccessToken().userId
        SevcabelApplication.setUserID(userID)
        VKApi.users().get().executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                val user = (response!!.parsedModel as VKList<VKApiUser>)[0]
                userReference.child(userID).child("email").setValue(email)
                userReference.child(userID).child("surname").setValue(user.first_name)
                userReference.child(userID).child("lastname").setValue(user.last_name)
            }
        })

        val params = VKParameters()
        params[VKApiConst.FIELDS] = "photo_max_orig"

        val request = VKRequest("users.get", params)
        request.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                super.onComplete(response)

                val resp: JSONArray = response.json.getJSONArray("response")
                val user: JSONObject = resp.getJSONObject(0)
                val photoMaxOrigUrl: String = user.getString("photo_max_orig")
                userReference.child(userID).child("photo").setValue(photoMaxOrigUrl)
            }
        })
    }

    fun startActivityWithIsAdminIntent (userID: String){
        var isAdmin: Boolean

        userReference!!.addListenerForSingleValueEvent( object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    isAdmin = try {
                        dataSnapshot.child(userID).child("admin")
                                .getValue(String::class.java).equals("admin")
                    }catch(e: NullPointerException){
                        false
                    }
                    Log.d(TAG, "onDataChange $isAdmin")
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    SevcabelApplication.setAdmin(isAdmin)
                    progressBar.visibility = View.GONE
                    startActivity(intent)
                    finish()

                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        writeVKInfoToDatabase()
                        startActivityWithIsAdminIntent(SevcabelApplication.getUserId())
                    }

                    override fun onError(error: VKError) {
                        Toast.makeText(applicationContext, "Ошибка", Toast.LENGTH_LONG).show()
                    }
                })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}
