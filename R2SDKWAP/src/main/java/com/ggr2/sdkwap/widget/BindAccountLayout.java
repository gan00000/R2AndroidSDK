package com.ggr2.sdkwap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.core.base.utils.ToastUtils;
import com.ggr2.sdkwap.R;
import com.ggr2.sdkwap.login.LoginType;
import com.ggr2.sdkwap.utils.StarPyUtil;
import com.r2games.sdk.entity.response.ResponseBindThirdPartyUidData;
import com.r2games.sdk.r2api.R2SDKAPI;
import com.r2games.sdk.r2api.callback.R2APICallback;

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

        fbBind = contentView.findViewById(R.id.r2d_fb_bind_btn);
        googleBind = contentView.findViewById(R.id.r2d_google_bind_btn);



        fbBind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!StarPyUtil.isBindGoogle(getTheContext())) {
//                    guestBindFB();
//                }else {
//                    ToastUtils.toast(getTheContext(),R.string.r2d_string_hasbind_other_tips);
//                }

                if (StarPyUtil.isBindGoogle(getTheContext())){
//                  googleBind.setEnabled(false);
//                  googleBind.setText(R.string.r2d_string_hasbind_account);
                    ToastUtils.toast(getTheContext(),R.string.r2d_string_account_already_bind);
                    return;
                }

                guestBindFB();
            }
        });

        googleBind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!StarPyUtil.isBindFB(getTheContext())) {
//                    guestBindGoogle();
//                }else {
//                    ToastUtils.toast(getTheContext(),R.string.r2d_string_hasbind_other_tips);
//                }
                if (StarPyUtil.isBindGoogle(getTheContext())){
//                  fbBind.setEnabled(false);
//                  fbBind.setText(R.string.r2d_string_hasbind_account);
                    ToastUtils.toast(getTheContext(),R.string.r2d_string_account_already_bind);
                    return;
                }
                guestBindGoogle();
            }
        });
        if (StarPyUtil.isBindFB(getTheContext())){
//            fbBind.setEnabled(false);
//            fbBind.setText(R.string.r2d_string_hasbind_account);
        }
        if (StarPyUtil.isBindGoogle(getTheContext())){
//            googleBind.setEnabled(false);
//            googleBind.setText(R.string.r2d_string_hasbind_account);
        }

        return contentView;
    }

    private void guestBindGoogle() {

        R2SDKAPI.getInstance(context).bindGoogleAccount(activity, StarPyUtil.getUid(getTheContext()), new R2APICallback<ResponseBindThirdPartyUidData>() {

            @Override
            public void onCompleted(int code, String msg,
                                    ResponseBindThirdPartyUidData t) {
                if (code == 1009){

                    ToastUtils.toast(getTheContext(),R.string.r2d_string_google_hasbind);

                }else if (R2SDKAPI.RESPONSE_OK == code) {
                    // bind success
                    StarPyUtil.updateSdkLoginDataBindGoogle(getTheContext(),true);
                    StarPyUtil.savePreviousLoginType(getTheContext(), LoginType.R2GameLoginType_GOOGLE);
                    ToastUtils.toast(getTheContext(),R.string.r2d_string_bind_success);
                    r2DDialog.dismiss();
                }else {
                    ToastUtils.toast(getTheContext(),R.string.r2d_string_bind_fail);
                }
            }
        });

    }

    private void guestBindFB() {

        R2SDKAPI.getInstance(context).bindFacebook(activity, StarPyUtil.getUid(getTheContext()), new R2APICallback<ResponseBindThirdPartyUidData>() {

            @Override
            public void onCompleted(int code, String msg,ResponseBindThirdPartyUidData t) {

                if (code == 1009){

                    ToastUtils.toast(getTheContext(),R.string.r2d_string_fb_hasbind);

                }else if (R2SDKAPI.RESPONSE_OK == code) {
                    // bind success
                    StarPyUtil.updateSdkLoginDataBindFB(getTheContext(),true);
                    StarPyUtil.savePreviousLoginType(getTheContext(), LoginType.R2GameLoginType_FB);
                    ToastUtils.toast(getTheContext(),R.string.r2d_string_bind_success);
                    r2DDialog.dismiss();
                }else {
                    ToastUtils.toast(getTheContext(),R.string.r2d_string_bind_fail);
                }
            }
        });


    }


}
