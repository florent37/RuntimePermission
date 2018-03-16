package com.github.florent37.runtimepermission.kotlin

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.github.florent37.runtimepermission.RuntimePermission
import com.github.florent37.runtimepermission.PermissionResult

fun Fragment.askPermission(vararg permissions: String, block: (PermissionResult) -> Unit): KotlinRuntimePermission {
    return KotlinRuntimePermission(RuntimePermission.askPermission(activity)
            .request(permissions.toList())
            .onResponse(block))
}

fun FragmentActivity.askPermission(vararg permissions: String, block: (PermissionResult) -> Unit): KotlinRuntimePermission {
    return KotlinRuntimePermission(RuntimePermission.askPermission(this)
            .request(permissions.toList())
            .onResponse(block))
}

class KotlinRuntimePermission(private val runtimePermission: RuntimePermission) {

    var declinedBlock: ((PermissionResult) -> Unit)? = null

    fun onDeclined(block: ((PermissionResult) -> Unit)) : KotlinRuntimePermission {
        this.declinedBlock = block
        runtimePermission.ask()
        return this
    }
}
