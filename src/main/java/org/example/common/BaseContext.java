package org.example.common;

/**
 * 基于 ThreadLocal 的工具类
 * @author Maplerain
 * @date 2023/4/16 3:01
 **/
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 在当前线程存储 Id
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取当前线程的 Id
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
