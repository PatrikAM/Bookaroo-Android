package cz.mendelu.pef.examtemplate2023.ui.screens.root

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.examtemplate2023.ui.elements.BaseScreen
import cz.mendelu.pef.examtemplate2023.ui.screens.destinations.MapScreenDestination
import cz.mendelu.pef.examtemplate2023.ui.theme.headLine

@Destination(start = true)
@Composable
fun ExamRoot(
    navigator: DestinationsNavigator
) {
    BaseScreen(
        topBarText = "What is My IP?",
        drawFullScreenContent = false,
        showLoading = false,
        placeholderScreenContent = null,

        ) {
        RootContent(
            paddingValues = it,
        ) {
            navigator.navigate(MapScreenDestination())
        }
    }

}

@Composable
fun RootContent(
    paddingValues: PaddingValues,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "What is My IP & Location?",
            style = headLine(),
            modifier = Modifier.padding(10.dp)
        )
        Button(
            onClick = onClick
        ) {
            Text(text = "Find my IP address")
        }
    }
}
