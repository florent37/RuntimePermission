package com.sensorberg.permissionbitte.callbacks;

import com.sensorberg.permissionbitte.RuntimePermission;

import java.util.List;

public interface PermissionListener {
    void onAccepted(RuntimePermission runtimePermission, List<String> accepted);
    void onDenied(RuntimePermission runtimePermission, List<String> denied, List<String> foreverDenied);
}
