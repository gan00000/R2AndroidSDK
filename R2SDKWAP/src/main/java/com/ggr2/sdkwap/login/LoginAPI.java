package com.ggr2.sdkwap.login;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.core.base.utils.PL;
import com.core.base.utils.ToastUtils;
import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.R2DDialog;
import com.ggr2.sdkwap.api.GameSDKImpl;
import com.ggr2.sdkwap.utils.StarPyUtil;
import com.r2games.sdk.R2SDK;
import com.r2games.sdk.callbacks.R2Callback;
import com.r2games.sdk.entity.R2Error;
import com.r2games.sdk.entity.response.ResponseLoginData;
import com.r2games.sdk.r2api.R2SDKAPI;
import com.r2games.sdk.r2api.callback.R2APICallback;

public class LoginAPI {

    public static void fbLogin(final  Activity activity, final R2DDialog r2DDialog){
        R2SDK.getInstance(activity).loginWithFacebookUid(activity, new R2Callback<ResponseLoginData>() {
            @Override
            public void onSuccess(ResponseLoginData loginData) {
                String r2Uid = loginData.getR2Uid();
                String timestamp = loginData.getTimestamp();
                String sign = loginData.getSign();
                //注意： r2Uid,timestap, sign参数 是研发服务器端验证登录
                //数据的合法性需要的参数，具体验签规则请联系R2 SDK服务器技术人员。

                StarPyUtil.saveLoginData(activity,LoginType.R2GameLoginType_FB,r2Uid,timestamp,sign,false,false,false);
                loginSuccess(loginData,r2DDialog);
            }
            @Override
            public void onCancel() {
                //登录取消
                ToastUtils.toast(activity, R.string.r2d_string_login_cancel);
            }
            @Override
            public void onError(R2Error error) {
                //登录失败
                PL.e("error code:" + error.getCode() + "_desc:" + error.getDesc());
                ToastUtils.toast(activity,R.string.r2d_string_login_fail);
            }
        });
    }


    public static void googleLogin(final  Activity activity, final R2DDialog r2DDialog){

        R2SDK.getInstance(activity).loginWithGoogleAccountUid(activity, new R2Callback<ResponseLoginData>() {
            @Override
            public void onSuccess(ResponseLoginData loginData) {
                String r2Uid = loginData.getR2Uid();
                String timestamp = loginData.getTimestamp();
                String sign = loginData.getSign();
                //注意： r2Uid,timestap, sign参数 是研发服务器端验证登录
                //数据的合法性需要的参数，具体验签规则请联系R2 SDK服务器技术人员。

                StarPyUtil.saveLoginData(activity, LoginType.R2GameLoginType_GOOGLE,r2Uid,timestamp,sign,false,false,false);

                loginSuccess(loginData,r2DDialog);
            }
            @Override
            public void onCancel() {
                //登录取消
                ToastUtils.toast(activity,R.string.r2d_string_login_cancel);
            }
            @Override
            public void onError(R2Error error) {
                //登录失败
                PL.e("error code:" + error.getCode() + "_desc:" + error.getDesc());
                ToastUtils.toast(activity,R.string.r2d_string_login_fail);
            }
        });

    }


    public static void guestLogin(final  Activity activity, final R2DDialog r2DDialog){



        R2SDKAPI.getInstance(activity.getApplicationContext()).login(activity, new R2APICallback<ResponseLoginData>() {

            @Override
            public void onCompleted(int code, String msg,
                                    ResponseLoginData loginData) {
                if (R2SDKAPI.RESPONSE_OK == code) {
                    // showLogin success
                    String r2Uid = loginData.getR2Uid();
                    String timestamp = loginData.getTimestamp();
                    String sign = loginData.getSign();
                    //注意： r2Uid,timestap, sign参数是研发服务器端验证
                    //数据合法性需要的参数，具体验签规则请联系R2 SDK服务器技术人员。

                    //研发可以根据如下方法判断当前登录的r2uid是否绑定过某种第三方账号
                    boolean linked_fb = loginData.isBoundToFbAccount();
                    boolean linked_google_act = loginData.isBoundToGoogleAccount();
                    boolean linked_google_games = loginData.isBoundToGoogleGamesAccount();

                    StarPyUtil.saveLoginData(activity,LoginType.R2GameLoginType_GUEST,r2Uid,timestamp,sign,linked_fb,linked_google_act,linked_google_games);

                    showGuestTips(activity,loginData,r2DDialog);
                }else {

                    PL.e("error code:" + code + "_desc:" + msg);
                    ToastUtils.toast(activity,R.string.r2d_string_login_fail);
                }
            }
        });

    }


    static AlertDialog alertDialog;

    public static void showGuestTips(Activity activity, final ResponseLoginData loginData, final R2DDialog r2DDialog){

        View tipsView = activity.getLayoutInflater().inflate(R.layout.r2d_layout_login_guest_tips,null,false);

        r2DDialog.setContentView(tipsView);

        View tipsOk = tipsView.findViewById(R.id.guest_tips_ok);



        tipsOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertDialog != null){
                    alertDialog.dismiss();

                }

                loginSuccess(loginData,r2DDialog);

            }
        });

        r2DDialog.setContentView(tipsView);

//        alertDialog = new AlertDialog.Builder(getTheContext()).setView(tipsView).create();
//        alertDialog.setCanceledOnTouchOutside(false);
//        alertDialog.setCancelable(false);
//        alertDialog.show();

    }


    private static void loginSuccess(ResponseLoginData loginData, R2DDialog r2DDialog){
        GameSDKImpl.getInstance().setResponseLoginData(loginData);
        if (GameSDKImpl.getInstance().getR2LoginCallbackLogin() != null){
            GameSDKImpl.getInstance().getR2LoginCallbackLogin().onSuccess(loginData);
            if (r2DDialog != null) {
                r2DDialog.dismiss();
            }
        }
    }

}
