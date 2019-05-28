package com.ggr2.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.core.base.utils.PL;
import com.ggr2.sdkwap.api.IStarpy;
import com.ggr2.sdkwap.api.R2Callback;
import com.ggr2.sdkwap.api.StarpyFactory;
import com.r2games.sdk.entity.response.ResponseLoginData;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;


    private IStarpy iStarpy;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.demo_login);

        this.activity = this;

        iStarpy = StarpyFactory.create();

//        iStarpy.setGameLanguage(this, SGameLanguage.zh_CH);

        //初始化sdk
        iStarpy.initSDK(this);

        //在游戏Activity的onCreate生命周期中调用
        iStarpy.onCreate(this);


        /**
         * 在游戏获得角色信息的时候调用
         * roleId 角色id
         * roleName  角色名
         * rolelevel 角色等级
         * severCode 角色伺服器id
         * serverName 角色伺服器名称
         */
//        iStarpy.registerRoleInfo(this, "roleid_1", "roleName", "rolelevel", "1000", "serverName");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iStarpy.showLogin(activity, new R2Callback() {
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

                    }
                });
            }
        });



        findViewById(R.id.currentLoginType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iStarpy.showCurrentLoginView(activity);

            }
        });

        findViewById(R.id.bindact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStarpy.showBindView(activity);
            }
        });


        findViewById(R.id.unbindact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iStarpy.showUnBindView(activity);
            }
        });




    }



    @Override
    protected void onResume() {
        super.onResume();
        PL.i("activity onResume");
        iStarpy.onResume(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        iStarpy.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        iStarpy.onPause(this);
        PL.i("activity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        PL.i("activity onStop");
        iStarpy.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PL.i("activity onDestroy");
        iStarpy.onDestroy(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PL.i("activity onRequestPermissionsResult");
        iStarpy.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        iStarpy.onWindowFocusChanged(this,hasFocus);
    }
}
