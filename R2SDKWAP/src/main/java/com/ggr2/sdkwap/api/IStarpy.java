package com.ggr2.sdkwap.api;

import android.app.Activity;

import com.ggr2.sdkwap.base.IGameLifeCycle;

/**
 * Created by gan on 2017/2/13.
 */

public interface IStarpy extends IGameLifeCycle {

    public void initSDK(Activity activity);
    public void showLogin(Activity activity, R2Callback r2Callback);
    public void showCurrentLoginView(Activity activity);
    public void showBindView(Activity activity);
    public void showUnBindView(Activity activity);

}
