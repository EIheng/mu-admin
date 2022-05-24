package com.mudongheng.app.exception;

/**
 * 通用表单验证错误，提交后返回状态码200与空值，但不报错
 *
 * @author 幕冬
 * @since 2022年04月24日
 */
public class ParamException extends RuntimeException {

    public ParamException(String message) {
        super(message);
    }
}
