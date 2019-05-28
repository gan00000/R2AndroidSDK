package com.ggr2.sdkwap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.utils.StarPyUtil;
import com.ggr2.sdkwap.login.LoginType;
import com.r2games.sdk.R2SDK;
import com.r2games.sdk.callbacks.R2Callback;
import com.r2games.sdk.entity.R2Error;
import com.r2games.sdk.entity.response.ResponseLoginData;
import com.r2games.sdk.r2api.R2SDKAPI;
import com.r2games.sdk.r2api.callback.R2APICallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GanYuanrong on 2017/2/6.
 */

public class AccountLoginMainLayout extends SLoginBaseRelativeLayout {

    private View contentView;

    private View fbLoginView, googleLoginView,guestLoginView;


    public AccountLoginMainLayout(Context context) {
        super(context);

    }


    public AccountLoginMainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AccountLoginMainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createView(Context context, LayoutInflater layoutInflater) {
        return onCreateView(layoutInflater);
    }

    public View onCreateView(LayoutInflater inflater) {

        contentView = inflater.inflate(R.layout.r2d_layout_login_main, null);

        fbLoginView =  contentView.findViewById(R.id.r2d_fb_login_btn);
        googleLoginView = contentView.findViewById(R.id.r2d_google_login_btn);

        guestLoginView = contentView.findViewById(R.id.r2d_guest_login_btn);

        fbLoginView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                R2SDK.getInstance(context).loginWithFacebookUid(activity, new R2Callback<ResponseLoginData>() {
                    @Override
                    public void onSuccess(ResponseLoginData loginData) {
                        String r2Uid = loginData.getR2Uid();
                        String timestamp = loginData.getTimestamp();
                        String sign = loginData.getSign();
                        //注意： r2Uid,timestap, sign参数 是研发服务器端验证登录
                        //数据的合法性需要的参数，具体验签规则请联系R2 SDK服务器技术人员。

                        saveLoginData(LoginType.R2GameLoginType_FB,r2Uid,timestamp,sign,false,false,false);

                    }
                    @Override
                    public void onCancel() {
                        //登录取消
                    }
                    @Override
                    public void onError(R2Error error) {
                        //登录失败
                    }
                });


            }
        });
        googleLoginView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                R2SDK.getInstance(context).loginWithGoogleAccountUid(activity, new R2Callback<ResponseLoginData>() {
                    @Override
                    public void onSuccess(ResponseLoginData loginData) {
                        String r2Uid = loginData.getR2Uid();
                        String timestamp = loginData.getTimestamp();
                        String sign = loginData.getSign();
                        //注意： r2Uid,timestap, sign参数 是研发服务器端验证登录
                        //数据的合法性需要的参数，具体验签规则请联系R2 SDK服务器技术人员。

                        saveLoginData(LoginType.R2GameLoginType_GOOGLE,r2Uid,timestamp,sign,false,false,false);
                    }
                    @Override
                    public void onCancel() {
                        //登录取消
                    }
                    @Override
                    public void onError(R2Error error) {
                        //登录失败
                    }
                });


            }
        });
        guestLoginView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                R2SDKAPI.getInstance(context.getApplicationContext()).login(activity, new R2APICallback<ResponseLoginData>() {

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

                            saveLoginData(LoginType.R2GameLoginType_GUEST,r2Uid,timestamp,sign,linked_fb,linked_google_act,linked_google_games);
                        }
                    }
                });


            }
        });


        return contentView;
    }

    private void saveLoginData(String loginType, String userId, String timestamp, String accessToken,boolean linked_fb,boolean linked_google_act,boolean linked_google_games ) {

        JSONObject loginDataJsonObject = new JSONObject();
        try {
            loginDataJsonObject.put("userId",userId);
            loginDataJsonObject.put("timestamp",timestamp);
            loginDataJsonObject.put("accessToken",accessToken);
            loginDataJsonObject.put("linked_fb",linked_fb);
            loginDataJsonObject.put("linked_google_act",linked_google_act);
            loginDataJsonObject.put("linked_google_games",linked_google_games);

            StarPyUtil.saveSdkLoginData(context,loginDataJsonObject.toString());

            StarPyUtil.savePreviousLoginType(context,loginType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
