package com.github.florent37.runtimepermission.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.github.florent37.runtimepermission.PermissionResult;

import com.github.florent37.runtimepermission.rx.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.github.florent37.runtimepermission.sample.AppendText.appendText;

/**
 * Sample of a very basic activity asking for permission.
 * It shows a button to trigger the permission dialog if permission is needed,
 * and hide it when it doesn't
 */
public class RuntimePermissionMainActivityRx extends AppCompatActivity {

    private TextView resultView;
    private View requestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runtime_permissions_activity_main);

        resultView = findViewById(R.id.resultView);
        requestView = findViewById(R.id.requestView);

        RxView.clicks(requestView)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap($ -> new RxPermissions(this).request(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION))
                .subscribe(result -> {
                    //all permissions already granted or just granted

                    //your action
                    resultView.setText("Accepted :" + result.getAccepted());
                }, throwable -> {
                    if (throwable instanceof RxPermissions.Error) {
                        final PermissionResult result = ((RxPermissions.Error) throwable).getResult();

                        appendText(resultView, "Denied :");
                        //the list of denied permissions
                        for (String permission : result.getDenied()) {
                            appendText(resultView, permission);
                        }
                        //permission denied, but you can ask again, eg:

                            /*
                            new AlertDialog.Builder(RuntimePermissionMainActivity.this)
                                    .setMessage("Please accept our permissions")
                                    .setPositiveButton("yes", (dialog, which) -> { result.askAgain(); }) // ask again
                                    .setNegativeButton("no", (dialog, which) -> { dialog.dismiss(); })
                                    .show();
                            */

                        appendText(resultView, "ForeverDenied :");
                        //the list of forever denied permissions, user has check 'never ask again'
                        for (String permission : result.getForeverDenied()) {
                            appendText(resultView, permission);
                        }
                        // you need to open setting manually if you really need it
                        //result.goToSettings();
                    }
                });
    }
}
