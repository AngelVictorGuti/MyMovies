package com.angelvictor.movies.ui.common

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class RequestPermission(fragment: Fragment, private val permission: String) {

    private var onRequest: (PermissionState) -> Unit = {}
    private var permissionState = PermissionState.DENIED
    private val launcher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val deniedList: List<String> = permissions.filter {
                !it.value
            }.map {
                it.key
            }
            when {
                deniedList.isNotEmpty() -> {
                    deniedList.groupBy {
                        permissionState = if (fragment.shouldShowRequestPermissionRationale(it)){
                            PermissionState.DENIED
                        } else {
                            PermissionState.EXPLAINED
                        }
                    }
                } else -> permissionState = PermissionState.GRANTED
            }
            onRequest(permissionState)
        }

    suspend fun request(): PermissionState =
        suspendCancellableCoroutine { continuation ->
            onRequest = {
                continuation.resume(it)
            }
            launcher.launch(arrayOf(permission))
        }
}