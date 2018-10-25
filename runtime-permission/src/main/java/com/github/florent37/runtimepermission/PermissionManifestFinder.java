package com.github.florent37.runtimepermission;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PermissionManifestFinder {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    public static List<String> findNeededPermissionsFromManifest(Context context) {
        final PackageManager pm = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) { /* */ }

        final List<String> needed = new ArrayList<>();
        if (info != null && info.requestedPermissions != null && info.requestedPermissionsFlags != null) {
            for (int i = 0; i < info.requestedPermissions.length; i++) {
                final int flags = info.requestedPermissionsFlags[i];
                String group = null;
                try {
                    group = pm.getPermissionInfo(info.requestedPermissions[i], 0).group;
                } catch (PackageManager.NameNotFoundException e) { /* */ }
                if (((flags & PackageInfo.REQUESTED_PERMISSION_GRANTED) == 0) && group != null) {
                    needed.add(info.requestedPermissions[i]);
                }
            }
        }
        return needed;
    }

}
