package cz.mendelu.pef.examtemplate2023.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class IP(
    var ip: String? = null,
    var hostname: String? = null,
    var city: String? = null,
    var country: String? = null,
    var region: String? = null,
    var loc: String? = null
) : Serializable, ClusterItem {


    override fun getPosition(): LatLng {
        try {
            return LatLng(
                loc!!.split(",")[0].toDouble(),
                loc!!.split(",")[1].toDouble()
            )

        } catch (e: Exception) {
            return LatLng(
                0.0,
                0.0
            )
        }
    }

    fun hasLocation(): Boolean {
        try {
            LatLng(
                loc!!.split(",")[0].toDouble(),
                loc!!.split(",")[1].toDouble()
            )
            return true

        } catch (e: Exception) {
            return false
        }
    }

    override fun getTitle(): String? {
        return hostname
    }

    override fun getSnippet(): String? {
        return hostname
    }

    override fun getZIndex(): Float? {
        return 0.0f
    }

}
