package com.buildit.common.application.exceptions;

public class StatusChangeNotAllowedException extends Exception {
    public StatusChangeNotAllowedException(){}
    public StatusChangeNotAllowedException(String message){
        super(message);
    }
}
