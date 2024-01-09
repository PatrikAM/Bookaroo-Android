package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OpenIndicator(color: Color) {
    val infiniteTransition = rememberInfiniteTransition()
    val animColor by infiniteTransition.animateColor(
        initialValue = Color.Black,
        targetValue = color,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val size = 20.dp
    Canvas(
        modifier = Modifier
            .background(color = animColor, shape = CircleShape)
            .size(size)
    ) {
        drawCircle(
            color = animColor,
            radius = size.toPx() / 2
        )
    }


//    Box(Modifier.fillMaxSize().background(color))
}
