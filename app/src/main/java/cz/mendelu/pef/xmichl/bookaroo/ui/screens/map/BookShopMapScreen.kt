package cz.mendelu.pef.xmichl.bookaroo.ui.screens.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.map.CustomMapRenderer
import cz.mendelu.pef.xmichl.bookaroo.model.BookShop
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BookShopDetail
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BookarooDialog
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.PlaceholderScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BookShopMapScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.BookarooTheme


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun BookShopMapScreen(navigator: DestinationsNavigator) {

    val viewModel = hiltViewModel<BookShopMapViewModel>()

    val uiState: MutableState<UiState<MapData, BookShopMapErrors>> =
        rememberSaveable { mutableStateOf(UiState()) }

    val detailUiState: MutableState<UiState<BookShop, BookShopMapErrors>> =
        rememberSaveable { mutableStateOf(UiState()) }

    viewModel.uiState.value.let {
        uiState.value = it
    }

    viewModel.detailUiState.value.let {
        detailUiState.value = it
    }

    LocationPermission {
        viewModel.onPermissionError()
    }

    if (viewModel.showDialog) {
        BookarooDialog(
            content = PlaceholderScreenContent(
                image = uiState.value.image,
                text = stringResource(uiState.value.errors!!.communicationError)
            )
        ) {
            viewModel.onDialogDismiss()
        }
    }

    if (viewModel.showDetail) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.onDetailDismiss() },
//            containerColor = detailUiState.value.data!!.getColorIsOpen()
        ) {
            if (detailUiState.value.data != null) {
                BookShopDetail(bookShop = detailUiState.value.data!!)
            } else {
                Text(text = stringResource(id = detailUiState.value.errors!!.communicationError))
            }
        }
    }

//    val gpsPermissionState =
//        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
//
//    checkGPSPermsAndNavigate(
//        gpsPermissionState,
//        onShouldShowRationale = {
//            viewModel.onPermissionError()
//        }
//    ) {
//
//    }

    if (uiState.value.permissionError) {
        BookarooDialog(
            content = PlaceholderScreenContent
                (
                text = stringResource(R.string.bookaroo_needs_to_use_your_location),
                image = R.drawable.ic_camera
            )
        ) {
            viewModel.onPermissionSuccess()
        }
    }

    BaseScreen(
        topBarText = null,
        onBackClick = { navigator.popBackStack() },
        showLoading = false,
        placeholderScreenContent = null,
        drawFullScreenContent = true,
        isNavScreen = true,
        navigator = navigator,
        currentRoute = BookShopMapScreenDestination.route
    ) {
        BookShopMapScreenContent(
            paddingValues = it,
//            actions = viewModel,
            navigator = navigator,
            mapData = uiState.value.data,
            actions = viewModel
        )
    }
}

@SuppressLint("PotentialBehaviorOverride")
@OptIn(MapsComposeExperimentalApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BookShopMapScreenContent(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    actions: BookShopMapActions,
    mapData: MapData?,
) {

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false,
                mapToolbarEnabled = false
            )
        )
    }

    val context = LocalContext.current

    val location = getCurrentLocation(context)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }


    var googleMap by remember {
        mutableStateOf<GoogleMap?>(null)
    }
    var customRenderer by remember {
        mutableStateOf<CustomMapRenderer?>(null)
    }
    var manager by remember {
        mutableStateOf<ClusterManager<BookShop>?>(null)
    }

    if (mapData != null) {
        manager?.addItems(mapData.places)
        manager?.cluster()
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxHeight(),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ) {

            if (mapData != null) {
                MapEffect(mapData.places) {
                    if (googleMap == null) {
                        googleMap = it
                    }

                    if (manager == null) {
                        manager = ClusterManager(context, googleMap)
                        customRenderer = CustomMapRenderer(context, googleMap!!, manager!!)
//                        manager?.setOnClusterItemClickListener {
//                            actions.onClusterItemClick(it)
//                            true
//                        }

                        manager?.apply {
//                            algorithm = GridBasedAlgorithm()
                            renderer = customRenderer
                        }

                        manager?.addItems(mapData.places)

                    }

                    manager?.setOnClusterItemClickListener {
                        actions.onClusterItemClick(it)
                        true
                    }

                    googleMap?.setOnCameraIdleListener {
                        manager?.cluster()
                    }

                    googleMap?.setOnMarkerClickListener(manager)

                }

            }

        }

    }
}

//@ExperimentalPermissionsApi
//private fun checkGPSPermsAndNavigate(
//    gpsPermissionState: PermissionState,
//    onShouldShowRationale: () -> Unit,
//    onAllowed: () -> Unit,
//) {
//    if (gpsPermissionState.status.isGranted) {
//        onAllowed()
//    } else if (gpsPermissionState.status.shouldShowRationale) {
//        onShouldShowRationale()
//    } else {
//        gpsPermissionState.run { launchPermissionRequest() }
//    }
//}

//@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermission(
//    gpsPermissionState: PermissionState,
    onShouldShowRationale: () -> Unit,
//    onAllowed: () -> Unit,
) {
    val perm: String = android.Manifest.permission.ACCESS_FINE_LOCATION
//    if (Build.VERSION.SDK_INT > 32) {
//        perm = android.Manifest.permission.READ_MEDIA_IMAGES
//    }
    val permissionState =
        rememberPermissionState(permission = perm)
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    if (permissionState.status.shouldShowRationale) {
                        onShouldShowRationale()
                    } else {
                        permissionState.launchPermissionRequest()
                    }
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
}

@Composable
fun getCurrentLocation(context: Context): LatLng {
    val latitude = 49.0
    val longitude = 16.0

    var location = LatLng(latitude, longitude)

    val permission = Manifest.permission.ACCESS_FINE_LOCATION;
    val res = context.checkCallingOrSelfPermission(permission);
    if (res == PackageManager.PERMISSION_GRANTED) {
        getUserLocation(context = context).let {
            if (it.latitude != 0.0 && it.longitude != 0.0) {
                location = it
            }
        }
    }

    return location
}
