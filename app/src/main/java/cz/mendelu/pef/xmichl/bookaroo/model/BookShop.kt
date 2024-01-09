package cz.mendelu.pef.xmichl.bookaroo.model

import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
//import com.google.android.libraries.places.api.model.OpeningHours
import com.google.maps.android.clustering.ClusterItem
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class GPlaces(
    val next_page_token: String?,
    val results: List<BookShop>
) : Serializable

@JsonClass(generateAdapter = true)
data class GPlace(
    val result: BookShop
) : Serializable

//@JsonClass(generateAdapter = true)
data class BookShop(
    val geometry: Geometry?,
    val place: String?,
    val name: String?,
    val reference: String?,
    val icon: String?,
    val rating: Double?,
    val opening_hours: OpeningHours?,
    val formatted_address: String?,
    val photos: List<Photo>?,
    val place_id: String?,
    val formatted_phone_number: String?,
    val international_phone_number: String?,
    val url: String?,
) : Serializable, ClusterItem {

    fun getColorIsOpen(): Color {
        return if (this.opening_hours?.open_now == null) {
            Color.Yellow
        } else if (this.opening_hours.open_now) {
            Color.Green
        } else {
            Color.Red
        }
    }

    override fun getPosition(): LatLng {
        return LatLng(
            geometry?.location?.lat ?: 1.0,
            geometry?.location?.lng ?: 1.0
        )
    }

    override fun getTitle(): String? {
        return name
    }

    override fun getSnippet(): String? {
        return name
    }

    override fun getZIndex(): Float {
        return 0.0f
    }

}

@JsonClass(generateAdapter = true)
data class Geometry(
    val location: Location?
)

@JsonClass(generateAdapter = true)
data class Photo(
    val height: Double?,
    val width: Double?,
    val photo_reference: String?,
)

@JsonClass(generateAdapter = true)
data class OpeningHours(
    val open_now: Boolean?,
    val weekday_text: List<String>?
)
