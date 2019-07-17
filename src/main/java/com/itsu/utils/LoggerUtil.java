package com.itsu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 苏犇
 * @date 2019/7/13 11:22
 */

public class LoggerUtil {

    private static String clazzName = "";

    private static final Logger logger = LoggerFactory.getLogger(clazzName);

    private static Logger log = null;

    private LoggerUtil() {
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void info(String msg, Class clazz) {
        log = LoggerFactory.getLogger(clazz);
        log.info(msg);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static void warn(String msg, Class clazz) {
        log = LoggerFactory.getLogger(clazz);
        log.warn(msg);
    }

    public static void warn(String msg, Class clazz, Throwable throwable) {
        log = LoggerFactory.getLogger(clazz);
        log.warn(msg, throwable);
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void error(String msg, Class clazz) {
        log = LoggerFactory.getLogger(clazz);
        log.error(msg);
    }

    public static void error(String msg, Class clazz, Throwable throwable) {
        log = LoggerFactory.getLogger(clazz);
        log.error(msg, throwable);
    }

    public static void debug(String msg) {
        logger.debug(msg);
    }

    public static void debug(String msg, Class clazz) {
        log = LoggerFactory.getLogger(clazz);
        log.debug(msg);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Logger getLogger(Class clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Logger getLogger(String clazzName) {
        return LoggerFactory.getLogger(clazzName);
    }
}
