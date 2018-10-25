package com.github.florent37.runtimepermission.kotlin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.florent37.runtimepermission.RuntimePermission
import com.github.florent37.runtimepermission.PermissionResult

fun androidx.fragment.app.Fragment.askPermission(vararg permissions: String, acceptedblock: (PermissionResult) -> Unit): KotlinRuntimePermission {
    return KotlinRuntimePermission(RuntimePermission.askPermission(activity)
            .request(permissions.toList())
            .onAccepted(acceptedblock))
}

fun androidx.fragment.app.FragmentActivity.askPermission(vararg permissions: String, acceptedblock: (PermissionResult) -> Unit): KotlinRuntimePermission {
    return KotlinRuntimePermission(RuntimePermission.askPermission(this)
            .request(permissions.toList())
            .onAccepted(acceptedblock))
}

class KotlinRuntimePermission(var runtimePermission: RuntimePermission) {

    init {
        runtimePermission.ask()
    }

    fun onDeclined(block: ((PermissionResult) -> Unit)) : KotlinRuntimePermission {
        runtimePermission.onResponse{
            if(it.hasDenied() || it.hasForeverDenied()){
                block(it)
            }
        }
        return this
    }
}
