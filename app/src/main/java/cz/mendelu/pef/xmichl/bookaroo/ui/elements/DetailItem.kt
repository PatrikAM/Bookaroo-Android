package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintAltColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.headLine
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.smallMargin

@Composable
fun DetailItem(
    key: String,
    value: String,
    onClick: (label: String, value: String) -> Unit
) {
    Column(verticalArrangement = Arrangement.Center) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                Modifier.fillMaxWidth(0.3f),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = key, color = getTintAltColor())
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(smallMargin()),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = value,
                    color = getTintColor(),
                    style = headLine()
                )
                Spacer(Modifier.size(smallMargin()))
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    tint = getTintAltColor(),
                    modifier = Modifier.clickable {
                        onClick(key, value)
                    }
                )
            }
        }
    }
    HorizontalLine()
}