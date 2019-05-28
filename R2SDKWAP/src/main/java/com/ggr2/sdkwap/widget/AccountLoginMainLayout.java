package com.ggr2.sdkwap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.login.LoginAPI;

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

                LoginAPI.fbLogin(activity,r2DDialog);


            }
        });
        googleLoginView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginAPI.googleLogin(activity,r2DDialog);



            }
        });
        guestLoginView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginAPI.guestLogin(activity,r2DDialog);

            }
        });


        return contentView;
    }





}
