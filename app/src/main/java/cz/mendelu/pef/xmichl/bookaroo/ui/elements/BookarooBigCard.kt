package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.Typography
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookarooBigCard(
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier,
//    imageWidth: Dp,
//    imageHeight: Dp,
    slots: Map<String, String?>,
    onCardClick: () -> Unit,
    enabled: Boolean = true
) {
    if (enabled) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = modifier,
            onClick = onCardClick,
        ) {
            BookarooBigCardContent(
                title = title,
                subtitle = subtitle,
                slots = slots
//                imageWidth = imageWidth,
//                imageHeight = imageHeight,
            )
        }
    } else {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = modifier
        ) {
            BookarooBigCardContent(
                title = title,
                subtitle = subtitle,
                slots = slots
//                imageWidth = imageWidth,
//                imageHeight = imageHeight,
            )
        }
    }
}

@Composable
fun BookarooBigCardContent(
    title: String,
    subtitle: String?,
    slots: Map<String, String?>
//    imageWidth: Dp,
//    imageHeight: Dp,
) {
    Row(modifier = Modifier.padding(16.dp)) {
        LetterAvatar(letter = title[0])
        Spacer(modifier = Modifier.padding(10.dp))
        Column {
            Text(text = title, style = Typography.titleMedium)
            Spacer(modifier = Modifier.padding(5.dp))
            subtitle?.let { Text(text = it) }
        }
    }
    HorizontalLine()
//    Spacer(modifier = Modifier.padding(5.dp))
    val slotEntries = slots.entries.toList()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        slotEntries.forEachIndexed { index, (key, value) ->

//            Column(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(15.dp),
//            verticalArrangement = Arrangement.SpaceBetween
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = key,
                        style = Typography.labelSmall,
                        color = getTintColor()
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text = value ?: "unknown")
                }
            }
            if (index < slots.size - 1) {
                Column(
                    modifier = Modifier.fillMaxHeight(0.8F),
                    verticalArrangement = Arrangement.Center
                ) {
                    VerticalLine()
                }

            }
        }
    }
}
