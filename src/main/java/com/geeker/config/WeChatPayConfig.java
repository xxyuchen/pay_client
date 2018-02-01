package com.geeker.config;

/**
 * Created by Administrator on 2017/10/31 0031.
 */
public class WeChatPayConfig {
    /**
     * 微信服务号相关信息
     */
    public final static String APPID = "wxf9d0b3c29e8f0a14";//服务号的应用号
    public final static String MCH_ID = "1491158572";//商户号
    public final static String API_KEY = "zhejianghuakundaoweishujuketiwen";//API密钥

    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //测试
    public final static String NOTIFY_URL="http://geeker.worken.cn/thirdPay/pay/weChatNotify";
    //本地
    // public final static String NOTIFY_URL="http://affbfc7f.ngrok.io/payBack/weChatNotify.action";
}
