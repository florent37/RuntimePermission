package com.sensorberg.permissionbitte.sample

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.sensorberg.permissionbitte.sample.AppendText.appendText
import florent37.github.com.kotlin_permissions.askPermission
import kotlinx.android.synthetic.main.activity_request.*

/**
 * Sample of a very basic activity asking for permission.
 * It shows a button to trigger the permission dialog if permission is needed,
 * and hide it when it doesn't
 */
class MainActivityKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        requestView.setOnClickListener {
            myMethod()
        }
    }

    fun myMethod(){
        askPermission(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION){
            //all permissions already granted or just granted
            //your action
            resultView.setText("Accepted :${it.accepted}")
        }.onDeclined { e ->
            appendText(resultView, "Denied :")
            //the list of denied permissions
            e.denied.forEach{
                appendText(resultView, it)
            }
            /*
            AlertDialog.Builder(this@MainActivityKotlinCoroutine)
                    .setMessage("Please accept our permissions")
                    .setPositiveButton("yes", (dialog, which) -> { result.askAgain(); }) //ask again
                    .setNegativeButton("no", (dialog, which) -> { dialog.dismiss(); })
                    .show();
            */

            appendText(resultView, "ForeverDenied :")
            //the list of forever denied permissions, user has check 'never ask again'
            e.foreverDenied.forEach{
                appendText(resultView, it)
            }
            // you need to open setting manually if you really need it
            //e.goToSettings();
        }
    }
}