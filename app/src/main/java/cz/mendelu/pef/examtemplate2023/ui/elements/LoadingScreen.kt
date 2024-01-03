package cz.mendelu.pef.examtemplate2023.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.examtemplate2023.ui.theme.Purple40

@Composable
fun LoadingScreen(
    modifier: Modifier,
){
    Column(modifier = modifier.fillMaxSize()
        .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = Purple40,
            strokeWidth = 5.dp)
    }
}