package com.ggr2.sdkwap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ggr2.sdkwap.R;

/**
 * Created by GanYuanrong on 2017/2/6.
 */

public class BindAccountLayout extends SLoginBaseRelativeLayout {

    private View contentView;

    private View fbBind,googleBind;


    public BindAccountLayout(Context context) {
        super(context);

    }


    public BindAccountLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BindAccountLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createView(Context context, LayoutInflater layoutInflater) {
        return onCreateView(layoutInflater);
    }

    public View onCreateView(LayoutInflater inflater) {

        contentView = inflater.inflate(R.layout.r2d_layout_bind_account, null);

        fbBind = contentView.findViewById(R.id.r2d_fb_bind);
        googleBind = contentView.findViewById(R.id.r2d_google_bind);



        fbBind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        googleBind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return contentView;
    }




}
