package com.newton.storycompleter.ui.readingmode

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.newton.storycompleter.R
import com.newton.storycompleter.app.theme.StoryCompleterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingModeTopBar(
    modifier: Modifier = Modifier,
    storyTitle: String,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
) {
    LargeTopAppBar(
        title = {
            Text(
                text = storyTitle,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onDelete,
                content = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.del)
                    )
                }
            )
            IconButton(
                onClick = onEdit, content = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit)
                    )
                }
            )
        }
    )


}

@Preview
@Composable
private fun PreviewReadingModeTopBar() {
    StoryCompleterTheme {
        ReadingModeTopBar(
            storyTitle = "The Story of The Little Pig In The Circle of Circumstances",
            onBack = {  },
            onDelete = { },
            onEdit = { }
        )
    }

}