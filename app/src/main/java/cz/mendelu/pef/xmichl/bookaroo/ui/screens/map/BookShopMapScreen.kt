package cz.mendelu.pef.xmichl.bookaroo.ui.screens.map

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.*
import com.google.maps.android.compose.clustering.Clustering
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.map.CustomMapRenderer
import cz.mendelu.pef.xmichl.bookaroo.model.BookShop
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BookShopMapScreenDestination


@Destination
@Composable
fun BookShopMapScreen(navigator: DestinationsNavigator) {

    val viewModel = hiltViewModel<BookShopMapViewModel>()

    val uiState: MutableState<UiState<MapData, BookShopMapErrors>> =
        rememberSaveable { mutableStateOf(UiState()) }

    viewModel.placesUIState.value.let {
        uiState.value = it
    }

    BaseScreen(
        topBarText = null,
        onBackClick = { navigator.popBackStack() },
        showLoading = false,
        placeholderScreenContent = null,
        drawFullScreenContent = true,
        isNavScreen = true,
        navigator = navigator,
        currentRoute = BookShopMapScreenDestination.route
    ) {
        BookShopMapScreenContent(
            paddingValues = it,
//            actions = viewModel,
            navigator = navigator,
            mapData = uiState.value.data,
        )
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun BookShopMapScreenContent(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
//    actions: MarkerClusteringScreenActions,
    mapData: MapData?,
) {


    val mapUiSettings by remember { mutableStateOf(
        MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false)
    ) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(49.62352578743681, 15.346186434122867), 5f)
    }

    val context = LocalContext.current
    var googleMap by remember {
        mutableStateOf<GoogleMap?>(null)
    }
    var customRenderer by remember {
        mutableStateOf<CustomMapRenderer?>(null)
    }
    var manager by remember {
        mutableStateOf<ClusterManager<BookShop>?>(null)
    }

    if (mapData != null) {
        manager?.addItems(mapData.places)
        manager?.cluster()
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
        GoogleMap(modifier = Modifier.fillMaxHeight(),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ){

            if (mapData != null) {
                MapEffect(mapData.places) {
                    if (googleMap == null) {
                        googleMap = it
                    }

                    if (manager == null) {
                        manager = ClusterManager(context, googleMap)
                        customRenderer = CustomMapRenderer(context, googleMap!!, manager!!)

                        manager?.apply {
//                            algorithm = GridBasedAlgorithm()
                            renderer = customRenderer
                        }

                        manager?.addItems(mapData.places)

                    }

                    googleMap?.setOnCameraIdleListener {
                        manager?.cluster()
                    }
                }


                Clustering(
                    items = mapData.places,
                    onClusterItemClick = {
//                        navigator.navigate(DetailScreenDestination())
                        true
                    },
//                    clusterItemContent = {
//                        Text(text = it.id.toString())
//                    }
                )
            }

        }

    }
}


