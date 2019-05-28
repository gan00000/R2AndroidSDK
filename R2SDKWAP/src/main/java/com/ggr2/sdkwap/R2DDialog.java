package com.ggr2.sdkwap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ggr2.sdkwap.base.SBaseDialogWrapContent;

public class R2DDialog extends SBaseDialogWrapContent {

    public R2DDialog(@NonNull Context context) {
        super(context);
        initR2D();
    }

    public R2DDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initR2D();
    }

    protected R2DDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initR2D();
    }


    private void initR2D(){
        this.setCanceledOnTouchOutside(false);

    }

}
