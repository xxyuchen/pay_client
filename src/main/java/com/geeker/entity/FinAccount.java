package com.geeker.entity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * 
 * 计费账户
 * 
 **/
@Entity
@DynamicUpdate
@DynamicInsert
public class FinAccount implements Serializable {
	@Id
	@GeneratedValue
	/**计费账户，生成格式‘YYYYMMDD’ + 随机码生成**/
	private String id;

	/**账户余额（如数据最新，则该字段为用户当前余额）**/
	private java.math.BigDecimal money;

	/**最近账户变动时间**/
	private java.util.Date updateTime;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setMoney(java.math.BigDecimal money){
		this.money = money;
	}

	public java.math.BigDecimal getMoney(){
		return this.money;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

}
