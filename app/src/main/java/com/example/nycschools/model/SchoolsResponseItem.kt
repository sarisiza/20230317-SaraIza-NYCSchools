package com.example.nycschools.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class SchoolsResponseItem
 * @author Sara Iza
 * Represents the response from the NYC Schools API
 */

@JsonClass(generateAdapter = true)
data class SchoolsResponseItem(
    @Json(name = "dbn")
    val dbn: String? = null,
    @Json(name = "location")
    val location: String? = null,
    @Json(name = "overview_paragraph")
    val overviewParagraph: String? = null,
    @Json(name = "phone_number")
    val phoneNumber: String? = null,
    @Json(name = "school_email")
    val schoolEmail: String? = null,
    @Json(name = "school_name")
    val schoolName: String? = null,
    @Json(name = "website")
    val website: String? = null
)