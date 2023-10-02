package com.newton.storycompleter.ui.editstory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.newton.storycompleter.R
import com.newton.storycompleter.app.theme.StoryCompleterTheme
import com.newton.storycompleter.ui.editstory.bottomSheet.SettingBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStoryScreen(
    modifier: Modifier = Modifier,
    state: EditStoryState,
    updateState: (EditStoryState) -> Unit,
    isEdit: Boolean,
    onFinishClick: () -> Unit,
    onGenerateClick: () -> Unit,
    onClose: () -> Unit,
    onDecreaseCandidate: () -> Unit,
    onIncreaseCandidate: () -> Unit,
    onDecreaseWords: () -> Unit,
    onIncreaseWords: () -> Unit,
    onPremiumClicked: () -> Unit,
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            EditStoryTopBar(
                titleId = if (isEdit) R.string.edit_story else R.string.create_stories,
                onBack = onClose,
                onSettingsClick = { scope.launch { sheetState.show() } }
            )
        },
        content = { scaffoldPadding ->
            Column(
                modifier = modifier
                    .padding(scaffoldPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                content = {
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = state.title,
                        onValueChange = { updateState(state.copy(title = it)) },
                        label = { Text(text = stringResource(id = R.string.title)) },
                        textStyle = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                    )

                    ElevatedCard(
                        modifier = modifier
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(5.dp)
                            ),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
                        content = {
                            Column(
                                modifier = modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                content = {
                                    TextField(
                                        modifier = modifier
                                            .fillMaxWidth()
                                            .height(300.dp),
                                        value = state.story,
                                        onValueChange = { updateState(state.copy(story = it)) },
                                        textStyle = MaterialTheme.typography.bodySmall,
                                        label = {
                                            Text(
                                                text = stringResource(id = R.string.create_stories),
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        },
                                    )

                                    Divider()
                                    Text(
                                        text = stringResource(
                                            id = R.string.word_count,
                                            state.wordCount
                                        ),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            )
                        }
                    )

                    Spacer(modifier = modifier.height(15.dp))

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        content = {
                            Button(
                                modifier = modifier.weight(.1f),
                                onClick = onGenerateClick,
                                content = {
                                    Text(
                                        text = stringResource(id = R.string.generate),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                enabled = state.isBtnEnabled,
                                shape = RoundedCornerShape(10.dp),
                            )

                            OutlinedButton(
                                modifier = modifier.weight(.1f),
                                onClick = onFinishClick,
                                shape = RoundedCornerShape(10.dp),
                                content = {
                                    Text(
                                        text = stringResource(id = R.string.finish),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                enabled = state.isBtnEnabled,
                            )
                        }
                    )

                    AnimatedVisibility(
                        visible = sheetState.isVisible,
                        content = {
                            SettingBottomSheet(
                                onDecreaseCandidate = onDecreaseCandidate,
                                onIncreaseCandidate = onIncreaseCandidate,
                                state = state,
                                onDecreaseWords = onDecreaseWords,
                                onIncreaseWords = onIncreaseWords,
                                updateSelection = updateState,
                                onDismissSheet = { scope.launch { sheetState.hide() } },
                                sheetState = sheetState,
                                onPremiumClicked = onPremiumClicked,
                            )
                        }
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewEditStory() {
    StoryCompleterTheme {
        EditStoryScreen(
            state = EditStoryState(),
            updateState = { },
            isEdit = false,
            onFinishClick = { },
            onGenerateClick = { },
            onClose = { },
            onDecreaseCandidate = { },
            onIncreaseCandidate = { },
            onDecreaseWords = { },
            onIncreaseWords = { },
            onPremiumClicked = { }
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewEditStory2() {
    StoryCompleterTheme {
        EditStoryScreen(
            state = EditStoryState(),
            updateState = { },
            isEdit = true,
            onFinishClick = { },
            onGenerateClick = { },
            onClose = { },
            onDecreaseCandidate = { },
            onIncreaseCandidate = { },
            onDecreaseWords = { },
            onIncreaseWords = { },
            onPremiumClicked = { }
        )
    }

}