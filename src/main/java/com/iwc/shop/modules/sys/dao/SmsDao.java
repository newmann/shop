package com.iwc.shop.modules.sys.dao;

import com.iwc.shop.common.persistence.CrudDao;
import com.iwc.shop.common.persistence.annotation.MyBatisDao;
import com.iwc.shop.modules.sys.entity.Sms;

/**
 * 短信发送接口
 * @author Tony Wong
 * @version 2014-05-16
 */
@MyBatisDao
public interface SmsDao extends CrudDao<Sms> {

    /**
     * 通过 code，type，mobile查找最后一条验证码
     */
    public Sms findLastOneBy(Sms sms);

}
