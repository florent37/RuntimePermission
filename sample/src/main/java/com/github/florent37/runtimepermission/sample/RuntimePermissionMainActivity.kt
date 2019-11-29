package com.github.florent37.runtimepermission.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.florent37.runtimepermission.PermissionFragment
import com.github.florent37.runtimepermission.RuntimePermission
import kotlinx.android.synthetic.main.runtime_permissions_activity_main.*

/**
 * Sample of a very basic activity asking for permission.
 * It shows a button to trigger the permission dialog if permission is needed,
 * and hide it when it doesn't
 */
class RuntimePermissionMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.runtime_permissions_activity_main)

        rx.setOnClickListener {
            open(RuntimePermissionMainActivityRx::class.java)
        }

        java7.setOnClickListener {
            open(RuntimePermissionMainActivityJava7::class.java)
        }

        java8.setOnClickListener {
            open(RuntimePermissionMainActivityJava8::class.java)
        }

        kotlin.setOnClickListener {
            open(RuntimePermissionMainActivityKotlin::class.java)
        }

        kotlinCoroutine.setOnClickListener {
            open(RuntimePermissionMainActivityKotlinCoroutine::class.java)
        }

        kotlinCoroutine.setOnClickListener {
            open(RuntimePermissionMainActivityKotlinCoroutine::class.java)
        }

        fragment.setOnClickListener{
            supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, RuntimePermissionInFragment.newInstance())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
        }


        openSettings.setOnClickListener {
            RuntimePermission(this).goToSettings()
        }
    }

    private fun open(classActivity: Class<*>) {
        startActivity(Intent(this, classActivity));
    }
}
