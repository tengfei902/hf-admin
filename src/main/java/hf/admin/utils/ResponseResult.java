package hf.admin.utils;

/**
 * Created by tengfei on 2017/11/6.
 */
public class ResponseResult<T> {

    private static final String SUCCESS_CODE = "0000000";
    private static final String SUCCESS_MSG = "SUCCESS";

    private String code;
    private String msg;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
