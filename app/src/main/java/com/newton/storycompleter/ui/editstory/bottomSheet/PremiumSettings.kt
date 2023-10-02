package com.newton.storycompleter.ui.editstory.bottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.storycompleter.R
import com.newton.storycompleter.ui.editstory.CreativeIndex
import com.newton.storycompleter.ui.editstory.EditStoryState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingBottomSheet(
    modifier: Modifier = Modifier,
    onDecreaseCandidate: () -> Unit,
    onIncreaseCandidate: () -> Unit,
    state: EditStoryState,
    onDecreaseWords: () -> Unit,
    onIncreaseWords: () -> Unit,
    updateSelection: (EditStoryState) -> Unit,
    onDismissSheet: () -> Unit,
    sheetState: SheetState,
    onPremiumClicked: () -> Unit,
) {


    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissSheet,
        content = {
            Column(
                modifier = modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                content = {

                    Row(
                        modifier = modifier.fillMaxWidth().padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = stringResource(id = R.string.only_premium),
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Button(
                                onClick = onPremiumClicked,
                                content = {
                                    Text(
                                        text = stringResource(id = R.string.go_premium),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                            )
                        }
                    )

                    SettingRow(
                        title = stringResource(id = R.string.creativity_lvl),
                        description = {
                            CreativityDropDownMenu(
                                state = state,
                                updateSelection = updateSelection
                            )
                        }
                    )

                    SettingRow(
                        title = stringResource(id = R.string.generate_candidate),
                        description = {
                            NumericalSetting(
                                onDecrease = onDecreaseCandidate,
                                onIncrease = onIncreaseCandidate,
                                count = state.premiumFeatures.candidateCount,
                                isEnabled = state.isPremiumVersion
                            )
                        }
                    )

                    SettingRow(
                        title = stringResource(id = R.string.generate_words),
                        description = {
                            NumericalSetting(
                                onDecrease = onDecreaseWords,
                                onIncrease = onIncreaseWords,
                                count = state.premiumFeatures.generatedWords,
                                isEnabled = state.isPremiumVersion
                            )
                        }
                    )
                }
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreativityDropDownMenu(
    modifier: Modifier = Modifier,
    state: EditStoryState,
    updateSelection: (EditStoryState) -> Unit,
) {

    val options =
        listOf(CreativeIndex.Inventive, CreativeIndex.Balanced, CreativeIndex.Conservative)

    var expanded by remember { mutableStateOf(false) }



    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        content = {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                content = {

                    TextField(
                        value = state.premiumFeatures.creativityIndex.name,
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = modifier.menuAnchor(),
                        textStyle = MaterialTheme.typography.bodySmall
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        content = {
                            options.forEach { item ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = item.name,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    onClick = {
                                        updateSelection(
                                            state.copy(
                                                premiumFeatures = state.premiumFeatures.copy(
                                                    creativityIndex = item
                                                )
                                            )
                                        )

                                        expanded = false
                                    }
                                )
                            }
                        }
                    )
                }
            )
        }
    )
}


@Composable
private fun SettingRow(
    modifier: Modifier = Modifier,
    title: String,
    description: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = title, style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            )
            description()
        }
    )
}


@Composable
fun NumericalSetting(
    modifier: Modifier = Modifier,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    count: Int,
    isEnabled: Boolean,
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            IconButton(
                onClick = onDecrease,
                content = {
                    Text(text = "-", modifier = modifier.size(30.sp.value.dp))
                },
                enabled = isEnabled
            )
            Text(text = count.toString(), style = MaterialTheme.typography.bodySmall)
            IconButton(
                onClick = onIncrease,
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                },
                enabled = isEnabled
            )
        }
    )

}