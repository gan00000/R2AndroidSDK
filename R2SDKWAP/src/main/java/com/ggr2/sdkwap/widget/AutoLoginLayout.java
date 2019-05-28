package com.ggr2.sdkwap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.login.LoginAPI;
import com.ggr2.sdkwap.login.LoginType;
import com.ggr2.sdkwap.utils.StarPyUtil;

/**
 * Created by GanYuanrong on 2017/2/6.
 */

public class AutoLoginLayout extends SLoginBaseRelativeLayout {

    private View contentView;


    public AutoLoginLayout(Context context) {
        super(context);

    }


    public AutoLoginLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoLoginLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createView(Context context, LayoutInflater layoutInflater) {
        return onCreateView(layoutInflater);
    }

    public View onCreateView(LayoutInflater inflater) {

        contentView = inflater.inflate(R.layout.r2d_layout_login_auto, null);


        if (closeImageView != null){
            closeImageView.setVisibility(GONE);
        }


        return contentView;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();


        String loginType = StarPyUtil.getPreviousLoginType(getTheContext());
        if (LoginType.R2GameLoginType_GUEST.equals(loginType)){

            LoginAPI.guestLogin(activity,r2DDialog);

        }else if (LoginType.R2GameLoginType_FB.equals(loginType)){

            LoginAPI.fbLogin(activity,r2DDialog);

        }else if (LoginType.R2GameLoginType_GOOGLE.equals(loginType)){
            LoginAPI.googleLogin(activity,r2DDialog);
        }
    }
}
