package com.ggr2.sdkwap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ggr2.sdkwap.R;

/**
 * Created by GanYuanrong on 2017/2/6.
 */

public class CurrentGuestLoginLayout extends SLoginBaseRelativeLayout {

    private View contentView;

    private ImageView currentLoginImageView;

    private View logoutView;
    private View guestBindView;


    public CurrentGuestLoginLayout(Context context) {
        super(context);

    }


    public CurrentGuestLoginLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrentGuestLoginLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createView(Context context, LayoutInflater layoutInflater) {
        return onCreateView(layoutInflater);
    }

    public View onCreateView(LayoutInflater inflater) {

        contentView = inflater.inflate(R.layout.r2d_layout_current_login_guest, null);

        currentLoginImageView =  contentView.findViewById(R.id.r2d_current_login_logo_guest);
        logoutView = contentView.findViewById(R.id.r2d_current_guest_logout);
        guestBindView = contentView.findViewById(R.id.r2d_current_guest_bind);


        logoutView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                

            }
        });
        guestBindView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return contentView;
    }




}
