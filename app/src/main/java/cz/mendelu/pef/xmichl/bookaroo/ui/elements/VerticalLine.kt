package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalLine() {
    Divider(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp) // Set the height of the divider as needed
    )
}