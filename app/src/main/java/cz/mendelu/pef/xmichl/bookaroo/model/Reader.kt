package cz.mendelu.pef.xmichl.bookaroo.model

import com.squareup.moshi.JsonClass
import org.mongodb.kbson.ObjectId
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Reader(
    var id: String?,
    var name: String?,
    var login: String?,
//    var password: String?,
    var token: String?
)
