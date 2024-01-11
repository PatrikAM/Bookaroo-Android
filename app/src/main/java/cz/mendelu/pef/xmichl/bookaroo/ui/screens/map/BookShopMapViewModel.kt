package cz.mendelu.pef.xmichl.bookaroo.ui.screens.map

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.architecture.BaseViewModel
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.places.IPlacesRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.BookShop
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookShopMapViewModel
@Inject constructor(
    private val repository: IPlacesRemoteRepository
) : BaseViewModel(), BookShopMapActions {

    var bookShopsFetched = false

    val uiState: MutableState<UiState<MapData, BookShopMapErrors>> = mutableStateOf(
        UiState(loading = false)
    )

    val detailUiState: MutableState<UiState<BookShop, BookShopMapErrors>> = mutableStateOf(
        UiState(loading = false)
    )

    var location = LatLng(49.20938519271039, 16.61466699693654)
//    var location = LatLng(41.0, 16.61466699693654)

    private var mapData = MapData()

    var showDialog: Boolean = false

    var showDetail: Boolean = false

//    init {
//        fetchPlaces()
//    }

    fun onLocationChange(location: LatLng) {
        if (!bookShopsFetched) {
            this.location = location
            bookShopsFetched = true
            fetchPlaces()

        }

    }

    fun fetchPlaces() {

        launch {
            when (
                val result = repository.getBookStores(location)
            ) {
                is CommunicationResult.CommunicationError -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = BookShopMapErrors(R.string.no_internet_connection),
                        image = R.drawable.ic_connection,
                        permissionError = uiState.value.permissionError
                    )
                }

                is CommunicationResult.Error -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = BookShopMapErrors(R.string.failed_to_fetch_bookshops),
                        permissionError = uiState.value.permissionError,
                        image = uiState.value.image
                    )
                }

                is CommunicationResult.Exception -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = BookShopMapErrors(R.string.unknown_error),
                        permissionError = uiState.value.permissionError,
                        image = uiState.value.image
                    )
                }

                is CommunicationResult.Success -> {
                    uiState.value = UiState(
                        loading = false,
                        data = MapData(result.data.results),
                        errors = null,
                        permissionError = uiState.value.permissionError,
                        image = uiState.value.image
                    )
                }
            }
            showDialog = uiState.value.errors != null
            mapDataChanged()
        }
    }

    fun onDialogDismiss() {
        showDialog = false
        mapDataChanged()
    }

    fun mapDataChanged() {
        uiState.value = UiState(
            loading = uiState.value.loading,
            data = uiState.value.data,
            errors = uiState.value.errors,
            image = uiState.value.image,
            actionDone = uiState.value.actionDone,
            permissionError = uiState.value.permissionError
        )
    }

    fun onPermissionError() {
        uiState.value.permissionError = true
        mapDataChanged()
    }

    fun onPermissionSuccess() {
        uiState.value.permissionError = false
        mapDataChanged()
    }

    override fun onClusterItemClick(item: BookShop) {
        showDetail = true
        detailUiState.value = UiState(
            loading = false,
            data = item
        )

        launch {
            when (
                val result = repository.getBookStoreDetail(item.place_id!!)
            ) {
                is CommunicationResult.CommunicationError -> {
                    detailUiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = BookShopMapErrors(R.string.no_internet_connection),
                        image = R.drawable.ic_connection,
                        permissionError = uiState.value.permissionError
                    )
                }

                is CommunicationResult.Error -> {
                    detailUiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = BookShopMapErrors(R.string.failed_to_fetch_bookshops),
                        permissionError = uiState.value.permissionError,
                        image = uiState.value.image
                    )
                }

                is CommunicationResult.Exception -> {
                    detailUiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = BookShopMapErrors(R.string.unknown_error),
                        permissionError = uiState.value.permissionError,
                        image = uiState.value.image
                    )
                }

                is CommunicationResult.Success -> {
                    detailUiState.value = UiState(
                        loading = false,
                        data = result.data.result,
                        errors = null,
                        permissionError = uiState.value.permissionError,
                        image = uiState.value.image
                    )
                }
            }
        }
    }

    override fun onDetailDismiss() {
        showDetail = false
        detailUiState.value = UiState(loading = false)
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationCallback?.let {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

}
