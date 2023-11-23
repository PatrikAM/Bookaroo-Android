package cz.mendelu.pef.xmichl.bookaroo.ui.screens.libraryAddEdit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(route = "library")
@Composable
fun AddEditLibraryScreen(
    navigator: DestinationsNavigator
) {
    Text(text = "add edit lib")
}
