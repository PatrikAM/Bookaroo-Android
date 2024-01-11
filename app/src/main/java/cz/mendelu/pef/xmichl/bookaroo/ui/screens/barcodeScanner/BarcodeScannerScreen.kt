package cz.mendelu.pef.xmichl.bookaroo.ui.screens.barcodeScanner

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.extension.isValidIsbn
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BookarooDialog
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.PlaceholderScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BookAddEditScreenDestination

@Destination
@Composable
fun BarcodeScannerScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    libraryId: String? = null
) {
    val localContext = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(localContext)
    }

    val reload = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>("reload")

    if (reload != null) {
        navController.previousBackStackEntry?.savedStateHandle?.set("reload", reload)
        navigator.popBackStack()
    }

    var showError = remember { false }

    if (showError) {
        BookarooDialog(
            content = PlaceholderScreenContent(
                R.drawable.ic_camera,
                stringResource(R.string.sadly_this_is_not_valid_isbn)
            )
        ) {
            showError = false
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val previewView = PreviewView(context)
            val preview = Preview.Builder().build()
            val selector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            preview.setSurfaceProvider(previewView.surfaceProvider)

            val imageAnalysis = ImageAnalysis.Builder().build()
            imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(context),
                BarcodeAnalyzer(
                    context
                ) {
                    if (it.isValidIsbn()) {
                        navigator.navigate(
                            BookAddEditScreenDestination(
                                isbn = it,
                                libraryId = libraryId
                            )
                        )
                    } else {
                        showError = true
                    }
                }
            )

            runCatching {
                cameraProviderFuture.get().bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    imageAnalysis
                )
            }.onFailure {
                Log.e("CAMERA", "Camera bind error ${it.localizedMessage}", it)
            }
            previewView
        }
    )
}