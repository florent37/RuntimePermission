package com.github.florent37.runtimepermission.callbacks;

import com.github.florent37.runtimepermission.RuntimePermission;

import java.util.List;

public interface PermissionListener {
    void onAccepted(RuntimePermission runtimePermission, List<String> accepted);
    void onDenied(RuntimePermission runtimePermission, List<String> denied, List<String> foreverDenied);
}
