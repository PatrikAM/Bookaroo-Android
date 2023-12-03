package cz.mendelu.pef.xmichl.bookaroo.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(
    val id: String,
    val isbn: String?,
//    val libraryId: String?,
    val author: String?,
    val title: String?,
    val subtitle: String?,
    val pageCount: Int?,
    val cover: String?,
    val description: String?
)