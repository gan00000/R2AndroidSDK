package com.ggr2.sdkwap.api;

import android.app.Activity;
import android.content.Intent;

import com.core.base.utils.PL;
import com.core.base.utils.ToastUtils;
import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.R2DDialog;
import com.ggr2.sdkwap.login.LoginType;
import com.ggr2.sdkwap.utils.StarPyUtil;
import com.ggr2.sdkwap.widget.AccountLoginMainLayout;
import com.ggr2.sdkwap.widget.BindAccountLayout;
import com.ggr2.sdkwap.widget.CurrentGuestLoginLayout;
import com.ggr2.sdkwap.widget.CurrentLoginLayout;
import com.ggr2.sdkwap.widget.UnbindAccountLayout;
import com.r2games.sdk.entity.response.ResponseLoginData;
import com.r2games.sdk.r2api.R2SDKAPI;


public class StarpyImpl implements IStarpy {

    private static StarpyImpl starpy;

    private ResponseLoginData responseLoginData;

    public ResponseLoginData getResponseLoginData() {
        return responseLoginData;
    }

    public void setResponseLoginData(ResponseLoginData responseLoginData) {
        this.responseLoginData = responseLoginData;
    }

    private StarpyImpl() {
    }

    public static StarpyImpl getInstance(){
        if (starpy == null){
            starpy = new StarpyImpl();
        }
        return starpy;
    }

    private R2Callback r2CallbackLogin;

    public R2Callback getR2CallbackLogin() {
        return r2CallbackLogin;
    }

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
    public void showLogin(Activity activity, R2Callback r2Callback) {

        this.r2CallbackLogin = r2Callback;
        R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);
        AccountLoginMainLayout accountLoginMainLayout = new AccountLoginMainLayout(activity);
        accountLoginMainLayout.setR2DDialog(r2DDialog);
        r2DDialog.setContentView(accountLoginMainLayout);
        r2DDialog.show();

    }

    @Override
    public void showCurrentLoginView(Activity activity) {
        if (StarPyUtil.isGuestLogin(activity)){

            R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);

            CurrentGuestLoginLayout currentGuestLoginLayout = new CurrentGuestLoginLayout(activity);
            currentGuestLoginLayout.setR2DDialog(r2DDialog);
            r2DDialog.setContentView(currentGuestLoginLayout);
            r2DDialog.show();

        }else {
            R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);
            CurrentLoginLayout currentLoginLayout = new CurrentLoginLayout(activity);
            currentLoginLayout.setR2DDialog(r2DDialog);
            r2DDialog.setContentView(currentLoginLayout);
            r2DDialog.show();
        }

    }


    @Override
    public void showBindView(Activity activity) {

        R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);
        BindAccountLayout bindAccountLayout = new BindAccountLayout(activity);
        bindAccountLayout.setR2DDialog(r2DDialog);
        r2DDialog.setContentView(bindAccountLayout);
        r2DDialog.show();

    }

    @Override
    public void showUnBindView(Activity activity) {

        if (LoginType.R2GameLoginType_GUEST.equals(StarPyUtil.getPreviousLoginType(activity))){
            ToastUtils.toast(activity,R.string.r2d_string_unbind_tips);
            return;
        }

        R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);
        UnbindAccountLayout unbindAccountLayout = new UnbindAccountLayout(activity);
        unbindAccountLayout.setR2DDialog(r2DDialog);
        r2DDialog.setContentView(unbindAccountLayout);
        r2DDialog.show();
    }

    public void logout(Activity activity){

        R2SDKAPI.getInstance(activity).logout(activity);

    }
}
