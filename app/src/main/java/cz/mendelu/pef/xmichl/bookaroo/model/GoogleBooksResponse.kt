package cz.mendelu.pef.xmichl.bookaroo.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoogleBooksResponse(
    var code: Long?,
    var type: String?,
    var message: String?
)
