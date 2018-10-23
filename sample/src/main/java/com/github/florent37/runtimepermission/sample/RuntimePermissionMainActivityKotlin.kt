package com.github.florent37.runtimepermission.sample

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.github.florent37.runtimepermission.sample.AppendText.appendText
import kotlinx.android.synthetic.main.runtime_permissions_activity_request.*

/**
 * Sample of a very basic activity asking for permission.
 * It shows a button to trigger the permission dialog if permission is needed,
 * and hide it when it doesn't
 */
class RuntimePermissionMainActivityKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.runtime_permissions_activity_request)

        requestView.setOnClickListener {
            myMethod()
        }
    }

    fun myMethod() {
        askPermission(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION) {
            //all permissions already granted or just granted
            //your action
            resultView.setText("Accepted :${it.accepted}")
        }.onDeclined { e ->
            if (e.hasDenied()) {
                appendText(resultView, "Denied :")
                //the list of denied permissions
                e.denied.forEach {
                    appendText(resultView, it)
                }

                AlertDialog.Builder(this@RuntimePermissionMainActivityKotlin)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
            }

            if(e.hasForeverDenied()) {
                appendText(resultView, "ForeverDenied :")
                //the list of forever denied permissions, user has check 'never ask again'
                e.foreverDenied.forEach {
                    appendText(resultView, it)
                }
                // you need to open setting manually if you really need it
                e.goToSettings();
            }
        }
    }
}