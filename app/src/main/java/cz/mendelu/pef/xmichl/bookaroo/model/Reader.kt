package cz.mendelu.pef.xmichl.bookaroo.model

import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
data class Reader(
    var id: UUID?,
    var name: String?,
    var email: String?,
    var password: String?,
    var token: String?
)
