package com.geeker.dao;

import com.geeker.entity.FinAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/11/6 0006.
 */
public interface FinAccountDao extends JpaRepository<FinAccount,Integer> {
    FinAccount findById(String id);
}
