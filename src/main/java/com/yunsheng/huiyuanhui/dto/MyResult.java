package com.yunsheng.huiyuanhui.dto;

public class MyResult<T> {
    T result;
    boolean success;
    int status; // 0:成功，1：失败
    String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static MyResult getSuccessReault(Object t) {
        MyResult myResult = new MyResult();
        myResult.setSuccess(true);
        myResult.setStatus(0);
        myResult.setResult(t);
        return myResult;
    }

    public static MyResult getFailReault(String msg) {
        MyResult myResult = new MyResult();
        myResult.setSuccess(false);
        myResult.setStatus(1);
        myResult.setMsg(msg);
        return myResult;
    }
}
