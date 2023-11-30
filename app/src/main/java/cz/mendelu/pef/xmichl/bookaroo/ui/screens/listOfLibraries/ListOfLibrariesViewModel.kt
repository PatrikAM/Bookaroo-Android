package cz.mendelu.pef.xmichl.bookaroo.ui.screens.listOfLibraries

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.architecture.BaseViewModel
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.library.ILibraryRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.login.LoginErrors
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.switcher.BookarooRootData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfLibrariesViewModel
@Inject constructor(
    private val repository: ILibraryRemoteRepository,
) : BaseViewModel(), IListOfLibrariesActions {

    val uiState: MutableState<UiState<List<Library>, ListOfLibrariesErrors>> =
        mutableStateOf(UiState())

    val data: BookarooRootData = BookarooRootData()

    init {
        fetchLibraries()
    }

    fun refreshLibraries() {
        uiState.value = UiState(
            loading = true,
            data = null,
            errors = null
        )
        fetchLibraries()
    }


    override fun fetchLibraries() {
        launch {
            when (val result =
                repository.fetchLibraries() ) {
                is CommunicationResult.CommunicationError -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = ListOfLibrariesErrors(R.string.no_internet_connection)
                    )
                }

                is CommunicationResult.Error -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = ListOfLibrariesErrors(R.string.failed_to_log_in)
                    )
                }

                is CommunicationResult.Exception -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = ListOfLibrariesErrors(R.string.unknown_error)
                    )
                }

                is CommunicationResult.Success -> {
                    uiState.value = UiState(
                        loading = true,
                        data = result.data,
                        errors = null
                    )
                }
            }
        }
    }

}