package com.sentosh1ne.linkedinapihelperv2.entities

interface Fields {
    /**
     * To access any of the following lite profile fields, your application must request the r_liteprofile member permission
     */
    object LiteProfile {
        val ID = "id"

        val FIRST_NAME = "firstName"

        val LAST_NAME = "lastName"

        val MAIDEN_NAME = "maidenName"

        val PROFILE_PICTURE = "profilePicture"
    }

    object EmailAddress

    /**
     * To access any of the following basic profile fields, your application must request the r_basicprofile member permission
     */
    object BasicProfile {
        val ID = "id"

        val FIRST_NAME = "firstName"

        val LAST_NAME = "lastName"

        val MAIDEN_NAME = "maidenName"

        val PHONETIC_FIRST_NAME = "phoneticFirstName"

        val PHONETIC_LAST_NAME = "phoneticLastName"

        val HEADLINE = "headline"

        val LOCATION = "location"

        val INDUSTRY_ID = "industryId"

        val SUMMARY = "summary"

        val POSITIONS = "positions"

        val PICTURE_INFO = "pictureInfo"

        val PROFILE_PICTURE = "profilePicture"

        val VANITY_NAME = "vanityName"

        val LAST_MODIFIED = "lastModified"
    }

    /**
     * To access any of the following full profile fields, your app must request the r_fullprofile member permission.
     * Note that r_basicprofile provides access to a sub-set of the fields made available by r_fullprofile ,
     * so if you are requesting r_fullprofile , there is no need to also request the r_basicprofile permission.
     *
     */
    object FullProfile {
        val ADDRESS = "address"

        val ASSOCIATIONS = "associations"

        val BACKGROUND_IMAGE = "backgroundImage"

        val BIRTH_DATE = "birthDate"

        val CERTIFICATIONS = "certifications"

        val CONTACT_INSTRUCTIONS = "contactInstructions"

        val COURSES = "courses"

        val EDUCATIONS = "educations"

        val HONORS = "honors"

        val IMS = "ims"

        val LANGUAGES = "languages"

        val LEGACY_HONORS = "legacyHonors"

        val MARITAL_STATUS = "maritalStatus"

        val ORGANIZATIONS = "organizations"

        val PATENTS = "patents"

        val PHONE_NUMBERS = "phoneNumbers"

        val PROJECTS = "projects"

        val PUBLICATIONS = "publications"

        val SKILLS = "skills"

        val SUMMARY_MEDIA_ASSOCIATIONS = "summaryRichMediaAssociations"

        val SUPPORTED_LOCALES = "supportedLocales"

        val TEST_SCORES = "testScores"

        val VOLUNTEERING_EXPERIENCES = "volunteeringExperiences"

        val VOLUNTEERING_INTERESTS = "volunteeringInterests"

        val WEBSITES = "websites"
    }
}