package org.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Maplerain
 * @date 2023/4/5 22:25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int code;
    private String msg;;
    private Object data;


    public static Result success(Object data) {
        return new Result(1,"success",data);
    }

    public static Result success() {
        return new Result(1,"success",null);
    }

    public static Result error(String msg){
        return new Result(0,msg,null);
    }


}
