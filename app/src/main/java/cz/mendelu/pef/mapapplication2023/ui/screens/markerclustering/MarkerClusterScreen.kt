package cz.mendelu.pef.mapapplication2023.ui.screens.markerclustering

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.GridBasedAlgorithm
import com.google.maps.android.compose.*
import com.google.maps.android.compose.clustering.Clustering
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mapapplication2023.database.Place
import cz.mendelu.pef.mapapplication2023.map.CustomMapRenderer
import cz.mendelu.pef.mapapplication2023.ui.UiState
import cz.mendelu.pef.mapapplication2023.ui.elements.BaseScreen
import cz.mendelu.pef.mapapplication2023.ui.screens.destinations.DetailScreenDestination


@Destination
@Composable
fun MarkerClusterScreen(navigator: DestinationsNavigator) {

    val viewModel = hiltViewModel<MarkerClusterViewModel>()

    val uiState: MutableState<UiState<MapData, MarkerClusteringErrors>> =
        rememberSaveable { mutableStateOf(UiState()) }

    viewModel.placesUIState.value.let {
        uiState.value = it

    }

    BaseScreen(
        topBarText = "Marker clustering",
        onBackClick = {navigator.popBackStack()},
        showLoading = false,
        placeholderScreenContent = null,
        drawFullScreenContent = true
    ) {
        MarkerClusterScreenContent(
            paddingValues = it,
            actions = viewModel,
            navigator = navigator,
            mapData = uiState.value.data,
            )
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MarkerClusterScreenContent(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    actions: MarkerClusteringScreenActions,
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
        mutableStateOf<ClusterManager<Place>?>(null)
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


//                Clustering(
//                    items = mapData.places,
//                    onClusterItemClick = {
//                        navigator.navigate(DetailScreenDestination())
//                        true
//                    },
//                    clusterItemContent = {
//                        Text(text = it.id.toString())
//                    }
//                )
            }

        }

        OutlinedButton(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .align(Alignment.BottomCenter),
            onClick = {
                actions.generateNewMarkers()
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White
            )
        ) {
            Text(text = "Create markers")
        }

    }
}


