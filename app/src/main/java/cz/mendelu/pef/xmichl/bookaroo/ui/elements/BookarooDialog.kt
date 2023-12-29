package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.basicMargin
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.basicTextColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.basicTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookarooDialog(
//    cornerRadius: Dp = 16.dp,
//    progressIndicatorColor: androidx.compose.ui.graphics.Color = Color(0xFF35898f),
//    progressIndicatorSize: Dp = 80.dp,
    content: PlaceholderScreenContent,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false // disable the default size so that we can customize it
        )
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
        ) {

            Column(
                modifier = Modifier
                    .padding(start = 42.dp, end = 42.dp)
                    .padding(top = 42.dp, bottom = 42.dp)
                    .fillMaxWidth(0.5F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                PlaceHolderScreen(
//                    modifier = Modifier,
//                    content = placeholderScreenContent
//                )
                if (content.image != null) {
                    Image(
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.width(300.dp),
                        painter = painterResource(id = content.image),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(basicMargin()))

                if (content.text != null) {
                    Text(
                        text = content.text,
                        style = basicTextStyle(),
                        textAlign = TextAlign.Center,
                        color = basicTextColor()
                    )
                }
                Spacer(modifier = Modifier.height(basicMargin()))
                Button(onClick = onDismiss) {
                    Text("Ok")
                }
            }
        }
    }
}
