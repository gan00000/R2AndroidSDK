package com.ggr2.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.core.base.utils.PL;
import com.core.base.utils.ToastUtils;
import com.ggr2.sdkwap.api.IGameSDK;
import com.ggr2.sdkwap.api.R2LoginCallback;
import com.ggr2.sdkwap.api.R2LogoutCallback;
import com.ggr2.sdkwap.api.GameSDKFactory;
import com.r2games.sdk.entity.response.ResponseLoginData;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;


    private IGameSDK iGameSDK;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.demo_login);

        this.activity = this;

        iGameSDK = GameSDKFactory.create();

//        iGameSDK.setGameLanguage(this, SGameLanguage.zh_CH);

        //初始化sdk
        iGameSDK.initSDK(this);

        //在游戏Activity的onCreate生命周期中调用
        iGameSDK.onCreate(this);


        /**
         * 在游戏获得角色信息的时候调用
         * roleId 角色id
         * roleName  角色名
         * rolelevel 角色等级
         * severCode 角色伺服器id
         * serverName 角色伺服器名称
         */
//        iGameSDK.registerRoleInfo(this, "roleid_1", "roleName", "rolelevel", "1000", "serverName");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doLogin();
            }
        });



        findViewById(R.id.currentLoginType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iGameSDK.showCurrentLoginView(activity, new R2LogoutCallback() {
                    @Override
                    public void onSuccess() {
                        ToastUtils.toast(activity,"退出游戏了");

                        //todo 游戏处理退出，然后重新调用登录接口
                        doLoginFromLoginout();
                    }
                });

            }
        });

        findViewById(R.id.bindact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iGameSDK.showBindView(activity);
            }
        });


        findViewById(R.id.unbindact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iGameSDK.showUnBindView(activity, new R2LogoutCallback() {
                    @Override
                    public void onSuccess() {

                        ToastUtils.toast(activity,"退出游戏了");

                        //todo 游戏处理退出，然后重新调用登录接口
                        doLoginFromLoginout();

                    }
                });
            }
        });




    }

    private void doLogin() {
        //直接登录isNeedAutoLogin为true, 点击退出登录后再调用此方法回到登录界面isNeedAutoLogin为false
        iGameSDK.showLogin(activity,true, new R2LoginCallback() {
            @Override
            public void onSuccess(ResponseLoginData loginData) {

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

                ToastUtils.toast(activity,"登录成功  r2Uid:" + r2Uid);

            }
        });
    }

    private void doLoginFromLoginout() {
        //直接登录isNeedAutoLogin为true, 点击退出登录后再调用此方法回到登录界面isNeedAutoLogin为false
        iGameSDK.showLogin(activity,false, new R2LoginCallback() {
            @Override
            public void onSuccess(ResponseLoginData loginData) {

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

                ToastUtils.toast(activity,"登录成功  r2Uid:" + r2Uid);

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        PL.i("activity onResume");
        iGameSDK.onResume(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        iGameSDK.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        iGameSDK.onPause(this);
        PL.i("activity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        PL.i("activity onStop");
        iGameSDK.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PL.i("activity onDestroy");
        iGameSDK.onDestroy(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PL.i("activity onRequestPermissionsResult");
        iGameSDK.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        iGameSDK.onWindowFocusChanged(this,hasFocus);
    }
}
