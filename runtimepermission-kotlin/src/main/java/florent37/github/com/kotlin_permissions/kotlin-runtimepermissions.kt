package florent37.github.com.kotlin_permissions

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.sensorberg.permissionbitte.RuntimePermission
import com.sensorberg.permissionbitte.PermissionResult

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
