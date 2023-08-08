package com.HelloSpring.GlobalException;

public class UnAuthorizedUser extends RuntimeException {

    public UnAuthorizedUser(String msg) {
        super(msg);
    }

}
