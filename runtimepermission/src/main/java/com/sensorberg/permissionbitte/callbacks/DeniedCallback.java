package com.sensorberg.permissionbitte.callbacks;

import com.sensorberg.permissionbitte.PermissionResult;

public interface DeniedCallback {
    void onDenied(PermissionResult result);
}
