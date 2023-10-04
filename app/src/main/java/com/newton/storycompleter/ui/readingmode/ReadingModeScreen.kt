package com.newton.storycompleter.ui.readingmode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.storycompleter.app.data.local.Story
import com.newton.storycompleter.app.theme.BaskerVille
import com.newton.storycompleter.app.theme.Georgian
import com.newton.storycompleter.app.theme.StoryCompleterTheme


@Composable
fun ReadModeScreen(
    modifier: Modifier = Modifier,
    story: Story,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
) {

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontFamily = BaskerVille,
                fontSize = 40.sp,
                fontWeight = FontWeight.Medium
            )
        ) {
            append("“${story.content[0].titlecaseChar()}")
        }
        withStyle(
            style = SpanStyle(
                fontFamily = Georgian,
                fontSize = 16.sp,
                letterSpacing = 0.2.sp,
            )
        ) {
            append(story.content.substring(1,story.content.length-1))
        }
    }

    Scaffold(
        topBar = {
            ReadingModeTopBar(
                storyTitle = story.title,
                onBack = onBack,
                onDelete = onDelete,
                onEdit = onEdit
            )
        },
        content = { contentPadding ->
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding)
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    Text(text = annotatedString)

                }
            )

        }
    )

}


@Preview
@Composable
private fun PreviewReadingMode() {
    StoryCompleterTheme {
        ReadModeScreen(
            onEdit = { },
            onBack = { },
            onDelete = { },
            story = Story(title = "This is my story", content = "This is my song")
        )
    }
}