package com.ggr2.sdkwap.utils;

import android.content.Context;
import android.text.TextUtils;

import com.core.base.cipher.DESCipher;
import com.core.base.utils.JsonUtil;
import com.core.base.utils.SPUtil;
import com.core.base.utils.SStringUtil;
import com.ggr2.sdkwap.login.LoginType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gan on 2017/2/7.
 */

public class StarPyUtil {

    public static final String STAR_PY_SP_FILE = "star_py_sp_file.xml";
    public static final String STARPY_SDK_LOGIN_TERMS_FILE = "starpy_sdk_login_terms_file.xml";

    public static final String ADS_ADVERTISERS_NAME = "ADS_ADVERTISERS_NAME";
    public static final String STARPY_GAME_LANGUAGE = "STARPY_GAME_LANGUAGE";

    public static final String STARPY_LOGIN_USERNAME = "STARPY_LOGIN_USERNAME";//保存用户的账号
    public static final String STARPY_LOGIN_PASSWORD = "STARPY_LOGIN_PASSWORD";//保存用户的密码

    public static final String STARPY_LOGIN_SERVER_RETURN_DATA = "STARPY_LOGIN_SERVER_RETURN_DATA";//保存服务端返回的数据

    public static final String STARPY_SDK_LOGIN_TERMS = "STARPY_SDK_LOGIN_TERMS";
    public static final String STARPY_MAC_LOGIN_USERNAME = "STARPY_MAC_LOGIN_USERNAME";
    public static final String STARPY_MAC_LOGIN_PASSWORD = "STARPY_MAC_LOGIN_PASSWORD";
    public static final String STARPY_PREVIOUS_LOGIN_TYPE = "STARPY_PREVIOUS_LOGIN_TYPE";



    public static void saveAccount(Context context,String account){
        SPUtil.saveSimpleInfo(context,STAR_PY_SP_FILE, STARPY_LOGIN_USERNAME, account);
    }

    public static String getAccount(Context context){
        return SPUtil.getSimpleString(context,STAR_PY_SP_FILE, STARPY_LOGIN_USERNAME);
    }
    public static void saveMacAccount(Context context,String account){
        SPUtil.saveSimpleInfo(context,STAR_PY_SP_FILE, STARPY_MAC_LOGIN_USERNAME, account);
    }

    public static String getMacAccount(Context context){
        return SPUtil.getSimpleString(context,STAR_PY_SP_FILE, STARPY_MAC_LOGIN_USERNAME);
    }

    public static void savePassword(Context context,String password){
        SPUtil.saveSimpleInfo(context,STAR_PY_SP_FILE, STARPY_LOGIN_PASSWORD, encryptPassword(password));
    }

    public static String getPassword(Context context){
        return decryptPassword(SPUtil.getSimpleString(context,STAR_PY_SP_FILE, STARPY_LOGIN_PASSWORD));
    }
    public static void saveMacPassword(Context context,String password){
        SPUtil.saveSimpleInfo(context,STAR_PY_SP_FILE, STARPY_MAC_LOGIN_PASSWORD, encryptPassword(password));
    }

    public static String getMacPassword(Context context){
        return decryptPassword(SPUtil.getSimpleString(context,STAR_PY_SP_FILE, STARPY_MAC_LOGIN_PASSWORD));
    }

    public static void savePreviousLoginType(Context context,String loginType){
        SPUtil.saveSimpleInfo(context,STAR_PY_SP_FILE, STARPY_PREVIOUS_LOGIN_TYPE, loginType);
    }

    public static String getPreviousLoginType(Context context){
        return SPUtil.getSimpleString(context,STAR_PY_SP_FILE, STARPY_PREVIOUS_LOGIN_TYPE);
    }

    public static boolean isFBLogin(Context context){
        return LoginType.R2GameLoginType_FB.equals(getPreviousLoginType(context));
    }

    public static boolean isGoogleLogin(Context context){
        return LoginType.R2GameLoginType_GOOGLE.equals(getPreviousLoginType(context));
    }

    public static boolean isGuestLogin(Context context){
        return LoginType.R2GameLoginType_GUEST.equals(getPreviousLoginType(context));
    }

//    private final static String cipherKey = "20170314starpypassword";
    private final static String cipherPasswordFlag = "888*****888";
    private static String encryptPassword(String password){
        try {
            if (SStringUtil.isNotEmpty(password)){
                return cipherPasswordFlag + DESCipher.encrypt3DES(password,PY_DY_ENCRYPT_SECRETKEY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }
    private static String decryptPassword(String encryptText){
        try {
            if (SStringUtil.isNotEmpty(encryptText) && encryptText.startsWith(cipherPasswordFlag)){
                encryptText = encryptText.replace(cipherPasswordFlag,"");
                return DESCipher.decrypt3DES(encryptText,PY_DY_ENCRYPT_SECRETKEY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptText;
    }

  /*  public static void saveUid(Context context,String uid){
        SPUtil.saveSimpleInfo(context,STAR_PY_SP_FILE, STARPY_LOGIN_USER_ID, uid);
    }*/

    public static boolean isBindFB(Context context){
        return JsonUtil.getBooleanValueByKey(context,getSdkLoginData(context), "linked_fb",false);
    }

    public static boolean isBindGoogle(Context context){
        return JsonUtil.getBooleanValueByKey(context,getSdkLoginData(context), "linked_google_act",false);
    }

    public static void saveLoginData(Context context, String loginType, String userId, String timestamp, String accessToken,boolean linked_fb,boolean linked_google_act,boolean linked_google_games ) {

        JSONObject loginDataJsonObject = new JSONObject();
        try {
            loginDataJsonObject.put("userId",userId);
            loginDataJsonObject.put("timestamp",timestamp);
            loginDataJsonObject.put("accessToken",accessToken);
            loginDataJsonObject.put("linked_fb",linked_fb);
            loginDataJsonObject.put("linked_google_act",linked_google_act);
            loginDataJsonObject.put("linked_google_games",linked_google_games);

            StarPyUtil.saveSdkLoginData(context,loginDataJsonObject.toString());

            StarPyUtil.savePreviousLoginType(context,loginType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void updateSdkLoginDataBindFB(Context context,boolean linked_fb){
       String loginData = getSdkLoginData(context);
        try {
            JSONObject loginDataJsonObject = new JSONObject(loginData);
            loginDataJsonObject.put("linked_fb",linked_fb);
            StarPyUtil.saveSdkLoginData(context,loginDataJsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void updateSdkLoginDataBindGoogle(Context context,boolean linked_google_act){
        String loginData = getSdkLoginData(context);
        try {
            JSONObject loginDataJsonObject = new JSONObject(loginData);
            loginDataJsonObject.put("linked_google_act",linked_google_act);
            StarPyUtil.saveSdkLoginData(context,loginDataJsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void saveSdkLoginData(Context context,String data){
        SPUtil.saveSimpleInfo(context,STAR_PY_SP_FILE,STARPY_LOGIN_SERVER_RETURN_DATA,data);
    }

    public static String getSdkLoginData(Context context){
        return SPUtil.getSimpleString(context,STAR_PY_SP_FILE,STARPY_LOGIN_SERVER_RETURN_DATA);
    }

    public static boolean isLogin(Context context){
        return SStringUtil.isNotEmpty(getSdkLoginData(context));
    }

    public static void saveSdkLoginTerms(Context context,String terms){
        SPUtil.saveSimpleInfo(context,STARPY_SDK_LOGIN_TERMS_FILE,STARPY_SDK_LOGIN_TERMS,terms);
    }


    public static String getUid(Context context){
        return JsonUtil.getValueByKey(context,getSdkLoginData(context), "userId", "");
    }

    public static String getSdkTimestamp(Context context){
        return JsonUtil.getValueByKey(context,getSdkLoginData(context), "timestamp", "");
    }

    public static String getSdkAccessToken(Context context){
        return JsonUtil.getValueByKey(context,getSdkLoginData(context), "accessToken", "");
    }

    private static final String STARPY_LOGIN_ROLE_ID = "STARPY_LOGIN_ROLE_ID";
    private static final String STARPY_LOGIN_ROLE_NAME = "STARPY_LOGIN_ROLE_NAME";
    private static final String STARPY_LOGIN_ROLE_SERVER_CODE = "STARPY_LOGIN_ROLE_SERVER_CODE";
    private static final String STARPY_LOGIN_ROLE_SERVER_NAME = "STARPY_LOGIN_ROLE_SERVER_NAME";
    private static final String STARPY_LOGIN_ROLE_INFO = "STARPY_LOGIN_ROLE_INFO";
    private static final String STARPY_LOGIN_ROLE_LEVEL = "STARPY_LOGIN_ROLE_LEVEL";
    private static final String STARPY_LOGIN_ROLE_VIP = "STARPY_LOGIN_ROLE_VIP";


    public static String getRoleInfo(Context context){
        return SPUtil.getSimpleString(context,STAR_PY_SP_FILE,STARPY_LOGIN_ROLE_INFO);
    }

    private static void saveRoleInfoJson(Context context,String roleInfo){
        SPUtil.saveSimpleInfo(context,STAR_PY_SP_FILE,STARPY_LOGIN_ROLE_INFO,roleInfo);
    }

    public static String getRoleId(Context context){
        return JsonUtil.getValueByKey(context,getRoleInfo(context), STARPY_LOGIN_ROLE_ID, "");
    }

    public static String getRoleName(Context context){
        return JsonUtil.getValueByKey(context,getRoleInfo(context), STARPY_LOGIN_ROLE_NAME, "");
    }

    public static String getServerCode(Context context) {
        return JsonUtil.getValueByKey(context,getRoleInfo(context), STARPY_LOGIN_ROLE_SERVER_CODE, "");
    }

    public static String getServerName(Context context){
        return JsonUtil.getValueByKey(context,getRoleInfo(context), STARPY_LOGIN_ROLE_SERVER_NAME, "");
    }

    public static void saveRoleInfo(Context context, String roleId, String roleName, String severCode, String serverName) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(STARPY_LOGIN_ROLE_ID,roleId);
            jsonObject.put(STARPY_LOGIN_ROLE_NAME,roleName);
            jsonObject.put(STARPY_LOGIN_ROLE_SERVER_CODE,severCode);
            jsonObject.put(STARPY_LOGIN_ROLE_SERVER_NAME,serverName);
            saveRoleInfoJson(context, jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveRoleLevelVip(Context context,String roleLevel,String roleVip){
        if (SStringUtil.isNotEmpty(roleLevel)) {
            SPUtil.saveSimpleInfo(context,STAR_PY_SP_FILE,STARPY_LOGIN_ROLE_LEVEL,roleLevel);
        }
        if (SStringUtil.isNotEmpty(roleVip)) {
            SPUtil.saveSimpleInfo(context,STAR_PY_SP_FILE,STARPY_LOGIN_ROLE_VIP,roleVip);
        }
    }

    public static String getRoleLevel(Context context){
        return SPUtil.getSimpleString(context,STAR_PY_SP_FILE,STARPY_LOGIN_ROLE_LEVEL);
    }
    public static String getRoleVip(Context context){
        return SPUtil.getSimpleString(context,STAR_PY_SP_FILE,STARPY_LOGIN_ROLE_VIP);
    }

    public static boolean checkAccount(String account){
        if (TextUtils.isEmpty(account)){
            return false;
        }
        if (account.matches("^[A-Za-z0-9]{6,18}$")){
            return true;
        }
        return false;
    }

    public static boolean checkPassword(String password){
        if (TextUtils.isEmpty(password)){
            return false;
        }
        if (password.matches("^[A-Za-z0-9]{6,18}$")){
            return true;
        }
        return false;
    }

    private static final String PY_DY_ENCRYPT_SECRETKEY = "(starpy99988820170227dyrl)";
    public static String encryptDyUrl(Context context,String data){
        try {
            return DESCipher.encrypt3DES(data,PY_DY_ENCRYPT_SECRETKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decryptDyUrl(Context context,String data){
        try {
            return DESCipher.decrypt3DES(data,PY_DY_ENCRYPT_SECRETKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static final String STARPY_LOGIN_GOOGLE_ID = "STARPY_LOGIN_GOOGLE_ID";
    public static void saveGoogleId(Context context, String googleId){
        SPUtil.saveSimpleInfo(context,StarPyUtil.STAR_PY_SP_FILE,STARPY_LOGIN_GOOGLE_ID,googleId);
    }
    public static String getGoogleId(Context context){
       return SPUtil.getSimpleString(context,StarPyUtil.STAR_PY_SP_FILE,STARPY_LOGIN_GOOGLE_ID);
    }


    private static final String STARPY_GOOGLE_ADVERTISING_ID = "STARPY_GOOGLE_ADVERTISING_ID";
    public static void saveGoogleAdId(Context context, String googleAdId){
        SPUtil.saveSimpleInfo(context,StarPyUtil.STAR_PY_SP_FILE,STARPY_GOOGLE_ADVERTISING_ID,googleAdId);
    }
    public static String getGoogleAdId(Context context){
        return SPUtil.getSimpleString(context,StarPyUtil.STAR_PY_SP_FILE,STARPY_GOOGLE_ADVERTISING_ID);
    }

    private static final String STARPY_GOOGLE_INSTALL_REFERRER = "STARPY_GOOGLE_INSTALL_REFERRER";
    public static void saveReferrer(Context context, String referrer){
        SPUtil.saveSimpleInfo(context,StarPyUtil.STAR_PY_SP_FILE,STARPY_GOOGLE_INSTALL_REFERRER,referrer);
    }
    public static String getReferrer(Context context){
        return SPUtil.getSimpleString(context,StarPyUtil.STAR_PY_SP_FILE,STARPY_GOOGLE_INSTALL_REFERRER);
    }
    private static final String STARPY_GOOGLE_TOKEN_ID_STRING = "STARPY_GOOGLE_TOKEN_ID_STRING";
    public static void saveGoogleIdToken(Context context, String idTokenString){
        SPUtil.saveSimpleInfo(context,StarPyUtil.STAR_PY_SP_FILE,STARPY_GOOGLE_TOKEN_ID_STRING,idTokenString);
    }
    public static String getGoogleIdToken(Context context){
        return SPUtil.getSimpleString(context,StarPyUtil.STAR_PY_SP_FILE,STARPY_GOOGLE_TOKEN_ID_STRING);
    }

    private static final String STARPY_ONLY_THIRD_PAY = "STARPY_ONLY_THIRD_PAY";
    public static void setOnlyThirdPay(Context context, String data){
        SPUtil.saveSimpleInfo(context,StarPyUtil.STAR_PY_SP_FILE,STARPY_ONLY_THIRD_PAY,data);
    }
    public static String getOnlyThirdPay(Context context){
        return SPUtil.getSimpleString(context,StarPyUtil.STAR_PY_SP_FILE, STARPY_ONLY_THIRD_PAY);
    }


    public static String getGameLanguage(Context context) {
        return SPUtil.getSimpleString(context, StarPyUtil.STAR_PY_SP_FILE, StarPyUtil.STARPY_GAME_LANGUAGE);
    }

    public static void saveGameLanguage(Context context,String language) {
        if (TextUtils.isEmpty(language)) {
            return;
        }
        SPUtil.saveSimpleInfo(context, StarPyUtil.STAR_PY_SP_FILE, StarPyUtil.STARPY_GAME_LANGUAGE,language);
    }


}
