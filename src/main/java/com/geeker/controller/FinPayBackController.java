package com.geeker.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.geeker.config.AlipayConfig;
import com.geeker.config.WeChatPayConfig;
import com.geeker.dao.FinAccountDao;
import com.geeker.dao.FinRechargeLogDao;
import com.geeker.entity.FinAccount;
import com.geeker.entity.FinRechargeLog;
import com.geeker.utils.PayCommonUtil;
import com.geeker.utils.XMLUtil;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
* @Author TangZhen
* @Date 2017/11/6 0006 13:57
* @Description  充值回调
*/
@RestController
public class FinPayBackController {
    @Autowired
    private FinRechargeLogDao finRechargeLogDao;
    @Autowired
    private FinAccountDao finAccountDao;

    private Logger logger = LoggerFactory.getLogger(FinPayBackController.class);
    /**
     * 支付宝回调
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/aliPayNotify")
    @ResponseBody
    public String alipayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"),"gbk");
                params.put(name, valueStr);
            }
            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            // 支付宝交易号
            //String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            // 计算得出通知验证结果
            // boolean AlipaySignature.rsaCheckV1(Map<String, String> params,
            // String publicKey, String charset, String sign_type)
            boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.CHARSET, "RSA2");
            if (verify_result) {            // 验证成功
                // 请在这里加上商户的业务逻辑程序代码
                FinRechargeLog finRechargeLog = finRechargeLogDao.findBySn(out_trade_no);
                if (finRechargeLog == null) {
                    return "fail";
                }
                if(trade_status.equals("TRADE_FINISHED")) {
                    // 判断该笔订单是否在商户网站中已经做过处理
                    // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    // 如果有做过处理，不执行商户的业务程序
                    // 注意：
                    // 如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    // 如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                    return "fail";
                }
                if (trade_status.equals("TRADE_SUCCESS")) {
                    // 判断该笔订单是否在商户网站中已经做过处理
                    // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    // 如果有做过处理，不执行商户的业务程序

                    // 注意：
                    // 如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                    //查询账户余额
                    FinAccount finAccount = finAccountDao.findById(finRechargeLog.getFinAccountId());
                    BigDecimal accountMoney = finAccount.getMoney();
                    finRechargeLog.setAccountMoney(finAccount.getMoney());
                    finRechargeLog.setFinalMoney(finAccount.getMoney().add(finRechargeLog.getRechargeMoney()));
                    finRechargeLog.setId(finRechargeLog.getId());
                    finRechargeLog.setStatus(1);
                    finRechargeLogDao.save(finRechargeLog);
                    //修改账户金额
                    finAccount.setMoney(finAccount.getMoney().add(finRechargeLog.getRechargeMoney()));
                    finAccount.setUpdateTime(new Date());
                    finAccountDao.save(finAccount);
                    //return  finRechargeLogService.updateForPay(frl,fa);
                    logger.error("支付宝充值--账户：{}，账户原金额：{},充值金额：{}，充值后金额{}，订单号：{}",finRechargeLog.getFinAccountId(),accountMoney,finRechargeLog.getRechargeMoney(),finAccount.getMoney(),out_trade_no);
                }
                return "success";
            } else {// 验证成功
                return "fail";
            }
        } catch (Exception e) {
            //logger.error("ApiPayBackController aliPayNotify fail : 支付宝回调失败！");
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value="/weChatNotify",method = RequestMethod.POST)
    @ResponseBody
    public void wxNotify(HttpServletRequest request,HttpServletResponse response) throws IOException, JDOMException {
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();
        //解析xml成map
        Map<String, String> m = XMLUtil.doXMLParse(sb.toString());
        //过滤空 设置 TreeMap
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        //判断签名是否正确
            String resXml = "";
        if(PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
            //判断是否支付成功
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                String mch_id = (String)packageParams.get("mch_id"); //商户号
                String out_trade_no = (String)packageParams.get("out_trade_no"); //商户订单号
                String total_fee = (String)packageParams.get("total_fee");
                //查询订单 根据订单号查询订单  GoodsTrade -订单实体类
                FinRechargeLog finRechargeLog = finRechargeLogDao.findBySn(out_trade_no);
                if(!WeChatPayConfig.MCH_ID.equals(mch_id)||finRechargeLog==null||new BigDecimal(total_fee).compareTo(finRechargeLog.getRechargeMoney().multiply(new BigDecimal(100))) != 0){
                    logger.info("支付失败,错误信息：" + "参数错误");
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                }else{
                    if(finRechargeLog.getStatus() == 0){//支付状态--未支付
                        //订单状态的修改。根据实际业务逻辑执行
                        try{
                            FinAccount finAccount = finAccountDao.findById(finRechargeLog.getFinAccountId());
                            BigDecimal accountMoney = finAccount.getMoney();
                            finRechargeLog.setAccountMoney(finAccount.getMoney());
                            finRechargeLog.setRechargeMoney(new BigDecimal(total_fee).divide(new BigDecimal(100)));
                            finRechargeLog.setFinalMoney(finAccount.getMoney().add(finRechargeLog.getRechargeMoney()));
                            finRechargeLog.setId(finRechargeLog.getId());
                            finRechargeLog.setStatus(1);//已支付
                            finRechargeLogDao.save(finRechargeLog);
                            //修改账户金额
                            finAccount.setUpdateTime(new Date());
                            finAccount.setMoney(finRechargeLog.getFinalMoney());
                            finAccountDao.save(finAccount);
                            logger.error("微信充值--账户：{}，账户原金额：{},充值金额：{}，充值后金额{}，订单号：{}",finRechargeLog.getFinAccountId(),accountMoney,finRechargeLog.getRechargeMoney(),finAccount.getMoney(),out_trade_no);

                        }catch(Exception e){
                            e.printStackTrace();
                            logger.error("WeixinPayController wxNotify  is fail -----微信回调支付失败", e);
                        }
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    }else{
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                        logger.info("订单已处理");
                    }
                }

            }else {
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
        } else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
            logger.info("通知签名验证失败");
        }
        //------------------------------
        //处理业务完毕
        //------------------------------
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();

    }


    @RequestMapping(value = "/test.do")
    @ResponseBody
    public String testUpdate(){
        /*FinRechargeLog finRechargeLog = finRechargeLogDao.findBySn("20171106204724746222");
        FinAccount finAccount = finAccountDao.findById(finRechargeLog.getFinAccountId());
        finRechargeLog.setAccountMoney(finAccount.getMoney());
        finRechargeLog.setFinalMoney(new BigDecimal(0));
        finRechargeLog.setStatus(2);
        finRechargeLogDao.save(finRechargeLog);*/
        return "suss";
    }
}
