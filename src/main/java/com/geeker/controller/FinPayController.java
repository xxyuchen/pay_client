package com.geeker.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.geeker.config.AlipayConfig;
import com.geeker.config.WeChatPayConfig;
import com.geeker.utils.PayCommonUtil;
import com.geeker.utils.XMLUtil;
import org.jdom.JDOMException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
* @Author TangZhen
* @Date 2017/11/6 0006 12:21
* @Description  充值
*/
@RestController
public class FinPayController {
    /**
     * 充值
     * @param sn
     * @param payChannel
     * @param requstType
     * @param rechargeMoney
     * @return
     */
    @RequestMapping(value = "/rechargeAccount")
    @ResponseBody
    public Map<String,Object> rechargeAccount(String sn, Integer payChannel, String requstType, String rechargeMoney) {
        Map<String,Object> map = new HashMap<>();
        //map =finPayService.rechargeAccount(sn,payChannel,requstType,rechargeMoney);
        //支付渠道--支付宝
        if(payChannel == 2){
            //启动支付服务
            AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                    AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
            String payStr = "";
            try {
                AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
                model.setOutTradeNo(sn);
                model.setSubject("数聚客财务中心-支付宝充值");
                model.setTimeoutExpress("30m");
                model.setProductCode("QUICK_MSECURITY_PAY");
                model.setTotalAmount(rechargeMoney);
                if(requstType.equals("pc")){
                    AlipayTradePagePayRequest alipayPageRequest = new AlipayTradePagePayRequest();
                    alipayPageRequest.setReturnUrl(AlipayConfig.return_url);
                    alipayPageRequest.setNotifyUrl(AlipayConfig.notify_url);
                    //商户订单号，商户网站订单系统中唯一订单号，必填
                    alipayPageRequest.setBizModel(model);
                    payStr = client.pageExecute(alipayPageRequest).getBody(); //调用SDK生成表单
                }else if(requstType.equals("app")){
                    AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
                    // 封装请求支付信息
                    alipayRequest.setBizModel(model);
                    // 设置异步通知地址（回调地址）
                    alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
                    payStr = client.sdkExecute(alipayRequest).getBody(); //调用SDK生成表单
                }
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
            map.put("payInfo",payStr);
            map.put("sn",sn);
        }
        //支付渠道--微信
        if(payChannel == 1) {
            //设置回调地址-获取当前的地址拼接回调地址
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
            parameters.put("appid", WeChatPayConfig.APPID);
            parameters.put("mch_id", WeChatPayConfig.MCH_ID);
            parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());//随机数
            parameters.put("body", "数聚客财务中心-微信充值");
            parameters.put("out_trade_no", sn); //订单id
            parameters.put("fee_type", "CNY");
            parameters.put("total_fee",String.valueOf((int)(Double.valueOf(rechargeMoney)*100)));//充值金额(入参单位为分)
            parameters.put("spbill_create_ip", "127.0.0.1");
            parameters.put("notify_url", WeChatPayConfig.NOTIFY_URL);//通知地址
            if(requstType.equals("pc")){
                parameters.put("trade_type", "NATIVE");//交易类型-NATIVE-原生扫码支付
            }else if(requstType.equals("app")){
                parameters.put("trade_type", "APP");//交易类型-APP-app支付
            }
            //设置签名
            String sign = PayCommonUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            //封装请求参数结束
            String requestXML = PayCommonUtil.getRequestXml(parameters);
            //调用统一下单接口
            String result = PayCommonUtil.httpsRequest(WeChatPayConfig.UNIFIED_ORDER_URL, "POST", requestXML);
            SortedMap<Object, Object> backMap = new TreeMap<>();
            try {
                Map<String, String> resultMap = XMLUtil.doXMLParse(result);
                backMap.put("appid", WeChatPayConfig.APPID);
                backMap.put("partnerid", WeChatPayConfig.MCH_ID);
                backMap.put("prepayid", resultMap.get("prepay_id"));
                backMap.put("package", "Sign=WXPay");
                backMap.put("noncestr", PayCommonUtil.CreateNoncestr());
                //本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
                backMap.put("timestamp", Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10)));
                backMap.put("sign", PayCommonUtil.createSign("UTF-8", backMap));
                if(requstType.equals("pc")){
                    backMap.put("sn",sn);
                    backMap.put("codeUrl",resultMap.get("code_url"));
                }
                map.put("payInfo", backMap);
            } catch (JDOMException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
