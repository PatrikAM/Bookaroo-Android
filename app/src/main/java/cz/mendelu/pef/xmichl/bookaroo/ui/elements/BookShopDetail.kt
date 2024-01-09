package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.sp
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.model.BookShop
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.basicMargin
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.basicTextStyle
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.headLine
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.smallMargin


@Composable
fun BookShopDetail(bookShop: BookShop) {
    Column(
        Modifier
            .fillMaxSize()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .fillMaxWidth(0.9F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OpenIndicator(bookShop.getColorIsOpen())
                Spacer(Modifier.size(basicMargin()))
                Text(
                    text = bookShop.title ?: stringResource(id = R.string.unknown),
                    style = headLine()
                )
            }
            val context = LocalContext.current
            bookShop.place?.let { Text(text = it, textAlign = TextAlign.Center) }
            bookShop.formatted_address?.let {

                val text = AnnotatedString(
                    text = it,
                )

                if (bookShop.url != null) {
                    ClickableText(
                        text = text,
                        style = TextStyle(color = getTintColor()),
                        modifier = Modifier.padding(top = basicMargin())
                    ) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(bookShop.url))
                        context.startActivity(intent)
                    }
                } else {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                    )
                }
            }
//            bookShop.rating?.let { Text(text = it.toString(), textAlign = TextAlign.Center) }
//            bookShop.rating?.let { RatingBar(rating = it.toFloat()) }
            bookShop.rating?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = smallMargin())
                ) {
                    Text(text = "$it", textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.size(smallMargin()))
                    RatingBar(rating = it.toFloat())
                }
            }


            bookShop.formatted_phone_number?.let {
                Row(
                    Modifier
                        .padding(smallMargin())
                        .clickable {
                            val u = Uri.parse(
                                "tel:" + bookShop.international_phone_number
                            )
                            val i = Intent(Intent.ACTION_DIAL, u)
                            try {

                                context.startActivity(i)
                            } catch (s: SecurityException) {

                                // show() method display the toast with
                                // exception message.
                                Toast
                                    .makeText(context, "An error occurred", Toast.LENGTH_LONG)
                                    .show()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = null,
                        Modifier.padding(smallMargin())
                    )
                    Text(text = it, textAlign = TextAlign.Center)
                }
            }

            bookShop.opening_hours?.weekday_text?.forEach {
                Text(text = it)
            }
        }
    }
}
