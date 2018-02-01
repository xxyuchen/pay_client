package com.geeker.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * 
 * 用户充值流水
 * 
 **/
@SuppressWarnings("serial")
@Entity
public class FinRechargeLog implements Serializable {
	@Id
	@GeneratedValue
	/****/
	private Integer id;

	/**计费账户，生成格式‘YYYYMMDD’ + 随机码生成**/
	private String finAccountId;

	/**充值人id**/
	private Integer userId;

	/**充值人姓名**/
	private String userName;

	/**1-微信 2-支付宝**/
	private Integer payChannel;

	/**0-未支付 1 - 已支付 2-充值成功  9 删除**/
	private Integer status;

	/**充值单号**/
	private String sn;

	/**账户余额（充值前）**/
	private java.math.BigDecimal accountMoney;

	/**最终金额，充值前+ 充值金额求和**/
	private java.math.BigDecimal finalMoney;

	/**充值金额**/
	private java.math.BigDecimal rechargeMoney;

	/**可开票金额**/
	private java.math.BigDecimal invoiceMoney;

	/**充值时间**/
	private java.util.Date createTime;

	/**开票id**/
	private Integer finInvoiceApplyId;

	/**开票标记 0-未开票 1-已开票**/
	private Integer invoiceStatus;

	private String finRechargeId;

	public String getFinRechargeId() {
		return finRechargeId;
	}

	public void setFinRechargeId(String finRechargeId) {
		this.finRechargeId = finRechargeId;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setFinAccountId(String finAccountId){
		this.finAccountId = finAccountId;
	}

	public String getFinAccountId(){
		return this.finAccountId;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setPayChannel(Integer payChannel){
		this.payChannel = payChannel;
	}

	public Integer getPayChannel(){
		return this.payChannel;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setSn(String sn){
		this.sn = sn;
	}

	public String getSn(){
		return this.sn;
	}

	public void setAccountMoney(java.math.BigDecimal accountMoney){
		this.accountMoney = new java.math.BigDecimal(accountMoney.toPlainString());
	}

	public java.math.BigDecimal getAccountMoney(){
		return this.accountMoney;
	}

	public void setFinalMoney(java.math.BigDecimal finalMoney){
		this.finalMoney = new java.math.BigDecimal(finalMoney.toPlainString());
	}

	public java.math.BigDecimal getFinalMoney(){
		return this.finalMoney;
	}

	public void setRechargeMoney(java.math.BigDecimal rechargeMoney){
		this.rechargeMoney = rechargeMoney;
	}

	public java.math.BigDecimal getRechargeMoney(){
		return this.rechargeMoney;
	}

	public void setInvoiceMoney(java.math.BigDecimal invoiceMoney){
		this.invoiceMoney = invoiceMoney;
	}

	public java.math.BigDecimal getInvoiceMoney(){
		return this.invoiceMoney;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setFinInvoiceApplyId(Integer finInvoiceApplyId){
		this.finInvoiceApplyId = finInvoiceApplyId;
	}

	public Integer getFinInvoiceApplyId(){
		return this.finInvoiceApplyId;
	}

	public void setInvoiceStatus(Integer invoiceStatus){
		this.invoiceStatus = invoiceStatus;
	}

	public Integer getInvoiceStatus(){
		return this.invoiceStatus;
	}

}
