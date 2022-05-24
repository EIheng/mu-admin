package com.mudongheng.app.component;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.model.vo.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 幕冬
 * @since 2022年03月26日
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        log.info("启用全局异常处理器");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DataResult<Object> handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return DataResult.error("请求失败，请联系后台管理员！");
    }

    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public DataResult<Object> loginException(NotLoginException e) {
        log.warn(e.getMessage());
        return DataResult.error("登录凭证失效，请重新登录！");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DataResult<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String defaultMessage;
        if (fieldError == null) {
            // 一般不会到这步...但IDEA报错
            defaultMessage = "表单验证错误";
        } else {
            defaultMessage = StpUtil.isLogin() ? "用户 %s 触发表单异常：%s".formatted(StpUtil.getLoginIdAsInt(), fieldError.getDefaultMessage()) : fieldError.getDefaultMessage();
        }
        log.warn(defaultMessage);
        return DataResult.error(defaultMessage);
    }

    @ExceptionHandler(ParamException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DataResult<Object> paramException(ParamException e) {
        log.warn(e.getMessage());
        return DataResult.error(e.getMessage());
    }
}
