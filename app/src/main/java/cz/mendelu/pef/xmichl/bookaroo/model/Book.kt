package cz.mendelu.pef.xmichl.bookaroo.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(
    var id: String? = null,
    var isbn: String? = null,
    var library: String? = null,
    var author: String? = null,
    var title: String? = null,
    var subtitle: String? = null,
    var pages: Int? = null,
    var cover: String? = null,
    var description: String? = null,
    var publisher: String? = null,
    var published: String? = null,
    val language: String? = null
)