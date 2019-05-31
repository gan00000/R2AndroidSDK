package com.ggr2.sdkwap.api;

import android.app.Activity;
import android.content.Intent;

import com.core.base.utils.PL;
import com.core.base.utils.SStringUtil;
import com.core.base.utils.SignatureUtil;
import com.core.base.utils.ToastUtils;
import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.R2DDialog;
import com.ggr2.sdkwap.utils.StarPyUtil;
import com.ggr2.sdkwap.widget.AccountLoginMainLayout;
import com.ggr2.sdkwap.widget.AutoLoginLayout;
import com.ggr2.sdkwap.widget.BindAccountLayout;
import com.ggr2.sdkwap.widget.CurrentFBGGLoginLayout;
import com.ggr2.sdkwap.widget.CurrentGuestLoginLayout;
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

    private R2LoginCallback r2LoginCallbackLogin;
    private R2LogoutCallback r2LogoutCallback;

    public R2LoginCallback getR2LoginCallbackLogin() {
        return r2LoginCallbackLogin;
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
    public void showLogin(Activity activity, R2LoginCallback r2LoginCallback) {

        PL.i("fb keyhash:" + SignatureUtil.getHashKey(activity, activity.getPackageName()));
        PL.i("google sha1:" + SignatureUtil.getSignatureSHA1WithColon(activity, activity.getPackageName()));


        this.r2LoginCallbackLogin = r2LoginCallback;

        R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);

        if (SStringUtil.isEmpty(StarPyUtil.getPreviousLoginType(activity))){
            AccountLoginMainLayout accountLoginMainLayout = new AccountLoginMainLayout(activity);
            accountLoginMainLayout.setR2DDialog(r2DDialog);
            r2DDialog.setContentView(accountLoginMainLayout);
            r2DDialog.show();
        }else {

            AutoLoginLayout autoLoginLayout = new AutoLoginLayout(activity);
            autoLoginLayout.setR2DDialog(r2DDialog);
            r2DDialog.setContentView(autoLoginLayout);
            r2DDialog.show();

        }


    }

    @Override
    public void showCurrentLoginView(Activity activity, R2LogoutCallback r2LogoutCallback) {

        this.r2LogoutCallback = r2LogoutCallback;
        if (!StarPyUtil.isLogin(activity) || SStringUtil.isEmpty(StarPyUtil.getPreviousLoginType(activity))){
            ToastUtils.toast(activity,R.string.r2d_string_login_first);
            return;
        }

        if (StarPyUtil.isGuestLogin(activity)){

            R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);

            CurrentGuestLoginLayout currentGuestLoginLayout = new CurrentGuestLoginLayout(activity);
            currentGuestLoginLayout.setR2DDialog(r2DDialog);
            r2DDialog.setContentView(currentGuestLoginLayout);
            r2DDialog.show();

        }else {
            R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);
            CurrentFBGGLoginLayout currentFBGGLoginLayout = new CurrentFBGGLoginLayout(activity);
            currentFBGGLoginLayout.setR2DDialog(r2DDialog);
            r2DDialog.setContentView(currentFBGGLoginLayout);
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
    public void showUnBindView(Activity activity, R2LogoutCallback r2LogoutCallback) {

        this.r2LogoutCallback = r2LogoutCallback;

        if (!StarPyUtil.isLogin(activity) || SStringUtil.isEmpty(StarPyUtil.getPreviousLoginType(activity))){
            ToastUtils.toast(activity,R.string.r2d_string_login_first);
            return;
        }


        R2DDialog r2DDialog = new R2DDialog(activity, R.style.Starpy_Theme_AppCompat_Dialog_Notitle_Fullscreen);

        if (StarPyUtil.isGuestLogin(activity)){

            CurrentGuestLoginLayout currentGuestLoginLayout = new CurrentGuestLoginLayout(activity);
            currentGuestLoginLayout.setR2DDialog(r2DDialog);
            r2DDialog.setContentView(currentGuestLoginLayout);
            r2DDialog.show();

        }else {

            UnbindAccountLayout unbindAccountLayout = new UnbindAccountLayout(activity);
            unbindAccountLayout.setR2DDialog(r2DDialog);
            r2DDialog.setContentView(unbindAccountLayout);
            r2DDialog.show();
        }


    }

    public void logout(Activity activity){

        R2SDKAPI.getInstance(activity).logout(activity);
//        StarPyUtil.savePreviousLoginType(activity,"");
        StarPyUtil.saveSdkLoginData(activity,"");

        if (this.r2LogoutCallback == null){
            this.r2LogoutCallback.onSuccess();
        }

    }
}
