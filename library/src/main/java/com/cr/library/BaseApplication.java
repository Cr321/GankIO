package com.cr.library;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 *
 * @author RUI CAI
 * @date 2017/10/30
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        IMMLeaks.fixFocusedViewLeak(this);
    }
}
