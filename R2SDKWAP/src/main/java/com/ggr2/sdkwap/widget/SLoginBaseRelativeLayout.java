package com.ggr2.sdkwap.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.R2DDialog;
import com.ggr2.sdkwap.utils.Localization;

/**
 * Created by gan on 2017/4/12.
 */

public abstract class SLoginBaseRelativeLayout extends SBaseRelativeLayout {

    Context context;
    Activity activity;
    LayoutInflater inflater;
    ImageView closeImageView;
    R2DDialog r2DDialog;

    public void setR2DDialog(R2DDialog r2DDialog) {
        this.r2DDialog = r2DDialog;
    }

    public SLoginBaseRelativeLayout(Context context) {
        super(context);

        initView(context);
    }

    public SLoginBaseRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    public SLoginBaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SLoginBaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);

    }

    private void initView(Context context) {
        this.context = context;
        this.activity = (Activity) getContext();
        Localization.updateSGameLanguage(getTheContext());

        inflater = LayoutInflater.from(context);

        closeImageView = new ImageView(activity);

        View contentView = createView(this.context, inflater);

        if (contentView != null) {

            int closeeRadius = getCloseRadius();
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,closeeRadius,closeeRadius,0);

//            LayoutParams l = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            addView(contentView, layoutParams);
//            relativeLayout.addView(contentView,webviewLp);
        }


        RelativeLayout.LayoutParams colseLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        colseLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        this.addView(closeImageView,colseLp);

        closeImageView.setBackgroundResource(R.drawable.com_star_close);
        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (r2DDialog != null){
                    r2DDialog.dismiss();
                }
            }
        });


    }


    private int getCloseRadius(){
        return getContext().getResources().getDrawable(R.drawable.com_star_close).getIntrinsicWidth()/2;
    }


    protected Context getTheContext() {
        return getContext();
    }

    protected abstract View createView(Context context, LayoutInflater layoutInflater);

}
