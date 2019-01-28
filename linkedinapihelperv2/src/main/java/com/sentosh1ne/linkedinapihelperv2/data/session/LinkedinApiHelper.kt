package com.sentosh1ne.linkedinapihelperv2.data.session

import android.app.Activity
import android.content.Intent
import com.sentosh1ne.linkedinapihelperv2.ui.LinkedInAuthActivity

object LinkedinApiHelper {
    private const val REQUEST_CODE = 444555

    fun init() {

    }

    fun login(activity: Activity, scope: String, appConfig: AppConfig) {
        val intent = Intent(activity, LinkedInAuthActivity::class.java)
        intent.putExtra("scope", scope)
        intent.putExtra("appConfig", appConfig)
        activity.startActivityForResult(intent, REQUEST_CODE)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    }
}


