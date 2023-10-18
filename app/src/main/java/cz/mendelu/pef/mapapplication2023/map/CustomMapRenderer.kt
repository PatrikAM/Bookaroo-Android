package cz.mendelu.pef.mapapplication2023.map

import android.content.Context
import android.graphics.Bitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import cz.mendelu.pef.mapapplication2023.database.Place

class CustomMapRenderer(
    private val context: Context,
    map: GoogleMap,
    manager: ClusterManager<Place>
) : DefaultClusterRenderer<Place>(context,map, manager) {

    private val icons: MutableMap<Int, Bitmap> = mutableMapOf()

    override fun onBeforeClusterItemRendered(item: Place, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getIcon(item)))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Place>): Boolean {
        return cluster.size > 5
    }

    private fun getIcon(place: Place): Bitmap {
        if (!icons.containsKey(place.type)) {
            icons.put(place.type!!, MarkerUtil.createBitmapMarker(context = context))
        }
        return icons[place.type]!!
    }
}