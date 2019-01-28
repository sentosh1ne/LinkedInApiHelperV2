package com.sentosh1ne.linkedinapihelperv2.utils

import java.util.*

/**
 * Represents the types of data for which access is being requested.
 */
class PermissionsScope(vararg permissions: String) {

    private val permissions = HashSet<String>()

    val scopeValue: String
        get() = toString()

    init {
        this.permissions.addAll(permissions)
    }

    override fun toString(): String {
        val scope = StringBuilder()
        permissions.forEachIndexed { index, item ->
            if (index == 0) {
                scope.append(item)
            } else {
                scope.append("+$item")
            }
        }

        return scope.toString()
    }
}

object Scopes {
    /**
     * Your Profile Overview 	Name, photo, headline, and current positions
     */
    val R_BASICPROFILE = "r_basicprofile"

    /**
     * Your Full Profile 	Full profile including experience, education, skills, and recommendations
     */
    val R_FULLPROFILE = "r_fullprofile"


    val R_LITEPROFILE = "r_liteprofile"

    /**
     * Your Email Address 	The primary email address you use for your LinkedIn account
     */
    val R_EMAILADDRESS = "r_emailaddress"

    /**
     * Your Contact Info 	Address, phone number, and bound accounts
     */
    val R_CONTACTINFO = "r_contactinfo"
    /**
     * Company Page & Analytics 	Edit company pages for which I am an Admin and post status updates on behalf of those companies
     */
    val RW_COMPANY_ADMIN = "rw_company_admin"

    /**
     * Share, comment & like    Post updates, make comments and like posts
     */
    val W_SHARE = "w_share"
}
