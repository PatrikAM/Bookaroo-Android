package cz.mendelu.pef.mapapplication2023.ui.screens.lines_and_polygons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.collections.PolylineManager
import com.google.maps.android.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mapapplication2023.database.PolygonAndPolyline
import cz.mendelu.pef.mapapplication2023.ui.UiState
import cz.mendelu.pef.mapapplication2023.ui.elements.BaseScreen
import java.util.*

@Destination
@Composable
fun PolygonAndPolylineScreen(navigator: DestinationsNavigator) {

    val viewModel = hiltViewModel<PolygonAndPolylineViewModel>()

    val uiState: MutableState<UiState<List<PolygonAndPolyline>, PolygonAndPolylinesErrors>> =
        rememberSaveable { mutableStateOf(UiState()) }

    viewModel.polygonAndPolylinesUIState.value.let {
        uiState.value = it

    }

        BaseScreen(
            topBarText = "Polylines and polygons",
            onBackClick = {navigator.popBackStack()},
            showLoading = false,
            drawFullScreenContent = true,
            placeholderScreenContent = null
    ) {
        PolygonAndPolylineScreenContent(
            paddingValues = it,
            actions = viewModel,
            objects = uiState.value.data
        )
    }

}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun PolygonAndPolylineScreenContent(
    paddingValues: PaddingValues,
    actions: PolygonAndPolylinesScreenActions,
    objects: List<PolygonAndPolyline>?
) {

    val mapUiSettings by remember { mutableStateOf(
        MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false)
    ) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(49.62352578743681, 15.346186434122867), 5f)
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxHeight(),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ) {

        }

        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            OutlinedButton(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                onClick = {
                    actions.generateNewObjects()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Text(text = "Create polylines")
            }

            OutlinedButton(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                onClick = {

                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Text(text = "Hide part of polylines")
            }
        }



    }
}

private fun hideEvenNumberedPolylines(polylineManager: PolylineManager) {
    // TODO Dokončit
}



