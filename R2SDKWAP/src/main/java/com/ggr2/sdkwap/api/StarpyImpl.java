package com.ggr2.sdkwap.api;

import android.app.Activity;
import android.content.Intent;

import com.core.base.utils.PL;
import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.R2DDialog;
import com.ggr2.sdkwap.utils.StarPyUtil;
import com.ggr2.sdkwap.widget.AccountLoginMainLayout;
import com.ggr2.sdkwap.widget.BindAccountLayout;
import com.ggr2.sdkwap.widget.CurrentGuestLoginLayout;
import com.ggr2.sdkwap.widget.CurrentLoginLayout;
import com.r2games.sdk.r2api.R2SDKAPI;


public class StarpyImpl implements IStarpy {


    @Override
    public void onCreate(Activity activity) {

    }

    @Override
    public void onResume(Activity activity) {

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onPause(Activity activity) {

    }

    @Override
    public void onStop(Activity activity) {

    }

    @Override
    public void onDestroy(Activity activity) {

    }

    @Override
    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {

    }

    @Override
    public void onWindowFocusChanged(Activity activity, boolean hasFocus) {

    }

    @Override
    public void initSDK(final Activity activity) {
        PL.i("IStarpy initSDK");
        //清除上一次登录成功的返回值
//        StarPyUtil.saveSdkLoginData(activity,"");

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                R2SDKAPI.getInstance(activity.getApplicationContext()).init();

            }
        });

    }


    @Override
    public void showLogin(Activity activity) {

        R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);
        r2DDialog.setContentView(new AccountLoginMainLayout(activity));
        r2DDialog.show();

    }

    @Override
    public void showCurrentLoginInfo(Activity activity) {
        if (StarPyUtil.isGuestLogin(activity)){

            R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);
            r2DDialog.setContentView(new CurrentGuestLoginLayout(activity));
            r2DDialog.show();

        }else {
            R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);
            r2DDialog.setContentView(new CurrentLoginLayout(activity));
            r2DDialog.show();
        }

    }


    @Override
    public void showBindView(Activity activity) {

        R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);
        r2DDialog.setContentView(new BindAccountLayout(activity));
        r2DDialog.show();

    }
}
