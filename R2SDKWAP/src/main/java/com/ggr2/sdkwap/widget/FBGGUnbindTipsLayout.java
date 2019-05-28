package com.ggr2.sdkwap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.core.base.utils.ToastUtils;
import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.login.LoginType;
import com.ggr2.sdkwap.utils.StarPyUtil;
import com.r2games.sdk.R2SDK;
import com.r2games.sdk.callbacks.R2Callback;
import com.r2games.sdk.entity.R2Error;
import com.r2games.sdk.entity.response.ResponseUnbindThirdPartyUidData;

/**
 * Created by GanYuanrong on 2017/2/6.
 */

public class FBGGUnbindTipsLayout extends SLoginBaseRelativeLayout {

    private View contentView;

    private View logoutOkView;

    private View logoutCancelView;


    public FBGGUnbindTipsLayout(Context context) {
        super(context);

    }


    public FBGGUnbindTipsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FBGGUnbindTipsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createView(Context context, LayoutInflater layoutInflater) {
        return onCreateView(layoutInflater);
    }

    public View onCreateView(LayoutInflater inflater) {

        contentView = inflater.inflate(R.layout.r2d_layout_fbgg_unbind_tips, null);

        logoutOkView =  contentView.findViewById(R.id.guest_tips_ok);
        logoutCancelView = contentView.findViewById(R.id.guest_tips_cancel);


        logoutCancelView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                UnbindAccountLayout unbindAccountLayout = new UnbindAccountLayout(activity);
                unbindAccountLayout.setR2DDialog(r2DDialog);
                r2DDialog.setContentView(unbindAccountLayout);


            }
        });

        logoutOkView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //解绑
                unbind();
            }
        });

        return contentView;
    }

    private void unbind() {

        if (LoginType.R2GameLoginType_GUEST.equals(StarPyUtil.getPreviousLoginType(getTheContext()))){
            return;
        }

        R2SDK.getInstance(context).unbindThridPartyUid(StarPyUtil.getUid(getTheContext()), StarPyUtil.getPreviousLoginType(getTheContext()),
                new R2Callback<ResponseUnbindThirdPartyUidData>() {

                    @Override
                    public void onSuccess(ResponseUnbindThirdPartyUidData result) {
                        //unbind success
                        ToastUtils.toast(getTheContext(),R.string.r2d_string_unbind_success);
                        r2DDialog.dismiss();
                    }

                    @Override
                    public void onError(R2Error error) {
                        //unbind error
                        ToastUtils.toast(getTheContext(),R.string.r2d_string_unbind_fail);
                    }

                    @Override
                    public void onCancel() {
                        //unbind cancel
                        ToastUtils.toast(getTheContext(),R.string.r2d_string_unbind_cancel);
                    }
                });

    }


}
