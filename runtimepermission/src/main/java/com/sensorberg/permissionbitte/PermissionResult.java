package com.sensorberg.permissionbitte;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PermissionResult {

    private final RuntimePermission runtimePermission;
    @NonNull
    private final List<String> accepted = new ArrayList<>();
    @NonNull
    private final List<String> foreverDenied = new ArrayList<>();
    @NonNull
    private final List<String> denied = new ArrayList<>();

    protected PermissionResult(RuntimePermission runtimePermission, @Nullable final List<String> accepted, @Nullable final List<String> foreverDenied, @Nullable final List<String> denied) {
        this.runtimePermission = runtimePermission;
        if (accepted != null) {
            this.accepted.addAll(accepted);
        }
        if (foreverDenied != null) {
            this.foreverDenied.addAll(foreverDenied);
        }
        if (denied != null) {
            this.denied.addAll(denied);
        }
    }

    public void askAgain(){
        runtimePermission.ask();
    }

    public boolean isAccepted(){
        return foreverDenied.isEmpty() && denied.isEmpty();
    }

    @NonNull
    public RuntimePermission getRuntimePermission() {
        return runtimePermission;
    }

    public void goToSettings() {
        runtimePermission.goToSettings();
    }

    public boolean hasDenied(){
        return !denied.isEmpty();
    }

    public boolean hasForeverDenied(){
        return !foreverDenied.isEmpty();
    }

    @NonNull
    public List<String> getAccepted() {
        return accepted;
    }

    @NonNull
    public List<String> getForeverDenied() {
        return foreverDenied;
    }

    @NonNull
    public List<String> getDenied() {
        return denied;
    }
}
