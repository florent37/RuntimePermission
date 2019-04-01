package com.github.florent37.runtimepermission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;

import com.github.florent37.runtimepermission.callbacks.AcceptedCallback;
import com.github.florent37.runtimepermission.callbacks.DeniedCallback;
import com.github.florent37.runtimepermission.callbacks.ForeverDeniedCallback;
import com.github.florent37.runtimepermission.callbacks.PermissionListener;
import com.github.florent37.runtimepermission.callbacks.ResponseCallback;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RuntimePermission {

    private static final String TAG = "PERMISSION_FRAGMENT_WEEEEE";

    private final Reference<FragmentActivity> activityReference;

    //The list of permissions we want to ask
    private final List<String> permissionsToRequest = new ArrayList<>();

    //region callbacks
    private final List<ResponseCallback> responseCallbacks = new ArrayList<>();
    private final List<AcceptedCallback> acceptedCallbacks = new ArrayList<>();
    private final List<ForeverDeniedCallback> foreverDeniedCallbacks = new ArrayList<>();
    private final List<DeniedCallback> deniedCallbacks = new ArrayList<>();
    private final List<PermissionListener> permissionListeners = new ArrayList<>();

    //the listener we will give to the fragment
    private final PermissionFragment.PermissionListener listener = new PermissionFragment.PermissionListener() {
        @Override
        public void onRequestPermissionsResult(List<String> acceptedPermissions, List<String> refusedPermissions, List<String> askAgainPermissions) {
            onReceivedPermissionResult(acceptedPermissions, refusedPermissions, askAgainPermissions);
        }
    };
    //endregion

    public RuntimePermission(@Nullable final FragmentActivity activity) {
        if (activity != null) {
            this.activityReference = new WeakReference<>(activity);
        } else {
            this.activityReference = new WeakReference<>(null);
        }
    }

    /**
     * Fill permissions to only ask If we do not call this method,
     * If not set or empty, the library will find all needed permissions to ask from manifest
     * You can call .request(permissions) after this method if you want to give permissions in a separate method
     */
    public static RuntimePermission askPermission(@Nullable final FragmentActivity activity, String... permissions) {
        return new RuntimePermission(activity).request(permissions);
    }

    /**
     * Fill permissions to only ask If we do not call this method,
     * If not set or empty, the library will find all needed permissions to ask from manifest
     * You can call .request(permissions) after this method if you want to give permissions in a separate method
     */
    public static RuntimePermission askPermission(@Nullable final Fragment fragment, String... permissions) {
        @Nullable FragmentActivity activity = null;
        if(fragment != null){
            activity = fragment.getActivity();
        }
        return askPermission(activity).request(permissions);
    }

    /**
     * Just a helper methods in case the user blocks permission.
     * It goes to your application settings page for the user to enable permission again.
     */
    public void goToSettings() {
        final FragmentActivity fragmentActivity = this.activityReference.get();
        if (fragmentActivity != null) {
            fragmentActivity.startActivity(new Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", fragmentActivity.getPackageName(), null)));
        }
    }

    private void onReceivedPermissionResult(List<String> acceptedPermissions, List<String> refusedPermissions, List<String> askAgainPermissions) {
        final PermissionResult permissionResult = new PermissionResult(this, acceptedPermissions, refusedPermissions, askAgainPermissions);
        if (permissionResult.isAccepted()) {
            for (AcceptedCallback callback : acceptedCallbacks) {
                callback.onAccepted(permissionResult);
            }
            for (PermissionListener permissionListener : permissionListeners) {
                permissionListener.onAccepted(permissionResult, permissionResult.getAccepted());
            }
        }
        if (permissionResult.hasDenied()) {
            for (DeniedCallback callback : deniedCallbacks) {
                callback.onDenied(permissionResult);
            }
        }
        if (permissionResult.hasForeverDenied()) {
            for (ForeverDeniedCallback callback : foreverDeniedCallbacks) {
                callback.onForeverDenied(permissionResult);
            }
        }

        if (permissionResult.hasForeverDenied() || permissionResult.hasDenied()) {
            for (PermissionListener permissionListener : permissionListeners) {
                permissionListener.onDenied(permissionResult, permissionResult.getDenied(), permissionResult.getForeverDenied());
            }
        }

        for (ResponseCallback responseCallback : responseCallbacks) {
            responseCallback.onResponse(permissionResult);
        }
    }

    /**
     * We want to only request given permissions
     * If we do not call this method, the library will find all needed permissions to ask from manifest
     * @see android.Manifest.permission
     */
    public RuntimePermission request(@Nullable final List<String> permissions) {
        if (permissions != null) {
            permissionsToRequest.clear();
            permissionsToRequest.addAll(permissions);
        }
        return this;
    }

    /**
     * We want to only request given permissions
     *
     * @see android.Manifest.permission
     */
    public RuntimePermission request(@Nullable final String... permissions) {
        if (permissions != null) {
            return this.request(Arrays.asList(permissions));
        } else {
            return this;
        }
    }

    public RuntimePermission onResponse(@Nullable final ResponseCallback callback) {
        if (callback != null) {
            responseCallbacks.add(callback);
        }
        return this;
    }

    public RuntimePermission onResponse(@Nullable final PermissionListener permissionListener) {
        if (permissionListener != null) {
            permissionListeners.add(permissionListener);
        }
        return this;
    }

    public RuntimePermission onAccepted(@Nullable final AcceptedCallback callback) {
        if (callback != null) {
            acceptedCallbacks.add(callback);
        }
        return this;
    }

    public RuntimePermission onDenied(@Nullable final DeniedCallback callback) {
        if (callback != null) {
            deniedCallbacks.add(callback);
        }
        return this;
    }

    public RuntimePermission onForeverDenied(@Nullable final ForeverDeniedCallback callback) {
        if (callback != null) {
            foreverDeniedCallbacks.add(callback);
        }
        return this;
    }

    public void ask(@Nullable ResponseCallback responseCallback) {
        onResponse(responseCallback)
                .ask();
    }

    public void ask(@Nullable PermissionListener permissionListener) {
        onResponse(permissionListener)
                .ask();
    }

    /**
     * If we request permission using .request(names), we only ask them
     * If not, this lib will search needed permissions from Manifest
     */
    private List<String> findNeededPermissions(@NonNull Context context) {
        if (permissionsToRequest.isEmpty()) {
            return PermissionManifestFinder.findNeededPermissionsFromManifest(context);
        } else {
            return permissionsToRequest;
        }
    }

    /**
     * Ask for the permission. Which permission? Anything you register on your manifest that needs it.
     * It is safe to call this every time without querying `shouldAsk`.
     * In case you call `ask` without any permission, the method returns.
     */
    public void ask() {
        final FragmentActivity activity = activityReference.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }

        //retrieve permissions we want
        final List<String> permissions = findNeededPermissions(activity);

        // No need to ask for permissions on API levels below Android Marshmallow
        if (permissions.isEmpty() || Build.VERSION.SDK_INT < Build.VERSION_CODES.M || arePermissionsAlreadyAccepted(activity, permissions)) {
            onAllAccepted(permissions);
        } else {
            final PermissionFragment oldFragment = (PermissionFragment) activity
                    .getSupportFragmentManager()
                    .findFragmentByTag(TAG);

            if(oldFragment != null){
                oldFragment.setListener(listener);
            } else {
                final PermissionFragment newFragment = PermissionFragment.newInstance(permissions);
                newFragment.setListener(listener);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .add(newFragment, TAG)
                                .commitNowAllowingStateLoss();
                    }
                });

            }
        }
    }

    private boolean arePermissionsAlreadyAccepted(@NonNull Context context, @NonNull final List<String> permissions) {
        for (String permission : permissions) {
            final int permissionState = ContextCompat.checkSelfPermission(context, permission);
            if(permissionState == PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }

    private void onAllAccepted(@NonNull final List<String> permissions) {
        onReceivedPermissionResult(permissions, null, null);
    }

    public void askAgain() {

    }
}
