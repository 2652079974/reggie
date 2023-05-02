package org.example.common;

import java.sql.Time;
import java.util.Map;

/**
 * 时效性数据对象
 *
 * @author Maplerain
 * @date 2023/4/20 20:21
 **/
public class TimelinessDataObject<T> extends TDO {

    public TimelinessDataObject(Long validTime, T data) {
        super(validTime, data);
    }
}
