package com.sentosh1ne.linkedinhelperdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.sentosh1ne.linkedinapihelperv2.data.session.AppConfig
import com.sentosh1ne.linkedinapihelperv2.data.session.LinkedinApiHelper
import com.sentosh1ne.linkedinapihelperv2.entities.Fields
import com.sentosh1ne.linkedinapihelperv2.entities.PermissionsScope
import com.sentosh1ne.linkedinapihelperv2.entities.Scopes
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val clientId = "86rmods9ln6yj5"
    private val clientSecret = "VWUjjkx7yEQX2ceO"
    private val redirectUri = "http://www.omivoyage.com"

    private lateinit var apiHelper: LinkedinApiHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiHelper = LinkedinApiHelper(this)
        btnLogin.setOnClickListener {
            apiHelper.login(
                    PermissionsScope(Scopes.R_LITEPROFILE),
                    AppConfig(clientId, clientSecret, redirectUri)
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LinkedinApiHelper.ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.d(this::class.java.simpleName, "Successful login with token ${data?.getStringExtra("access_token")}")
            getUserProfileRaw()
        }
    }

    private fun getUserProfileRaw() {
        runBlocking {
            withContext(Dispatchers.Default) {
                val result = apiHelper.getUserProfilePretty(
                        Fields.LiteProfile.FIRST_NAME,
                        Fields.LiteProfile.LAST_NAME,
                        Fields.LiteProfile.PROFILE_PICTURE
                )

                Log.d(this::class.java.simpleName, "Profile json = ${result.toString()}")
            }

        }
    }
}
