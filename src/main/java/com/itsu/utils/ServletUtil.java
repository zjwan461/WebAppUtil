package com.itsu.utils;

import com.alibaba.fastjson.JSON;
import com.itsu.entity.JsonResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author 苏犇
 * @date 2019/7/9 22:36
 */

public class ServletUtil {
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;

    }

    public static HttpSession getSession() {
        HttpSession session = getRequest().getSession();
        return session;
    }

    public static boolean isAjax(HttpServletRequest request) {
        if (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
            return true;
        }
        return false;
    }

    public static String getRequestBasePath(HttpServletRequest request) {
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        return url;
    }

    public static String getRequestPath(HttpServletRequest request) {
        return getRequestBasePath(request) + request.getRequestURI();
    }


    public static <T> T writeJsonMsg(HttpServletRequest request, HttpServletResponse response, Exception e, Class<T> returnType, String errorCode) {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        T errorResult = null;
        try {
            pw = response.getWriter();
            errorResult = getErrorResult(request, response, e, returnType, errorCode);
            pw.write(JSON.toJSONString(errorResult));
            pw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            pw.close();
        }
        return errorResult;
    }

    public static <T> T getErrorResult(HttpServletRequest request, HttpServletResponse response, Exception e, Class<T> returnClass, String errorCode) throws IllegalAccessException, InstantiationException {
        T result = returnClass.newInstance();
        if (result instanceof ModelAndView) {
            ModelAndView view = (ModelAndView) result;
            view.setViewName("/error/" + errorCode);
            view.addObject("code", errorCode);
            view.addObject("msg", e.getMessage());
            view.addObject("time", LocalDateTime.now().toString());
            view.addObject("requestUrl", ServletUtil.getRequestPath(request));
            view.addObject("error", e.getClass().getName());
            return (T) view;
        } else if (result instanceof Map) {
            Map map = (Map) result;
            map.put("code", errorCode);
            map.put("msg", e.getMessage());
            map.put("time", LocalDateTime.now().toString());
            map.put("requestUrl", ServletUtil.getRequestPath(request));
            map.put("error", e.getClass().getName());
            return (T) map;
        } else {
            result = (T) JsonResult.error(errorCode, e.getMessage(), LocalDateTime.now(), ServletUtil.getRequestPath(request), e.getClass().getName());
            return result;
        }

    }
}
