package com.ggr2.sdkwap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.api.StarpyImpl;
import com.ggr2.sdkwap.utils.StarPyUtil;

/**
 * Created by GanYuanrong on 2017/2/6.
 */

public class UnbindAccountLayout extends SLoginBaseRelativeLayout {

    private View contentView;

    private ImageView currentLoginImageView;

    private View logoutView,unbindView;


    public UnbindAccountLayout(Context context) {
        super(context);

    }


    public UnbindAccountLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnbindAccountLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createView(Context context, LayoutInflater layoutInflater) {
        return onCreateView(layoutInflater);
    }

    public View onCreateView(LayoutInflater inflater) {

        contentView = inflater.inflate(R.layout.r2d_layout_unbind, null);

        currentLoginImageView =  contentView.findViewById(R.id.r2d_current_unbind_logo);
        logoutView = contentView.findViewById(R.id.r2d_current_unbind_logout);
        unbindView = contentView.findViewById(R.id.r2d_current_unbind_unbind_btn);


        if (StarPyUtil.isFBLogin(getContext())){
            currentLoginImageView.setImageResource(R.drawable.r2d_fb_current);
        }else if (StarPyUtil.isGoogleLogin(getContext())){
            currentLoginImageView.setImageResource(R.drawable.r2d_google_current);
        }

        logoutView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                StarpyImpl.getInstance().logout(activity);
                r2DDialog.dismiss();
            }
        });
        unbindView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                FBGGLogoutTipsLayout fbggLogoutTipsLayout = new FBGGLogoutTipsLayout(activity);
                fbggLogoutTipsLayout.setR2DDialog(r2DDialog);
                r2DDialog.setContentView(fbggLogoutTipsLayout);

            }
        });


        return contentView;
    }



}
