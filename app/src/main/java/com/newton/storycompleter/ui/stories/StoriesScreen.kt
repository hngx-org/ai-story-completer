package com.newton.storycompleter.ui.stories

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.newton.storycompleter.AiStoriesViewModel
import com.newton.storycompleter.R
import com.newton.storycompleter.app.theme.StoryCompleterTheme
import com.newton.storycompleter.data.Story


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoriesScreen(
    modifier: Modifier = Modifier,
    onStoryClick: (Int) -> Unit, //Todo add state
    onCreateStoryClick: () -> Unit, //Todo navigate to the next screen
    state: StoryListState
) {
/*val stories = listOf( "The Lost City",
        "The Secret of the Island",
        "The Curse of the Castle",
        "The Haunted Mansion",
        "The Mysterious Stranger")*/
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val viewModel = AiStoriesViewModel()

    viewModel.generateText()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = { StoriesTopBar(scrollBehavior = scrollBehaviour) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = modifier.padding(end = 16.dp, bottom = 16.dp),
                onClick = onCreateStoryClick,
                content = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.create_stories)
                    )
                    Text(text = stringResource(id = R.string.create_stories))
                }
            )
        },
        content = { contentPadding ->

            Box(modifier = Modifier.padding(contentPadding).fillMaxSize()){
                if (state.loading)
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                else if (state.stories.isEmpty())
                    EmptyStoriesView()
                else
                    StoryList(
                        stories = state.stories,
                        onStoryClick = onStoryClick
                    )

            }
        }
    )
}


@Composable
fun StoryList(
    modifier: Modifier = Modifier,
    stories:List<Story>,
    onStoryClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            itemsIndexed(stories) {index,story->
                StoryItem(
                    modifier = modifier.animateContentSize(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing
                        )
                    ),
                    storyTitle = story.title,
                    onItemClick = {onStoryClick(index)},
                )
            }
        },
    )
}

@Preview
@Composable
private fun PreviewStoriesScreen() {
    StoryCompleterTheme {
        val stories = listOf(
                Story(title = "Kid Danger",id =0, content = ""),
                Story(title = "SpongeBob SquarePants", id = 1, content = ""),
                Story(title = "Sofia the first living in a castle", id = 2, content = "")
            )

        StoriesScreen(
            onStoryClick = { },
            onCreateStoryClick = { },
            state = StoryListState(stories = stories)
        )
    }
}