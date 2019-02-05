package com.sentosh1ne.linkedinhelperdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sentosh1ne.linkedinapihelperv2.data.session.AppConfig
import com.sentosh1ne.linkedinapihelperv2.data.session.LinkedinApiHelper
import com.sentosh1ne.linkedinapihelperv2.entities.PermissionsScope
import com.sentosh1ne.linkedinapihelperv2.entities.Scopes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val clientId = "777v6fv2ixsrmf"
    private val clientSecret = "vBOzh84VWgThhIA0"
    private val redirectUri = "https://google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin.setOnClickListener {
            LinkedinApiHelper(this).login(
                    PermissionsScope(Scopes.R_BASICPROFILE),
                    AppConfig(clientId, clientSecret, redirectUri)
            )
        }
    }
}
