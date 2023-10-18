package cz.mendelu.pef.mapapplication2023.ui.screens.markerclustering

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.mapapplication2023.architecture.BaseViewModel
import cz.mendelu.pef.mapapplication2023.database.IPlacesLocalRepository
import cz.mendelu.pef.mapapplication2023.database.Place
import cz.mendelu.pef.mapapplication2023.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MarkerClusterViewModel @Inject constructor(private val repository: IPlacesLocalRepository) : BaseViewModel(),
    MarkerClusteringScreenActions {

    private val numberOfMarkers = 10

    val placesUIState: MutableState<UiState<MapData, MarkerClusteringErrors>> = mutableStateOf(
        UiState(loading = false)
    )

    private var mapData = MapData()

    init {
        launch {
            repository.getAll().collect{
                mapData.places = it
                placesUIState.value = UiState(data = mapData, errors = null)
            }
        }
    }

    override fun generateNewMarkers() {
        launch {
            val places = mutableListOf<Place>()
            for (i in 0..numberOfMarkers) {
                val latitude = Random.nextDouble(48.0, 51.0)
                val longitude = Random.nextDouble(12.0, 18.0)
                val type = (0..1).random()
                val place = Place(latitude, longitude)
                place.type = type
                places.add(place)
            }
            repository.insert(places)
        }
    }

}