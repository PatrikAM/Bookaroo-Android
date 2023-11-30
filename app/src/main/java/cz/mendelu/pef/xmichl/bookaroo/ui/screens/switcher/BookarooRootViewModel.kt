package cz.mendelu.pef.xmichl.bookaroo.ui.screens.switcher

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.xmichl.bookaroo.architecture.BaseViewModel
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.model.Reader
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.login.LoginErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookarooRootViewModel
@Inject constructor(
    private val dataStoreRepo: DataStoreRepositoryImpl
) : BaseViewModel() {

    val uiState: MutableState<UiState<String, LoginErrors>> =
        mutableStateOf(UiState())

    val data: BookarooRootData = BookarooRootData()

    init {
        launch {
            data.token = dataStoreRepo.getUserToken()
            if (data.token != null) {
                uiState.value = UiState(
                    loading = false,
                    data = data.token,
                    errors = null
                )
            } else {
                uiState.value = UiState(
                    loading = false,
                    data = null,
                    errors = null
                )
//            }
            }
        }
    }
}