package com.sentosh1ne.linkedinapihelperv2.data.api.common

internal abstract class BaseApi {
    protected val requestCreator = RequestCreator()
    protected val client = ClientProvider.getClient()
}
