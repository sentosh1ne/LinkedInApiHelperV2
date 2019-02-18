# LinkedInApiHelperV2
Android library for LinkedIn version 2 of the API. (Created due to deprecation of current SDK) Work in Progress

## Installation
TBD
## Usage
```kotlin
class MainActivity : AppCompatActivity() {

    private val clientId = "your_client_id"
    private val clientSecret = "your_client_secret"
    //some redirect uri used to fetch the code required for accessToken request
    // more information can be found at LinkedIn API Documentation
    private val redirectUri = "https://google.com"

    private lateinit var apiHelper: LinkedinApiHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiHelper = LinkedinApiHelper(this)
        btnLogin.setOnClickListener {
            apiHelper.login(
                    PermissionsScope(Scopes.R_EMAILADDRESS),
                    AppConfig(clientId, clientSecret, redirectUri)
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LinkedinApiHelper.ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.d("ApiHelper", "Successful login with token ${data?.getStringExtra("access_token")}")
            apiHelper.getUserEmailRaw()
        }
    }
}
```
