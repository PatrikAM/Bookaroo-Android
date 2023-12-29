package cz.mendelu.pef.xmichl.bookaroo.ui.screens.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.xmichl.bookaroo.architecture.BaseViewModel
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookShopMapViewModel
@Inject constructor()
    : BaseViewModel() {

    private val numberOfMarkers = 10

    val placesUIState: MutableState<UiState<MapData, BookShopMapErrors>> = mutableStateOf(
        UiState(loading = false)
    )

    private var mapData = MapData()

    init {
    }

}
