package com.itsu.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 苏犇
 * @date 2019/7/15 22:13
 */

public class ValidateUtil {

    public static final String MAIL = "MAIL";

    public static final String PHONE = "PHONE";

    public static final String MAIL_REG = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public static final String PHONE_REG = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

    public static boolean validate(String data, String type) {
        boolean result = false;
        if (StringUtils.isNotBlank(data)) {
            if (MAIL.equalsIgnoreCase(type)) {
                LoggerUtil.info("data match Mail", ValidateUtil.class);
                result = data.matches(MAIL_REG);
            } else if (PHONE.equalsIgnoreCase(type)) {
                LoggerUtil.info("data match PHONE", ValidateUtil.class);
                result = data.matches(PHONE_REG);
            } else {
                LoggerUtil.warn("not valid data type , only support MAIL or Phone type ...", ValidateUtil.class);
            }
        } else {
            LoggerUtil.warn("data is null or '', return false", ValidateUtil.class);
        }

        return result;
    }

    public static String checkMailOrPhone(String data) {
        if (StringUtils.isNotBlank(data)) {
            if (data.matches(MAIL_REG)) {
                return MAIL;
            } else if (data.matches(PHONE_REG)) {
                return PHONE;
            } else {
                return "undefined";
            }
        }
        return "undefined";
    }
}
