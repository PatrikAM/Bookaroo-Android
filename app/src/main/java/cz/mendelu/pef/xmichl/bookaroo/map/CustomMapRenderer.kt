package cz.mendelu.pef.xmichl.bookaroo.map

import android.content.Context
import android.graphics.Bitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import cz.mendelu.pef.xmichl.bookaroo.model.BookShop

class CustomMapRenderer(
    private val context: Context,
    map: GoogleMap,
    manager: ClusterManager<BookShop>
) : DefaultClusterRenderer<BookShop>(context,map, manager) {

    private val icons: MutableMap<Int, Bitmap> = mutableMapOf()

    override fun onBeforeClusterItemRendered(item: BookShop, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getIcon(item)))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<BookShop>): Boolean {
        return cluster.size > 5
    }

    private fun getIcon(place: BookShop): Bitmap {
        if (!icons.containsKey(0)) {
            icons.put(0, MarkerUtil.createBitmapMarker(context = context))
        }
        return icons[0]!!

    }
}