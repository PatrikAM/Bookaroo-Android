package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import android.util.Patterns
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.mendelu.pef.xmichl.bookaroo.testTags.BooksTestTags

@Composable
fun ImageOnBlurredImage(
    imageUrl: String?
) {
    lateinit var image: String

    if (
        imageUrl == null ||
        !Patterns.WEB_URL.matcher(imageUrl).matches()
    ) {
        image = "https://dreieck.com/en/wp-content/uploads/sites/2/2023/03/opened-book-09-final-touch-1536x1152.png"
    } else {
        image = imageUrl
    }

    AsyncImage(
        model = image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize().blur(10.dp)
    )
    AsyncImage(
        model = image,
        modifier = Modifier.fillMaxHeight().testTag(BooksTestTags.TestTagBookImage),
        contentScale = ContentScale.FillHeight,
        contentDescription = null,
    )
}
