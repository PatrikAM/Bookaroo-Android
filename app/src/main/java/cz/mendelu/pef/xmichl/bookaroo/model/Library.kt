package cz.mendelu.pef.xmichl.bookaroo.model

import com.squareup.moshi.JsonClass
import org.mongodb.kbson.ObjectId

@JsonClass(generateAdapter = true)
data class Library(
    var id: ObjectId,
    var name: String?,
    var ownerId: ObjectId,
    var favouriteCount: Int? = 0,
    var total: Int? = 0,
    var readCount: Int? = 0
)
