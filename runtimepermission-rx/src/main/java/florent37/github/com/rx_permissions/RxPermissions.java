package florent37.github.com.rx_permissions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.sensorberg.permissionbitte.RuntimePermission;
import com.sensorberg.permissionbitte.PermissionResult;
import com.sensorberg.permissionbitte.callbacks.ResponseCallback;

import java.util.Arrays;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class RxPermissions {

    private final RuntimePermission runtimePermission;

    public RxPermissions(final FragmentActivity activity) {
        runtimePermission = RuntimePermission.askPermission(activity);
    }

    public RxPermissions(final Fragment fragment) {
        runtimePermission = RuntimePermission.askPermission(fragment.getActivity());
    }

    public Observable<PermissionResult> request(final List<String> permissions) {
        return Observable.create(new ObservableOnSubscribe<PermissionResult>() {
            @Override
            public void subscribe(final ObservableEmitter<PermissionResult> emitter) throws Exception {
                runtimePermission
                        .request(permissions)
                        .onResponse(new ResponseCallback() {
                            @Override
                            public void onResponse(PermissionResult result) {
                                if (result.isAccepted()) {
                                    emitter.onNext(result);
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new Error(result));
                                }
                            }
                        }).ask();
            }
        });
    }

    public Observable<PermissionResult> request(String... permissions) {
        return request(Arrays.asList(permissions));
    }

    public Single<PermissionResult> requestAsSingle(final String... permissions) {
        return requestAsSingle(Arrays.asList(permissions));
    }

    public Single<PermissionResult> requestAsSingle(final List<String> permissions) {
        return Single.create(new SingleOnSubscribe<PermissionResult>() {
            @Override
            public void subscribe(final SingleEmitter<PermissionResult> emitter) throws Exception {
                runtimePermission
                        .request(permissions)
                        .onResponse(new ResponseCallback() {
                            @Override
                            public void onResponse(PermissionResult result) {
                                if (result.isAccepted()) {
                                    emitter.onSuccess(result);
                                } else {
                                    emitter.onError(new Error(result));
                                }
                            }
                        }).ask();
            }
        });
    }

    public Flowable<PermissionResult> requestAsFlowable(String... permissions) {
        return requestAsFlowable(Arrays.asList(permissions));
    }

    public Flowable<PermissionResult> requestAsFlowable(final List<String> permissions) {
        return Flowable.create(new FlowableOnSubscribe<PermissionResult>() {
            @Override
            public void subscribe(final FlowableEmitter<PermissionResult> emitter) throws Exception {
                runtimePermission
                        .request(permissions)
                        .onResponse(new ResponseCallback() {
                            @Override
                            public void onResponse(PermissionResult result) {
                                if (result.isAccepted()) {
                                    emitter.onNext(result);
                                } else {
                                    emitter.onError(new Error(result));
                                }
                            }
                        }).ask();
            }
        }, BackpressureStrategy.LATEST);
    }

    public Maybe<PermissionResult> requestAsMaybe(String... permissions) {
        return requestAsMaybe(Arrays.asList(permissions));
    }

    public Maybe<PermissionResult> requestAsMaybe(final List<String> permissions) {
        return Maybe.create(new MaybeOnSubscribe<PermissionResult>() {
            @Override
            public void subscribe(final MaybeEmitter<PermissionResult> emitter) throws Exception {
                runtimePermission
                        .request(permissions)
                        .onResponse(new ResponseCallback() {
                            @Override
                            public void onResponse(PermissionResult result) {
                                if (result.isAccepted()) {
                                    emitter.onSuccess(result);
                                } else {
                                    emitter.onError(new Error(result));
                                }
                            }
                        }).ask();
            }
        });
    }

    public static class Error extends Throwable {
        private final PermissionResult result;

        private Error(PermissionResult result) {
            this.result = result;
        }

        public PermissionResult getResult() {
            return result;
        }
    }
}
