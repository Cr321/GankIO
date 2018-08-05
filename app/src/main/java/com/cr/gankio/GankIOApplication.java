package com.cr.gankio;

import com.blankj.utilcode.util.Utils;
import com.cr.library.BaseApplication;

/**
 * Created by RUI CAI on 2017/10/30.
 */

public class GankIOApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
