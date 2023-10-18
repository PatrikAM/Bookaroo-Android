package cz.mendelu.pef.mapapplication2023.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import java.io.Serializable

data class PolygonAndPolyline(var location: List<Location>) : Serializable {

    var id: Long? = null

    var type: Int? = null

    fun getLatLng(): List<LatLng>{
        val list: MutableList<LatLng> = mutableListOf()
        location.forEach {
            list.add(LatLng(it.latitude, it.longitude))
        }
        return list.toList()
    }

}

