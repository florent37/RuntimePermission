package com.github.florent37.runtimepermission.callbacks;

import com.github.florent37.runtimepermission.PermissionResult;

public interface DeniedCallback {
    void onDenied(PermissionResult result);
}
