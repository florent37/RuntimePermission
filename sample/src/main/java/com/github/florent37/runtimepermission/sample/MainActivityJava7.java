package com.github.florent37.runtimepermission.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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
public class MainActivityJava7 extends AppCompatActivity {

    private TextView resultView;
    private View request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        resultView = findViewById(R.id.resultView);
        request = findViewById(R.id.requestView);

        request.setOnClickListener(view -> myMethod());
    }

    private void myMethod() {
        askPermission(MainActivityJava7.this, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
                .ask(new PermissionListener() {
                    @Override
                    public void onAccepted(RuntimePermission runtimePermission, List<String> accepted) {
                        //all permissions already granted or just granted

                        //your action
                        resultView.setText("Accepted :"+accepted);
                    }

                    @Override
                    public void onDenied(RuntimePermission runtimePermission, List<String> denied, List<String> foreverDenied) {
                        appendText(resultView, "Denied :");
                        //the list of denied permissions
                        for (String permission : denied) {
                            appendText(resultView, permission);
                        }
                        //permission denied, but you can ask again, eg:

                            /*
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("Please accept our permissions")
                                    .setPositiveButton("yes", (dialog, which) -> { result.askAgain(); }) // ask again
                                    .setNegativeButton("no", (dialog, which) -> { dialog.dismiss(); })
                                    .show();
                            */

                        appendText(resultView, "ForeverDenied :");
                        //the list of forever denied permissions, user has check 'never ask again'
                        for (String permission : foreverDenied) {
                            appendText(resultView, permission);
                        }
                        // you need to open setting manually if you really need it
                        //runtimePermission.goToSettings();
                    }
                });
    }
}
