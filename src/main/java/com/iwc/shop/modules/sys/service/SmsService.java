package com.iwc.shop.modules.sys.service;

import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.modules.sys.dao.SmsDao;
import com.iwc.shop.modules.sys.entity.Sms;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 短信Service
 * @author Tony Wong
 * @version 2015-07-13
 */
@Service
@Transactional(readOnly = true)
public class SmsService extends CrudService<SmsDao, Sms> {
    /**
     * 验证注册用户的手机验证码
     */
    public boolean checkRegisterCode(String mobile, String code) {
        if (StringUtils.isBlank(code))
            return false;

        Sms sms = findLastOneBy(mobile, Sms.TYPE_REGISTER);
        if (sms == null)
            return false;

        if (!code.equals(sms.getCode()))
            return false;

        Date date = new Date();
        if (sms.getExpiredDate() == null || sms.getExpiredDate().getTime() < date.getTime())
            return false;

        return true;
    }

    /**
     * 验证重置密码的手机验证码
     */
    public boolean checkForgetPasswordCode(String mobile, String code) {
        if (StringUtils.isBlank(code))
            return false;

        Sms sms = findLastOneBy(mobile, Sms.TYPE_FORGET_PASSWORD);
        if (sms == null)
            return false;

        if (!code.equals(sms.getCode()))
            return false;

        Date date = new Date();
        if (sms.getExpiredDate() == null || sms.getExpiredDate().getTime() < date.getTime())
            return false;

        return true;
    }

    /**
     * 通过type，mobile查找最后一条验证码
     */
    public Sms findLastOneBy(String mobile, String type) {
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(type))
            return null;

        Sms sms = new Sms();
        sms.setMobile(mobile);
        sms.setType(type);
        return dao.findLastOneBy(sms);
    }
}
