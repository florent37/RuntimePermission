package com.github.florent37.runtimepermission.sample

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.github.florent37.runtimepermission.RuntimePermission
import com.github.florent37.runtimepermission.callbacks.AcceptedCallback
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.github.florent37.runtimepermission.kotlin.coroutines.experimental.askPermission
import kotlinx.android.synthetic.main.fragment_runtime_fragment_in.*
import kotlinx.android.synthetic.main.fragment_runtime_fragment_in.resultView

class RuntimePermissionInFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_runtime_fragment_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myMethod()

        requestView.setOnClickListener {
            myMethod()
        }
    }

    fun myMethod() {
        askPermission(android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.ACCESS_FINE_LOCATION) {
            //all permissions already granted or just granted
            //your action
            resultView.setText("Accepted :${it.accepted}")
        }.onDeclined { e ->
            if (e.hasDenied()) {
                AppendText.appendText(resultView, "Denied :")
                //the list of denied permissions
                e.denied.forEach {
                    AppendText.appendText(resultView, it)
                }

                AlertDialog.Builder(requireContext())
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
            }

            if (e.hasForeverDenied()) {
                AppendText.appendText(resultView, "ForeverDenied :")
                //the list of forever denied permissions, user has check 'never ask again'
                e.foreverDenied.forEach {
                    AppendText.appendText(resultView, it)
                }
                // you need to open setting manually if you really need it
                e.goToSettings();
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                RuntimePermissionInFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
