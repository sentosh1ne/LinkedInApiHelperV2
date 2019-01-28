package com.sentosh1ne.linkedinapihelperv2.data.session

import java.io.Serializable

class AccessToken(var accessTokenValue: String, var expiresOn: Long) : Serializable