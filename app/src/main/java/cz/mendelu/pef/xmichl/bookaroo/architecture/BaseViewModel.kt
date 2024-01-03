package cz.mendelu.pef.xmichl.bookaroo.architecture

import androidx.lifecycle.ViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    @ExperimentalPermissionsApi
    private fun checkPermsAndNavigate(
        permissionState: PermissionState,
        onShouldShowRationale: () -> Unit,
        onAllowed: () -> Unit,
    ) {
        if (permissionState.status.isGranted) {
            onAllowed()
        } else if (permissionState.status.shouldShowRationale) {
            onShouldShowRationale()
        } else {
            permissionState.run { launchPermissionRequest() }
        }
    }
}
