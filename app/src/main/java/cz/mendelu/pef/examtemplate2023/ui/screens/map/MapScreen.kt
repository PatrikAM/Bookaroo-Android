package cz.mendelu.pef.examtemplate2023.ui.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.examtemplate2023.R
import cz.mendelu.pef.examtemplate2023.model.IP
import cz.mendelu.pef.examtemplate2023.model.UiState
import cz.mendelu.pef.examtemplate2023.ui.elements.BaseScreen
import cz.mendelu.pef.examtemplate2023.ui.elements.PlaceholderScreenContent
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Destination(route = "map")
@Composable
fun MapScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = hiltViewModel<MapViewModel>()

    val uiState: MutableState<UiState<IP, IPErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }

    viewModel.uiState.value.let {
        uiState.value = it
    }

    BaseScreen(
        topBarText = "IP and Location",
        onBackClick = { navigator.navigateUp() },
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        placeholderScreenContent = if (
            uiState.value.errors != null &&
            !uiState.value.loading
        ) {
            PlaceholderScreenContent(
                image = R.drawable.ic_launcher_foreground,
                text = stringResource(id = uiState.value.errors!!.communicationError)
            )
        } else null,
        actions = {

        },

        ) {
        MapScreenContent(
            paddingValues = it,
            data = uiState.value.data!!
        )
    }


}

@Composable
fun MapScreenContent(
    paddingValues: PaddingValues,
    data: IP,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (data.city != null && data.city == "Prague") {
            ElevatedCard(
                Modifier
                    .fillMaxWidth(0.9F)
                    .padding(10.dp)) {
                AsyncImage(
                    "https://images.contentstack.io/v3/assets/blt06f605a34f1194ff/blt4bfcd9c6eb1d4749/625c313698a5e44ea7852880/BCC-2022-IT-PRAGUE-DAY1-STOP1.jpg",
//                    model = "https://img.freepik.com/free-vector/silhouette-skyline-illustration_53876-78787.jpg",
                    contentDescription = null
                )
            }
        } else {
//            ElevatedCard(
//                Modifier
//                    .fillMaxWidth(0.9F)
//                    .padding(10.dp)) {
                AsyncImage(
                    model = "https://img.freepik.com/free-vector/silhouette-skyline-illustration_53876-78787.jpg",
                    contentDescription = null,
                )
//            }
        }

        ElevatedCard(modifier = Modifier.fillMaxWidth(0.8F)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            ) {
                Column(Modifier.fillMaxWidth(0.4F)) {
                    Text(text = "Current IP is:", color = Color.Black)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = data.ip!!)
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 2.dp)
            ) {
                Column(Modifier.fillMaxWidth(0.3F)) {
                    data.city?.let { Text(text = "City: ") }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    data.city?.let { Text(text = it) }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 2.dp)
            ) {
                Column(Modifier.fillMaxWidth(0.3F)) {
                    data.region?.let { Text(text = "Region: ") }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    data.region?.let { Text(text = it) }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 2.dp, bottom = 10.dp)
            ) {
                Column(Modifier.fillMaxWidth(0.3F)) {
                    data.country?.let { Text(text = "Country: ") }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    data.country?.let { Text(text = it) }
                }
            }

        }

        if (data.loc != null && data.hasLocation()) {

            val mapUiSettings by remember {
                mutableStateOf(
                    MapUiSettings(
                        zoomControlsEnabled = false,
                        mapToolbarEnabled = false
                    )
                )
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(data.position, 10f)
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        uiSettings = mapUiSettings
                    ) {

                        Marker(
                            state = MarkerState(data.position),
                            onClick = {
                                false
                            },
//                            icon =
//                            BitmapDescriptorFactory
//                                .defaultMarker(
//                                    BitmapDescriptorFactory.HUE_AZURE
//                                )
                        )
                    }

                }
            }

        }
    }

}
