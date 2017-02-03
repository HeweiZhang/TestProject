package com.david.okgodemo.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/11.
 */

public class HttpResult<T> implements Serializable {
    private int errcode;
    private T data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "errcode=" + errcode +
                ", data=" + data +
                '}';
    }
}
