package cz.mendelu.pef.xmichl.bookaroo.model

import com.squareup.moshi.JsonClass
import org.mongodb.kbson.ObjectId

@JsonClass(generateAdapter = true)
data class Library(
    var id: String,
    var name: String?,
    var ownerId: String?,
    var favourite: Int? = 0,
    var total: Int? = 0,
    var read: Int? = 0
)
