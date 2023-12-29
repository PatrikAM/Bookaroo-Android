package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookarooSmallCard(
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier,
//    imageWidth: Dp,
//    imageHeight: Dp,
    photo: String,
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
            BookarooSmallCardContent(
                title = title,
                subtitle = subtitle,
                photo = photo
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
            BookarooSmallCardContent(
                title = title,
                subtitle = subtitle,
                photo = photo
//                imageWidth = imageWidth,
//                imageHeight = imageHeight,
            )
        }
    }
}

@Composable
fun BookarooSmallCardContent(
    title: String,
    subtitle: String?,
    photo: String
//    imageWidth: Dp,
//    imageHeight: Dp,
) {
    Row {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .size(width = 120.dp, height = 120.dp),
            contentAlignment = Alignment.Center
        ) {
            if (
                Patterns.WEB_URL.matcher(photo).matches()
            ) {
                ImageOnBlurredImage(imageUrl = photo)
            } else {
                ImageOnBlurredImage(imageUrl = null)
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier
                    .padding(bottom = 6.dp, top = 6.dp, end = 15.dp, start = 15.dp)
            )


            subtitle?.let { st ->
                Text(
                    text = st,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
//                Row {
//                    pet.tags?.forEach { tag: Tag ->
//                        if (tag.name != null) {
//                            AssistChip(
//                                onClick = {},
//                                label = { Text(text = tag.name!!) }
//                            )
//                        }
//                    }
//                }
        }
    }
}
