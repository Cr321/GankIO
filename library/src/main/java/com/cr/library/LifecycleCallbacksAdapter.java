package com.cr.library;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
/**
 * Helper to avoid implementing all lifecycle callback methods.
 * @author RUI CAI
 * @date 2017/11/02
 */

public class LifecycleCallbacksAdapter implements Application.ActivityLifecycleCallbacks {
    @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override public void onActivityStarted(Activity activity) {

    }

    @Override public void onActivityResumed(Activity activity) {

    }

    @Override public void onActivityPaused(Activity activity) {

    }

    @Override public void onActivityStopped(Activity activity) {

    }

    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override public void onActivityDestroyed(Activity activity) {

    }
}
