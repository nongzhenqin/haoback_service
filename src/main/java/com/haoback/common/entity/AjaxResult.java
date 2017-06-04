package com.haoback.common.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * ajax请求返回对象
 * Created by nong on 2017/4/10.
 */
public class AjaxResult implements Serializable {
    private long status;
    private String statusText;
    private Object data;
    private Map<String, Object> datas;

    public AjaxResult(){}


    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getDatas() {
        return datas;
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }
}
