package com.newton.storycompleter.ui.stories

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.newton.storycompleter.R
import com.newton.storycompleter.app.theme.StoryCompleterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryItem(
    modifier: Modifier = Modifier,
    storyTitle: String,
    onItemClick: (String) -> Unit //todo pass state class here
) {

    val onItemClicked = remember {
        {
            onItemClick(storyTitle)
        }
    }

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onItemClicked,
        shape = RoundedCornerShape(8.dp),
        content = {
            Row(
                modifier = modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.book_icon),
                        contentDescription = stringResource(id = R.string.book_icon),
                        modifier = modifier.size(40.dp)
                    )

                    Text(
                        text = storyTitle,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun PreviewStoryItem() {
    StoryCompleterTheme {
        StoryItem(storyTitle = "Greedy Goat Matata", onItemClick = { })
    }

}