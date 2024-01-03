@file:OptIn(ExperimentalMaterial3Api::class)

package cz.mendelu.pef.examtemplate2023.ui.elements

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.examtemplate2023.ui.theme.headLine

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseScreen(
    topBarText: String?,
    onBackClick: (() -> Unit)? = null,
    showSidePadding: Boolean = true,
    drawFullScreenContent: Boolean = false,
    placeholderScreenContent: PlaceholderScreenContent? = null,
    showLoading: Boolean = false,
    floatingActionButton: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit) {

        Scaffold(
            containerColor = Color.White,
            floatingActionButton = floatingActionButton,
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(align = Alignment.CenterHorizontally)
                        ) {
                            if(topBarText != null) {
                                Text(
                                    text = topBarText,
                                    style = headLine(),
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(start = 0.dp)
                                        .weight(1.5f)
                                )
                            }
                        }
                    },
                    actions = actions,
                    navigationIcon = {
                        if (onBackClick != null) {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                )
            }
        ) {
            if (placeholderScreenContent != null){
                PlaceHolderScreen(
                    modifier = Modifier.padding(it),
                    content = placeholderScreenContent)
            } else if (showLoading){
                LoadingScreen(modifier = Modifier.padding(it))
            } else {
                if (!drawFullScreenContent) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxHeight()
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            Column(
                                verticalArrangement = Arrangement.Top,
                                modifier = Modifier
                                    .padding(if (!showSidePadding) 16.dp else 0.dp)
                            ) {
                                content(it)
                            }
                        }
                    }
                } else {
                    content(it)
                }
            }
        }

}
