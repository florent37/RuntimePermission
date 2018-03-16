package com.github.florent37.runtimepermission.callbacks;

import com.github.florent37.runtimepermission.PermissionResult;

public interface AcceptedCallback {
    void onAccepted(PermissionResult result);
}
