package org.example.exception;


/**
 * @author Maplerain
 * @date 2023/4/16 22:59
 **/
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
