package com.ggr2.sdkwap.base;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by gan on 2017/2/13.
 */

public interface IGameLifeCycle {

    public void onCreate(Activity activity);

    public void onResume(Activity activity);

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data);

    public void onPause(Activity activity);
    
    public void onStop(Activity activity);

    public void onDestroy(Activity activity);

    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults);


    public void onWindowFocusChanged(Activity activity, boolean hasFocus);
}
