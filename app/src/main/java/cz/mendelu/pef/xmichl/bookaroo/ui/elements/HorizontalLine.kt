package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalLine() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp) // Set the height of the divider as needed
    )
}