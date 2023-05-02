package org.example.common;

import java.util.concurrent.TimeoutException;

/**
 * 封装
 *
 * @author Maplerain
 * @date 2023/4/20 20:36
 **/
public class TDO<T>{
    private final Long dateLine;

    private final T data;

    public TDO(Long validTime, T data) {
        this.dateLine = validTime + System.currentTimeMillis();
        this.data = data;
    }

    public T getData() throws Exception {
        if (System.currentTimeMillis() > dateLine) {
            throw new TimeoutException("数据超过有效期！");
        }
        return this.data;
    }
}
