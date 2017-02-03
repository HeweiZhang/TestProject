package com.sikeda.wxlogin.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.sikeda.wxlogin.AppConfig;
import com.sikeda.wxlogin.AppContext;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;


/**
 * Created by david on 2016/8/28.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppContext.getInstance().api.handleIntent(getIntent(), this);//wechat login
    }



    /**
     * 微信登录回调
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e("info", "baseResp.getType" + baseResp.getType());
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK://同意
                if (baseResp.getType() == 1) {//微信登录
                    if (((SendAuth.Resp) baseResp).state.equals(AppConfig.WX_STATE_RANDOM)) {//校验state
                        String code = ((SendAuth.Resp) baseResp).code;//使用单独微信登录的jar包时这里是：token
                        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                        Log.e("info", "baseResp.code:" + code);
                        AppConfig.WX_CODE = code;
                    }
                } else if (baseResp.getType() == 2) {//微信、好友圈分享

                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                Toast.makeText(this, "用户拒绝授权", Toast.LENGTH_SHORT).show();
                if (baseResp.getType() == 1) {//微信登录
                    AppConfig.WX_CODE = "error";
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                if (baseResp.getType() == 1) {//微信登录
                    AppConfig.WX_CODE = "error";
                }
                Toast.makeText(this, "取消操作", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        finish();
    }



}
