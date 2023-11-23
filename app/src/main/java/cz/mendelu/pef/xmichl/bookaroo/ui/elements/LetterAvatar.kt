package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.AvatarColors
import kotlin.random.Random

@Composable
fun LetterAvatar(letter: Char) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(
                AvatarColors[Random.nextInt(0, AvatarColors.size-1)],
                shape = CircleShape
            )
    ) {
        Text(
            text = letter.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color.White

        )
    }
}
