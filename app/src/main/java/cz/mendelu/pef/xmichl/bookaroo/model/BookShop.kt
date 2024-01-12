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
    val geometry: Geometry? = null,
    val place: String? = null,
    val name: String? = null,
    val reference: String? = null,
    val icon: String? = null,
    val rating: Double? = null,
    val opening_hours: OpeningHours? = null,
    val formatted_address: String? = null,
    val photos: List<Photo>? = null,
    val place_id: String? = null,
    val formatted_phone_number: String? = null,
    val international_phone_number: String? = null,
    val url: String? = null,
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
