package com.geeker.config;

/**
 * Created by Administrator on 2017/10/30 0030.
 */
public class AlipayConfig {
    // 商户appid     2016090900468100
    public static String APPID = "2017102609536749";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi2ZCDvrW0AY7zui8a1oclZOsB+GgM9sAkoxQdo4xFwPy7yp1qNLH+Nsp6aq4BFGx/4kkCeiFp5n13Em9TE1yh+i0HY2M/W3x+yj2TNJFhQXkTsMoye/1T5DdDfchRzbD5xE944iGMZpyPiXS0ByBsfnWCMIDiLWYR6obj6KaHU9DMd8dCAruELulNqYEUAgWz6cVmsxkpx0tLzeps3e0vLrVSSk++cbQGwpulAbdzRvciXCfGhW9NMAv0CQsLBXgsmoAXBbiG0PGHvq29tLiNfcNnNDLu8bSy6KMg8PnPMH+QbX9x23QgF9kH48XaaJA8BiRrH2AexVWP2WUD//y6QIDAQAB";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCMj2O2uEmwJ5CJ+Ond5nT9CU0c1drV0+khm3XZ74lrVptvFkP+kyuZ+0Rro5IbrY8YTA7B2MvQL/Q8n4W5S/O1R97GohHOyCGbUvhA7uj1zBFgzkBpHj7EI2nmvoRhCnFtys+d2cwgfDsQ/ZhsEZa82htUmW0OuijZZMFP/eCHCsdL54tIrTzbaMKd9YnLm8KVQ2KphHW3ByePq+t6aebppToMTro4AvWDmb69XeNHSRXesARyhKTWDM8Of3BYvEqTZKpMnDdx4yKeWKTAJd8MLFtFy/azVjlZhScoF6hNp+mqInthiNO4+VnpPfidK3jPURVHbrSrU71nWdXZjGmhAgMBAAECggEBAIjaEvuT8irQWqn677hc6Qp0Q+APV0FZUWKnOdc2oVUgvbUquIjkQU9qlPM4Dslj8i513uhdbBMGnYVoy4ndqSqXDw/WPABYWmAgKBUQh0DJryHRrmSqTfIpiLJgTAMvZ8C99eZ6Z4Q5cEjan8yYU2sL8BAuUUBFgAApWQ3I4RKYrbCvR7T8tG1aTsdIjTJ10I+xWvGs0BDWKrQuz3H3H1UD1btqtAmE6g74zwaE5xnnhqWleQhSilFW9kp2zd4kXMxqyYElqvA45NGVNT65Qr3VAqkXXXp4M52nfAHXEJAepZB3WPznmcWV+2E5Qa9jk7GTKRImNqfDkArC/fGwWQECgYEA9tFLU+HeOV2D5BSEwi3u9xUABleZLSmBJPM7105DXWJPe2a53jCuY3TY8SbARIghD2mZJdMOZHKejb5jFalZMuXkUF6xzzLF7nBxhCGkUV2ZXwm69hrNpu/v0Z+fQshBTlC+ApTsbIMf+qlHUl5j1c6FK51SOFf7UAUCPjSouVMCgYEAkcoXwOKN4nTZEsiDL18CzQUBdNRTyC1WhoysJgPPrfD7tB9J8u2ufW+sJB9k8pZFbFej+F96zHDn7wMnz7A2b0wnrXXQjaWtrlwlPQprdx8zdvp8TlNgEvd39ZeLESSqb4JcFsKYNDBD7YkwSFxqaq+AULyJk+Rxn6dWRglbjrsCgYEApVHQ69GZYMEzi3z2432S12r4hkb8cZSgTRii13czhgRIirZm/t2KoVFt0jELjZvE0ScBDqXMuRlQ/E4u3h59gascf3y+CrPOggRM9Jz4DAvupArHcPrJDu0drEIHkdrCa2uHqgEITzKRI/toq1JP+rEG4AzTOqwryBBizTnGXcMCgYEAjzV+mdmyWGZBqcLPoqPpA23MXXAJENUAbtEWdY0+33WeOvSVVo2F3u/on9868VUNwvxgNKXN4a9zFYT7AXfuMOwU+b6Ga+34qxMW/VwT7oL2sgQiQWqDMH5pFxWS5PcoeLsMLMtgabuTrm0ZwasOWdgl6qkZV+6KcrMU+11L0+sCgYA9GuBimR15stlsWivdZBUzQEpgWYHSufXCOaWalCOjdHaJ9xEddWH2Zo514IHCKTWSEwRZ/FwTUjyeg//12mPGQ4EJFRluOrhCwMe/kQrg2227Z1isXmyXpuhUcBzqZCaKMs4aUBQr20HZV6geNGltfVzFplWvmYH57xNG4fJMzw==";

    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //本地 测试用
    public static String notify_url = "http://geeker.worken.cn/thirdPay/pay/aliPayNotify";
    // 请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";

    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://geeker.worken.cn/view/Finance/Finance/list.html";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
