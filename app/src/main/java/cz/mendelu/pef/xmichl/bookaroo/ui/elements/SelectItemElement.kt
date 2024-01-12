package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import cz.mendelu.pef.xmichl.bookaroo.testTags.BooksTestTags
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.basicMargin

@Composable
fun SelectItemElement(
    label: String,
    enabled: Boolean = true,
    selectedId: String? = null,
    value: String? = null,
    modifier: Modifier = Modifier,
    items: List<Library>,
    onNewCreate: (String) -> Unit = {},
    onItemSelected: (id: String, item: Library) -> Unit,
    isLibraryCreationDone: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    var newValExpanded by remember { mutableStateOf(false) }
    var newLibrary by remember { mutableStateOf("") }

    newValExpanded = !isLibraryCreationDone && newValExpanded

    Box(
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        OutlinedTextField(
            label = { Text(label) },
            value = value ?: "",
            enabled = enabled,
            modifier = modifier.fillMaxWidth().testTag(BooksTestTags.TestTagBookLibraryTextField),
            trailingIcon = {
//                val icon = expanded.select(Icons.Filled.ArrowDropUp, Icons.Filled.ArrowDropDown)
                Icon(Icons.Default.ExpandMore, "")
            },
            onValueChange = { },
            readOnly = true,
        )

        // Transparent clickable surface on top of OutlinedTextField
        Surface(
            modifier = Modifier
                .testTag(BooksTestTags.TestTagBookLibSelector)
                .fillMaxSize()
                .padding(top = 8.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable(enabled = enabled) { expanded = true },
            color = Color.Transparent,
        ) {}
    }

    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false },
        ) {
//            MyTheme {
            Surface(
                shape = RoundedCornerShape(12.dp),
            ) {
                val listState = rememberLazyListState()
//                    if (selectedId != null) {
//                        LaunchedEffect("ScrollToSelected") {
//                            listState.scrollToItem(index = selectedId)
//                        }
//                    }

                LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
//                        if (notSetLabel != null) {
//                            item {
//                                LargeDropdownMenuItem(
//                                    text = notSetLabel,
//                                    selected = false,
//                                    enabled = false,
//                                    onClick = { },
//                                )
//                            }
//                        }
                    item {

                        Column {

                            LargeDropdownMenuItem(
                                text = stringResource(R.string.create_new_library),
                                selected = false,
                                enabled = true,
                                leadingIcon = Icons.Default.Add
                            ) {
                                newValExpanded = true
                            }

                            HorizontalLine()
                        }
                    }

                    items.forEach {
                        item {
                            val selectedItem = it.id == selectedId
                            it.name?.let { it1 ->
                                LargeDropdownMenuItem(
                                    text = it1,
                                    selected = selectedItem,
                                    enabled = true,
                                    modifierOption = Modifier
                                        .testTag(
                                            BooksTestTags.TestTagBookLibraryOption + it.id
                                        )
                                ) {
                                    onItemSelected(it.id, it)
                                    expanded = false
                                }
                            }

//                            if (index < items.lastIndex) {
//                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
//                            }
                        }
                    }
                }
//                }
            }
        }
    }

    if (newValExpanded) {
        Dialog(
            onDismissRequest = { newValExpanded = false },
        ) {
//            MyTheme {
            Surface(
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 42.dp, end = 42.dp)
                        .padding(top = 42.dp, bottom = 42.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Create new item")
                    OutlinedTextField(
                        value = newLibrary,
                        onValueChange = {
                            newLibrary = it
                        },
                        label = {
                            Text(stringResource(R.string.name))
                        },
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    Spacer(modifier = Modifier.height(basicMargin()))
                    Button(onClick = {
                        onNewCreate(newLibrary)
                        newLibrary = ""
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
fun LargeDropdownMenuItem(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    leadingIcon: ImageVector? = null,
    modifierOption: Modifier = Modifier,
    onClick: () -> Unit,
    ) {
    val contentColor = when {
        !enabled -> MaterialTheme.colorScheme.onSurface
        selected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface
    }

    CompositionLocalProvider(LocalContentColor provides contentColor) {

        Box(modifier = modifierOption
            .clickable(enabled) { onClick() }
            .fillMaxWidth()
            .padding(16.dp)) {
            Row {
                if (leadingIcon != null) {
                    Icon(
                        leadingIcon,
                        contentDescription = null
                    )
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}
