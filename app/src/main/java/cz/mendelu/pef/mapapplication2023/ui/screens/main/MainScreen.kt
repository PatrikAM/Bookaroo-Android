package cz.mendelu.pef.mapapplication2023.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mapapplication2023.ui.screens.destinations.MarkerClusterScreenDestination
import cz.mendelu.pef.mapapplication2023.ui.screens.destinations.PolygonAndPolylineScreenDestination

@Destination(start = true)
@Composable
fun MainScreen(navigator: DestinationsNavigator) {

    val viewModel = hiltViewModel<MainScreenViewModel>()

    Box(modifier = Modifier.fillMaxSize()){
        Column {
            OutlinedButton(
                onClick = {
                    navigator.navigate(MarkerClusterScreenDestination)
                }) {
                Text(text = "Marker clustering")
            }

            OutlinedButton(
                onClick = {
                    navigator.navigate(PolygonAndPolylineScreenDestination)
                }) {
                Text(text = "Polygons and polylines")
            }
        }


    }


}
