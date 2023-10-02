package com.newton.storycompleter.ui.stories

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
) {
    TopAppBar(
        modifier = modifier.shadow(elevation = 4.dp),
        title = {
            Text(
                text = stringResource(id = R.string.recent_stories),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        scrollBehavior = scrollBehavior
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewStoriesTopBar() {
    StoryCompleterTheme {
        StoriesTopBar()
    }

}