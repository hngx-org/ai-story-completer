package com.newton.storycompleter.ui.stories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.newton.storycompleter.R
import com.newton.storycompleter.app.theme.StoryCompleterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoriesTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onClick:() -> Unit,
) {
    TopAppBar(
        modifier = modifier.shadow(elevation = 4.dp),
        title = {
            Text(
                text = stringResource(id = R.string.recent_stories),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        scrollBehavior = scrollBehavior,
        actions = { Box(
            modifier = Modifier
                .background(color = Color(0xFF639454), shape = CircleShape)
                .width(35.dp)
                .height(35.dp)
                .clickable {
                   onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.account_user_profile_avatar),
                contentDescription = "call",
                tint = Color(0xFFFCFCFC)
            )
        }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewStoriesTopBar() {
    StoryCompleterTheme {
        StoriesTopBar(){}
    }

}