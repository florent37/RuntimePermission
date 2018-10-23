package com.github.florent37.runtimepermission.sample;

import android.Manifest;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.florent37.runtimepermission.PermissionResult;
import com.github.florent37.runtimepermission.RuntimePermission;
import com.github.florent37.runtimepermission.callbacks.PermissionListener;

import java.util.List;

import static com.github.florent37.runtimepermission.RuntimePermission.askPermission;
import static com.github.florent37.runtimepermission.sample.AppendText.appendText;

/**
 * Sample of a very basic activity asking for permission.
 * It shows a button to trigger the permission dialog if permission is needed,
 * and hide it when it doesn't
 */
public class RuntimePermissionMainActivityJava7 extends AppCompatActivity {

    private TextView resultView;
    private View request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runtime_permissions_activity_request);

        resultView = findViewById(R.id.resultView);
        request = findViewById(R.id.requestView);

        request.setOnClickListener(view -> myMethod());

        /*
        request.setOnClickListener(view -> {
            askPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .ask(new PermissionListener() {
                        @Override
                        public void onAccepted(PermissionResult permissionResult, List<String> accepted) {
                            Log.d("test", accepted.toString());
                        }

                        @Override
                        public void onDenied(PermissionResult permissionResult, List<String> denied, List<String> foreverDenied) {
                            Log.d("test", foreverDenied.toString());
                        }
                    });
        });
        */
    }

    private void myMethod() {
        askPermission(RuntimePermissionMainActivityJava7.this, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
                .ask(new PermissionListener() {
                    @Override
                    public void onAccepted(PermissionResult permissionResult, List<String> accepted) {
                        //all permissions already granted or just granted

                        //your action
                        resultView.setText("Accepted :" + accepted);
                    }

                    @Override
                    public void onDenied(PermissionResult permissionResult, List<String> denied, List<String> foreverDenied) {
                        if(!denied.isEmpty()) {
                            appendText(resultView, "Denied :");
                            //the list of denied permissions
                            for (String permission : denied) {
                                appendText(resultView, permission);
                            }

                            //permission denied, but you can ask again, eg:

                            new AlertDialog.Builder(RuntimePermissionMainActivityJava7.this)
                                    .setMessage("Please accept our permissions")
                                    .setPositiveButton("yes", (dialog, which) -> {
                                        permissionResult.askAgain();
                                    }) // ask again
                                    .setNegativeButton("no", (dialog, which) -> {
                                        dialog.dismiss();
                                    })
                                    .show();
                        }


                        if(!foreverDenied.isEmpty()) {
                            appendText(resultView, "ForeverDenied :");
                            //the list of forever denied permissions, user has check 'never ask again'
                            for (String permission : foreverDenied) {
                                appendText(resultView, permission);
                            }
                            // you need to open setting manually if you really need it
                            permissionResult.goToSettings();
                        }
                    }
                });
    }
}
