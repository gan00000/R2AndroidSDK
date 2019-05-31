package com.ggr2.sdkwap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.api.GameSDKImpl;
import com.ggr2.sdkwap.utils.StarPyUtil;

/**
 * Created by GanYuanrong on 2017/2/6.
 */

public class CurrentFBGGLoginLayout extends SLoginBaseRelativeLayout {

    private View contentView;

    private ImageView currentLoginImageView;

    private View logoutView;


    public CurrentFBGGLoginLayout(Context context) {
        super(context);

    }


    public CurrentFBGGLoginLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrentFBGGLoginLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createView(Context context, LayoutInflater layoutInflater) {
        return onCreateView(layoutInflater);
    }

    public View onCreateView(LayoutInflater inflater) {

        contentView = inflater.inflate(R.layout.r2d_layout_current_login_fb_google, null);

        currentLoginImageView =  contentView.findViewById(R.id.r2d_current_login_logo);
        logoutView = contentView.findViewById(R.id.r2d_current_fb_google_logout);


        if (StarPyUtil.isFBLogin(getContext())){
            currentLoginImageView.setImageResource(R.drawable.r2d_fb_current);
        }else if (StarPyUtil.isGoogleLogin(getContext())){
            currentLoginImageView.setImageResource(R.drawable.r2d_google_current);
        }

        logoutView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSDKImpl.getInstance().logout(activity);
                r2DDialog.dismiss();

            }
        });


        return contentView;
    }




}
