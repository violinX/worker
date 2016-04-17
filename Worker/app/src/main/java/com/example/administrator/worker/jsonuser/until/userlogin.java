package com.example.administrator.worker.jsonuser.until;

/**
 * Created by Administrator on 2016/4/14 0014.
 * 登录时json解析
 */
public class userlogin {

    private String result;
    private int status;
    private String reason;
    private data data;
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getReason() {
        return reason;
    }
    public void setrRason(String reason) {
        this.reason = reason;
    }
    public data getData() {
        return data;
    }
    public void setData(data data) {
        this.data = data;
    }

}
