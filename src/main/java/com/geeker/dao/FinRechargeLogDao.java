package com.geeker.dao;

import com.geeker.entity.FinRechargeLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/11/6 0006.
 */
public interface FinRechargeLogDao extends JpaRepository<FinRechargeLog,Integer> {

    FinRechargeLog findBySn(String sn);
}
