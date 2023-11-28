package cz.mendelu.pef.xmichl.bookaroo.model

import com.squareup.moshi.JsonClass
import org.mongodb.kbson.ObjectId

@JsonClass(generateAdapter = true)
data class Reader(
    var id: ObjectId?,
    var name: String?,
    var login: String?,
    var password: String?,
    var token: String?
)
