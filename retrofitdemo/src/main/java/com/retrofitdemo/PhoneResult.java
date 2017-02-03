package com.retrofitdemo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/22.
 */

public class PhoneResult {


    /**
     * code : 10001
     * desc : success
     * data : {"method":"GET"}
     */

    private int code;
    private String desc;
    /**
     * method : GET
     */

//    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

/*    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String method;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }*/
}
