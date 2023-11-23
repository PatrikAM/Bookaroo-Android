package cz.mendelu.pef.xmichl.bookaroo.model

import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
data class Library(
    var id: UUID,
    var name: String?,
    var owner: Reader?,
    var favouriteCount: Int?,
    var total: Int?,
    var readCount: Int?
)
