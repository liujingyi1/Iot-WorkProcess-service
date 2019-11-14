package com.rgk.workprocess.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnObject {
    private int code = 1; // 编码
    private String message = "success"; // 提示消息
    private Object result; // 数据
    private long timestamp;

    public ReturnObject(int code, Object result, String message) {
        super();
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ReturnObject setCode(int code) {
        this.code = code;
        return this;
    }

    public ReturnObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public ReturnObject setResult(Object result) {
        this.result = result;
        return this;
    }


    public ReturnObject setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
