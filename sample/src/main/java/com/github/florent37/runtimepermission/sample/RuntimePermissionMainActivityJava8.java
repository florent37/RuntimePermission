package com.github.florent37.runtimepermission.sample;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.github.florent37.runtimepermission.RuntimePermission.askPermission;
import static com.github.florent37.runtimepermission.sample.AppendText.appendText;

/**
 * Sample of a very basic activity asking for permission.
 * It shows a button to trigger the permission dialog if permission is needed,
 * and hide it when it doesn't
 */
public class RuntimePermissionMainActivityJava8 extends AppCompatActivity {

    private TextView resultView;
    private View requestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runtime_permissions_activity_request);

        resultView = findViewById(R.id.resultView);
        requestView = findViewById(R.id.requestView);

        requestView.setOnClickListener(view -> myMethod());
    }

    private void myMethod() {
        askPermission(RuntimePermissionMainActivityJava8.this)
                .request(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)

                .onAccepted((result) -> {
                    //all permissions already granted or just granted

                    //your action
                    resultView.setText("Accepted :" + result.getAccepted());
                })
                .onDenied((result) -> {
                    appendText(resultView, "Denied :");
                    //the list of denied permissions
                    for (String permission : result.getDenied()) {
                        appendText(resultView, permission);
                    }
                    //permission denied, but you can ask again, eg:

                    new AlertDialog.Builder(RuntimePermissionMainActivityJava8.this)
                            .setMessage("Please accept our permissions")
                            .setPositiveButton("yes", (dialog, which) -> {
                                result.askAgain();
                            }) // ask again
                            .setNegativeButton("no", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();

                })
                .onForeverDenied((result) -> {
                    appendText(resultView, "ForeverDenied :");
                    //the list of forever denied permissions, user has check 'never ask again'
                    for (String permission : result.getForeverDenied()) {
                        appendText(resultView, permission);
                    }
                    // you need to open setting manually if you really need it
                    result.goToSettings();
                })
                .ask();
    }
}
