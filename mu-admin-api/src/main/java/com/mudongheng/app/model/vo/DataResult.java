package com.mudongheng.app.model.vo;


import java.io.Serial;
import java.io.Serializable;

/**
 * 数据统一返回类型
 * @author 幕冬
 * @since 2022年03月21日
 */
public class DataResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected Boolean state;

    protected String msg;

    protected T data;

    public DataResult(Boolean state, String msg, T data) {
        this.state = state;
        this.msg = msg;
        this.data = data;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
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

    public static <T> DataResult<T> ok(String msg, T data) {
        return new DataResult<T>(true, msg, data);
    }

    public static <T> DataResult<T> ok() {
        return ok("操作成功！", null);
    }

    public static <T> DataResult<T> ok(String msg) {
        return ok(msg, null);
    }

    public static <T> DataResult<T> ok(T data) {
        return ok("操作成功！", data);
    }

    public static <T> DataResult<T> error(String msg, T data) {
        return new <T> DataResult<T>(false, msg, data);
    }

    public static <T> DataResult<T> error() {
        return error("操作失败！", null);
    }

    public static <T> DataResult<T> error(String msg) {
        return error(msg, null);
    }

    public static <T> DataResult<T> error(T data) {
        return error("操作失败！", data);
    }



}
