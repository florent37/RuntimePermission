package com.sensorberg.permissionbitte.callbacks;

import com.sensorberg.permissionbitte.PermissionResult;

public interface ForeverDeniedCallback {
    void onForeverDenied(PermissionResult result);
}
