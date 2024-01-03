package cz.mendelu.pef.examtemplate2023.ui.screens.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.examtemplate2023.R
import cz.mendelu.pef.examtemplate2023.architecture.BaseViewModel
import cz.mendelu.pef.examtemplate2023.architecture.CommunicationResult
import cz.mendelu.pef.examtemplate2023.communication.ip.IRemoteRepository
import cz.mendelu.pef.examtemplate2023.communication.location.LocIRemoteRepository
import cz.mendelu.pef.examtemplate2023.model.IP
import cz.mendelu.pef.examtemplate2023.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel
@Inject constructor(
    private val ipRepository: IRemoteRepository,
    private val locRepository: LocIRemoteRepository,
) : BaseViewModel() {


    val uiState: MutableState<UiState<IP, IPErrors>> =
        mutableStateOf(UiState())

    init {
        fetchIP()
    }

    fun fetchIPLoc(ip: String) {
        launch {
            when (val result =
                locRepository.fetchLoc("78.128.147.194")
            ) {
                is CommunicationResult.CommunicationError -> {
                    uiState.value = UiState(
                        loading = false,
                        data = uiState.value.data,
                        errors = IPErrors(R.string.no_internet_connection),
//                        image = R.drawable.ic_connection
                    )
                }

                is CommunicationResult.Error -> {
                    uiState.value = UiState(
                        loading = false,
                        data = uiState.value.data,
                        errors = IPErrors(R.string.failed_to_fetch)
                    )
                }

                is CommunicationResult.Exception -> {
                    uiState.value = UiState(
                        loading = false,
                        data = uiState.value.data,
                        errors = IPErrors(R.string.unknown_error)
                    )
                }

                is CommunicationResult.Success -> {
                    uiState.value = UiState(
                        loading = false,
                        data = result.data,
                        errors = null
                    )
                }
            }
        }
    }

    private fun fetchIP() {
        launch {
            when (val result =
                ipRepository.fetchIp()
            ) {
                is CommunicationResult.CommunicationError -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = IPErrors(R.string.no_internet_connection),
//                        image = R.drawable.ic_connection
                    )
                }

                is CommunicationResult.Error -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = IPErrors(R.string.failed_to_fetch)
                    )
                }

                is CommunicationResult.Exception -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = IPErrors(R.string.unknown_error)
                    )
                }

                is CommunicationResult.Success -> {
                    uiState.value = UiState(
                        loading = false,
                        data = result.data,
                        errors = null
                    )
                    result.data.ip?.let { fetchIPLoc(it) }
                }
            }
        }
    }



}
