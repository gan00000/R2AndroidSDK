package com.ggr2.sdkwap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.api.GameSDKImpl;

/**
 * Created by GanYuanrong on 2017/2/6.
 */

public class GuestLogoutTipsLayout extends SLoginBaseRelativeLayout {

    private View contentView;

    private View logoutOkView;

    private View logoutCancelView;


    public GuestLogoutTipsLayout(Context context) {
        super(context);

    }


    public GuestLogoutTipsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuestLogoutTipsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createView(Context context, LayoutInflater layoutInflater) {
        return onCreateView(layoutInflater);
    }

    public View onCreateView(LayoutInflater inflater) {

        contentView = inflater.inflate(R.layout.r2d_layout_guest_logout_tips, null);

        logoutOkView =  contentView.findViewById(R.id.guest_tips_ok);
        logoutCancelView = contentView.findViewById(R.id.guest_tips_cancel);


        logoutCancelView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                CurrentGuestLoginLayout guestLoginLayout = new CurrentGuestLoginLayout(activity);
                guestLoginLayout.setR2DDialog(r2DDialog);
                r2DDialog.setContentView(guestLoginLayout);


            }
        });

        logoutOkView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                GameSDKImpl.getInstance().logout(activity);
                r2DDialog.dismiss();
            }
        });

        return contentView;
    }




}
