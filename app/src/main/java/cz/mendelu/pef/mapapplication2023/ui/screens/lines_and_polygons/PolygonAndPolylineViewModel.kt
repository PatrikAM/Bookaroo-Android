package cz.mendelu.pef.mapapplication2023.ui.screens.lines_and_polygons

import android.content.res.AssetManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.mapapplication2023.architecture.BaseViewModel
import cz.mendelu.pef.mapapplication2023.database.Location
import cz.mendelu.pef.mapapplication2023.database.PolygonAndPolyline
import cz.mendelu.pef.mapapplication2023.ui.UiState
import cz.mendelu.pef.mapapplication2023.ui.screens.markerclustering.MarkerClusteringErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class PolygonAndPolylineViewModel @Inject constructor(): BaseViewModel(), PolygonAndPolylinesScreenActions{

    private val numberOfObjects = 50

    val polygonAndPolylinesUIState: MutableState<UiState<List<PolygonAndPolyline>,
            PolygonAndPolylinesErrors>> = mutableStateOf(
        UiState(loading = false)
    )


    override fun generateNewObjects() {
        launch {
            var counter = 0L;
            val objects = mutableListOf<PolygonAndPolyline>()
            for (i in 0..numberOfObjects) {
                val polygonOrPolyline = (0..1).random()
                val numberOfPoints = (3..30).random()
                val locations: MutableList<Location> = mutableListOf()
                for (vertices in 0..numberOfPoints) {
                    val latitude = Random.nextDouble(48.0, 51.0)
                    val longitude = Random.nextDouble(12.0, 18.0)
                    locations.add(Location(latitude, longitude))
                }
                val pp = PolygonAndPolyline(locations)
                pp.type = polygonOrPolyline
                pp.id = counter
                counter++
                objects.add(pp)
            }
            polygonAndPolylinesUIState.value = UiState(data = objects)
        }
    }
}

