package com.newton.storycompleter.ui.onboarding

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.storycompleter.R
import com.newton.storycompleter.app.theme.GradientColors
import kotlinx.coroutines.delay


private const val SPLASH_DURATION = 2000

@Composable
fun SplashScreen(
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val currentFontSizePx = with(LocalDensity.current) { 24.sp.toPx() }
    val currentFontSizeDoublePx = currentFontSizePx * 2

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = currentFontSizeDoublePx,
        animationSpec = infiniteRepeatable(tween(800, easing = LinearEasing)), label = ""
    )
    val brush = Brush.linearGradient(
        colors = GradientColors,
        start = Offset(offset, offset),
        end = Offset(offset + currentFontSizePx, offset+currentFontSizePx),
        tileMode = TileMode.Mirror
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            Column(
                modifier = modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.storytelling),
                        contentDescription = stringResource(id = R.string.story_logo),
                        modifier = modifier.size(100.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.splash_logo),
                        style = MaterialTheme.typography.titleLarge.merge(
                            TextStyle(brush = brush )
                        )
                    )
                }
            )
            LaunchedEffect(key1 = true, block = {
                delay(SPLASH_DURATION.toLong())
                onNext()
            })
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewSplash() {
    SplashScreen(onNext = {  } )

}