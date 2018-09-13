package com.github.florent37.runtimepermission.sample

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.github.florent37.runtimepermission.kotlin.askPermission
import kotlinx.android.synthetic.main.runtime_permissions_activity_request.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.runtime_permissions_activity_request)

        requestView.setOnClickListener {
            myMethod()
        }
    }

    fun myMethod() {
        val context = this
        askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) { _ ->
            Log.d("TestActivity", "accept permission WRITE_EXTERNAL_STORAGE")
        }.onDeclined { e ->
            if (e.hasDenied()) {
                Toast.makeText(context, "hasDenied", Toast.LENGTH_LONG).show()
                e.askAgain()
                return@onDeclined
            }

            if (e.hasForeverDenied()) {
                Toast.makeText(context, "hasForeverDenied", Toast.LENGTH_LONG).show()
                e.goToSettings()
            }
        }

    }

}