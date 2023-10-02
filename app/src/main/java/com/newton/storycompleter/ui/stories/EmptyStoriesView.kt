package com.newton.storycompleter.ui.stories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.newton.storycompleter.R
import com.newton.storycompleter.app.theme.StoryCompleterTheme

@Composable
fun EmptyStoriesView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            Text(
                text = stringResource(id = R.string.empty_stories),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                ),
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewEmptyStories() {
    StoryCompleterTheme {
        EmptyStoriesView()
    }
}

