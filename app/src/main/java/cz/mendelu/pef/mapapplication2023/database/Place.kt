package cz.mendelu.pef.mapapplication2023.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import java.io.Serializable


@Entity(tableName = "places")
data class Place(@ColumnInfo(name = "latitude")
                 var latitude: Double,
                 @ColumnInfo(name = "longitude")
                var longitude: Double) : Serializable, ClusterItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

    @ColumnInfo(name = "type")
    var type: Int? = null

    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }

    override fun getTitle(): String? {
        return type.toString()
    }

    override fun getSnippet(): String? {
        return type.toString()
    }

    override fun getZIndex(): Float? {
        return 0.0f
    }

}


