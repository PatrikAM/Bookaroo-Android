package cz.mendelu.pef.xmichl.bookaroo.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(
    val id: String? = null,
    val isbn: String? = null,
//    val libraryId: String?,
    val author: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val pageCount: Int? = null,
    val cover: String? = null,
    val description: String? = null
)