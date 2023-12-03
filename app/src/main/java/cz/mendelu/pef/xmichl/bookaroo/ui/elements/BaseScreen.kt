package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.ListOfBooksScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.ListOfLibrariesScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.basicMargin
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.basicTextColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getBackgroundColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor

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
    isNavScreen: Boolean = false,
    navigator: DestinationsNavigator? = null,
    currentRoute: String? = null,
    floatingActionButton: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {

    Scaffold(
        contentColor = getTintColor(),
        containerColor = getBackgroundColor(),
        floatingActionButton = floatingActionButton,
        topBar = {
            if (topBarText != null) {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = getBackgroundColor()),
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(align = Alignment.CenterHorizontally)
                        ) {
                            if (topBarText != null) {
                                Text(
                                    text = topBarText,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = basicTextColor(),
                                    modifier = Modifier
                                        .padding(start = 0.dp)
                                        .weight(1.5f),
                                    overflow = TextOverflow.Ellipsis
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
                                    contentDescription = stringResource(R.string.back),
                                    tint = getTintColor()
                                )
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (isNavScreen)
                NavigationBar {
                    listOf(
                        NavItem.Libraries,
                        NavItem.BookStores,
                        NavItem.Books,
                    ).forEach { navItem ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = "hello"
                                )
                            },
                            label = {
                                if (currentRoute == navItem.destination) {
                                    Text(
                                        text = stringResource(navItem.title),
                                    )
                                }
                            },
                            selected = currentRoute == navItem.destination,
                            onClick = {
                                if (currentRoute != navItem.destination) {
                                    navigator?.navigate(navItem.destination)
                                }
                            },
                            alwaysShowLabel = false
                        )
                    }
                }
        },

        ) {
        if (placeholderScreenContent != null) {
            PlaceHolderScreen(
                modifier = Modifier.padding(it),
                content = placeholderScreenContent
            )
        } else if (showLoading) {
            LoadingScreen(modifier = Modifier.padding(it))
        } else {
            if (!drawFullScreenContent) {
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
//                        .fillMaxWidth()
                ) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .padding(if (!showSidePadding) basicMargin() else 0.dp)
//                                    .fillParentMaxWidth()
//                                horizontalAlignment = Alignment.CenterHorizontally
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

enum class NavItem(val destination: String, @StringRes val title: Int, val icon: ImageVector) {
    Libraries(
        ListOfLibrariesScreenDestination.route,
        R.string.libraries,
        Icons.Filled.LibraryBooks
    ),
    BookStores(
        ListOfLibrariesScreenDestination.route,
        R.string.book_stores,
        Icons.Filled.Store
    ),
    Books(
        ListOfBooksScreenDestination.route,
        R.string.books,
        Icons.Filled.MenuBook
    ),
}
