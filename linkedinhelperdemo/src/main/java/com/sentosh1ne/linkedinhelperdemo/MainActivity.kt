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

    private val clientId = "777v6fv2ixsrmf"
    private val clientSecret = "vBOzh84VWgThhIA0"
    private val redirectUri = "https://google.com"

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
                val result = apiHelper.getUserProfileRaw(
                        Fields.BasicProfile.FIRST_NAME,
                        Fields.BasicProfile.LAST_NAME
                )

                Log.d(this::class.java.simpleName, "Profiile json = ${result.toString()}")
            }

        }
    }
}
