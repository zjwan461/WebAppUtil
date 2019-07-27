package com.itsu.utils;

import com.alibaba.fastjson.JSON;
import com.itsu.annotation.TreeBean;
import com.itsu.annotation.TreeChildren;
import com.itsu.annotation.TreeId;
import com.itsu.annotation.TreePid;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 苏犇
 * @date 2019/7/27 16:38
 */

public class TreeUtil {

    private static Field ID;
    private static Field PID;
    private static Field children;
    private static List<Field> otherFields = new ArrayList<>();
    private static boolean sort_flag = false;
    private static Logger logger = LoggerUtil.getLogger(TreeUtil.class);


    private TreeUtil() {
    }

    public static <T> List<T> transferToTree(List<T> source) throws IllegalAccessException, InstantiationException {
        long beginTime = new Date().getTime();
        if (!check(source)) {
            logger.info("source list check not pass , source list will not transfer to a tree model");
            return source;
        }
        init(source);
        List<T> treeList = transfer(source, new ArrayList<>());
        logger.info("get TreeList :" + JSON.toJSONStringWithDateFormat(treeList, "yyyy-MM-dd HH:mm:ss:SSS"));
        long finishedTime = new Date().getTime();
        logger.info("transfer to tree completed , use time {} ms", (finishedTime - beginTime));
        return treeList;
    }

    private static <T> List<T> transfer(List<T> source, List<T> treeList) throws IllegalAccessException, InstantiationException {
        for (T t : source) {
            if (PID.get(t) == null) {
                T obj = (T) t.getClass().newInstance();
                ID.set(obj, ID.get(t));
                PID.set(obj, PID.get(t));
                invokeOthers(t, obj);
                children.set(obj, transfer(source, ID.get(t)));
                treeList.add(obj);
            }
        }
        if (sort_flag) {
            treeList.sort((x, y) -> {
                int result = 0;
                try {
                    result = ID.get(x).hashCode() - ID.get(y).hashCode();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return result;
            });
        }

        return treeList;
    }

    private static <T> List<T> transfer(List<T> source, Object pid) throws IllegalAccessException, InstantiationException {
        List<T> childrenList = new ArrayList<>();
        for (T t : source) {
            if (PID.get(t) != null && PID.get(t).equals(pid)) {
                T obj = (T) t.getClass().newInstance();
                ID.set(obj, ID.get(t));
                PID.set(obj, PID.get(t));
                invokeOthers(t, obj);
                children.set(obj, transfer(source, ID.get(t)));
                childrenList.add(obj);
            }
        }

        if (sort_flag) {
            childrenList.sort((x, y) -> {
                int result = 0;
                try {
                    result = ID.get(x).hashCode() - ID.get(y).hashCode();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return result;
            });
        }

        return childrenList;
    }

    private static <T> void invokeOthers(T t, T obj) throws IllegalAccessException {
        for (Field field : otherFields) {
            field.set(obj, field.get(t));
        }
    }

    private static <T> void init(List<T> source) {
        logger.info("init start ...");
        sort_flag = source.get(0).getClass().getAnnotation(TreeBean.class).sort();
        Field[] fields = source.get(0).getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(TreeId.class)) {
                ID = field;
                ID.setAccessible(true);
                continue;
            }
            if (field.isAnnotationPresent(TreePid.class)) {
                PID = field;
                PID.setAccessible(true);
                continue;
            }
            if (field.isAnnotationPresent(TreeChildren.class)) {
                children = field;
                children.setAccessible(true);
                continue;
            }

            field.setAccessible(true);
            otherFields.add(field);

        }


        logger.info("init finished ...");
    }

    private static <T> boolean check(List<T> source) {
        logger.info("check source begin ...");
        if (CollectionUtils.isEmpty(source)) {
            logger.warn("source list is empty ...");
            return false;
        }

        T t = source.get(0);
        if (t.getClass().getAnnotation(TreeBean.class) == null) {
            logger.warn("source Bean not marked by @TreeBean");
            return false;
        }

        List<Field> idFileds = new ArrayList<>();
        List<Field> pidFileds = new ArrayList<>();
        List<Field> childrenFields = new ArrayList<>();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(TreeId.class)) {
                idFileds.add(field);
            }
            if (field.isAnnotationPresent(TreePid.class)) {
                pidFileds.add(field);
            }
            if (field.isAnnotationPresent(TreeChildren.class)) {
                childrenFields.add(field);
            }
        }
        if (idFileds.size() != 1) {
            logger.warn("source bean expire one Field marked by @TreeId , but found {} of it", idFileds.size());
            return false;
        }
        if (pidFileds.size() != 1) {
            logger.warn("source bean expire one Field marked by @TreePid , but found {} of it", pidFileds.size());
            return false;
        }
        if (childrenFields.size() != 1) {
            logger.warn("source bean expire one Field marked by @TreeChildren , but found {} of it", childrenFields.size());
            return false;
        }
        logger.info("check finished ...");
        return true;
    }


}
