package com.ceeses.web.result;

/**
 * JSON结果
 *
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-05-29 21:${MIMUTE}
 */
public class JsonResult {
    /**
     * 结果码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

    public JsonResult() {
        this(ResultCode.SUCCEED, "", null);
    }

    public JsonResult(Object data) {
        this(ResultCode.SUCCEED, "", data);
    }

    public JsonResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 结果代码
     */
    public static final class ResultCode {
        /**
         * 成功
         */
        public static final int SUCCEED = 0;
        /**
         * 失败
         */
        public static final int FAILED = 1;
    }
}
