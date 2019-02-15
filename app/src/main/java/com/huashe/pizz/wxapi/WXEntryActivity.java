package com.huashe.pizz.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.huashe.pizz.MyApplication;
import com.huashe.pizz.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";

    private static final String OPEN_WECHAT_ACCESS_TOKEN = "OPEN_WECHAT_ACCESS_TOKEN";
    private static final String OPEN_WECHAT_REFRESH_TOKEN = "OPEN_WECHAT_REFRESH_TOKEN";

    private Gson mGson;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIntent = getIntent();

        /**
         * 注意：第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，
         * 则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
         */
        try {
            if (!MyApplication.api.handleIntent(getIntent(), this)) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mGson = new Gson();

    }

    /**
     * 初始化微信开放平台
     *
     * @param context
     * @param appId
     * @return
     */
    public static IWXAPI initOpenWeChat(Context context, String appId) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        IWXAPI api = WXAPIFactory.createWXAPI(context, appId, true);
        // 将该app注册到微信
        api.registerApp(appId);
        return api;
    }

    /**
     * 微信登录
     *
     * @param context
     * @param iwxapi
     */
    public static void loginViaOpenWeChat(Context context, IWXAPI iwxapi) {
        // 判断微信客户端是否已安装
        if (!iwxapi.isWXAppInstalled()) {
            ToastUtil.showToast("未安装微信客户端！");
            return;
        }
        //
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_req_state";
        System.out.println(req.openId);
        iwxapi.sendReq(req);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        MyApplication.api.handleIntent(intent, this);
    }

    /**
     * 微信发送请求到第三方应用时，会回调到该方法
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                System.out.println("COMMAND_GETMESSAGE_FROM_WX");
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                System.out.println("COMMAND_SHOWMESSAGE_FROM_WX");
                break;
            default:
                break;
        }
    }

    /**
     * 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
     *
     * @param baseResp
     */
    @Override
    public void onResp(BaseResp baseResp) {
        Log.i(TAG, "baseresp.getType = " + baseResp.getType());

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.i(TAG, "发送成功");
                // 请求CODE
                String code = ((SendAuth.Resp) baseResp).code;
                // 通过code获取access_token
//                getAccessToken(code);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.i(TAG, "发送取消");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.i(TAG, "发送被拒绝");
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                Log.i(TAG, "不支持错误");
                break;
            default:
                Log.i(TAG, "发送返回");
                break;
        }
    }


    /**
     * 文字类型分享
     *
     * @param api        IWXAPI
     * @param text       文字
     * @param isTimeline 分享至朋友圈或聊天
     */
    public static void textSharing(IWXAPI api, String text, boolean isTimeline) {

        // 初始化一个WXTextObject对象，填写分享的文本内容
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = text;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // transaction字段用于唯一标识一个请求
        //req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        // 调用api接口发送数据到微信
        api.sendReq(req);

    }

    /**
     * 图片类型分享
     *
     * @param api        IWXAPI
     * @param bmp        图片的Bitmap
     * @param isTimeline 分享至朋友圈或聊天
     */
    public static void imageSharing(IWXAPI api, Bitmap bmp, boolean isTimeline) {

        // 初始化WXImageObject和WXMediaMessage对象
        WXImageObject imgObject = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObject;
        // 设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 0, 0, true);
        bmp.recycle();
        // BitmapToByteArray
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumbBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 缩略图
        msg.thumbData = baos.toByteArray();

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // transaction字段用于唯一标识一个请求
        //req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        // 调用api接口发送数据到微信
        api.sendReq(req);

    }


    /**
     * 网页类型分享
     *  @param api         IWXAPI
     * @param url         URL
     * @param title       标题
     * @param description 描述
     * @param thumb       缩略图
     */
    public static void webpagSharing(IWXAPI api, String url, String title, String description, Bitmap thumb) {

        // 初始化一个 对象，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

        // 用 对象初始化一个 对象，填写标题、描述
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = webpage;
        msg.title = title;
        msg.description = description;
        // BitmapToByteArray
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumb.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 缩略图
        msg.thumbData = baos.toByteArray();

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // transaction字段用于唯一标识一个请求
        //req.transaction = buildTransaction("webpage");
        req.message = msg;
//        req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        // 调用api接口发送数据到微信
        api.sendReq(req);

    }


}
