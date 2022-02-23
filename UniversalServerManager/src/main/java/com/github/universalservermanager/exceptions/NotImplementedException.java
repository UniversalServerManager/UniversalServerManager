package com.github.universalservermanager.exceptions;

public class NotImplementedException extends Exception{
    String message;
    String methodName;

    public NotImplementedException(String message, String methodName) {
        this.message = message;
        this.methodName = methodName;
    }
    public NotImplementedException(String message) {
        this.message = message;
        this.methodName = this.getStackTrace()[0].getMethodName();
    }
    public NotImplementedException() {
        this("This method is not implemented.");
    }
}
