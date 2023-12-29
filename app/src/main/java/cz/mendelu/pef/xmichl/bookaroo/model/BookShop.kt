package cz.mendelu.pef.xmichl.bookaroo.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class BookShop(
    val location: Location,
    val place: String,
    val name: String,
    val reference: String,
    val icon: String,
    val rating: Float,
//    opening_hours:
    val formatted_address: String
): Serializable, ClusterItem {



    override fun getPosition(): LatLng {
        return LatLng(location.lat, location.lng)
    }

    override fun getTitle(): String? {
        return name
    }

    override fun getSnippet(): String? {
        return name
    }

    override fun getZIndex(): Float? {
        return 0.0f
    }

}