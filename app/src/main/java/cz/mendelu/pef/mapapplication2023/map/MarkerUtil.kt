package cz.mendelu.pef.mapapplication2023.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import cz.mendelu.pef.mapapplication2023.R
import cz.mendelu.pef.mapapplication2023.database.Place


class MarkerUtil {

    companion object {

        fun createMarkerIconFromResource(context: Context, resource: Int): Bitmap {
            val drawable = ContextCompat.getDrawable(context, resource)
            drawable!!.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val bm = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bm)
            drawable.draw(canvas)
            return bm
        }


        fun createBitmapMarker(
            context: Context,
        ): Bitmap {
            return createMarkerIconFromResource(context, R.drawable.ic_marker)
        }
    }


}
