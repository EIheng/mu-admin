package com.mudongheng.app.component;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.stp.StpUtil;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.model.vo.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

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
    public DataResult<Object> exception(Exception e) {
        log.error(e.getMessage(), e);
        return DataResult.error("请求失败，请联系后台管理员！");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DataResult<Object> runtimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return DataResult.error("请求失败，请联系后台管理员！");
    }

    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public DataResult<Object> loginException(NotLoginException e) {
        log.warn(e.getMessage());
        return DataResult.error("登录凭证失效，请重新登录！");
    }

    @ExceptionHandler(NotRoleException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DataResult<Object> notRoleException(NotRoleException e, HttpServletRequest request) {
        if (StpUtil.isLogin()) {
            log.warn("用户 {} 不是 {}，没有调用 {} 的权限", StpUtil.getLoginId(), e.getRole(), request.getRequestURI());
        } else {
            log.info(e.getMessage());
        }
        return DataResult.error("权限不足！");
    }

    @ExceptionHandler(NotPermissionException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DataResult<Object> notPermissionException(NotPermissionException e) {
        if (StpUtil.isLogin()) {
            log.warn("用户 {} 尝试调用无权限的方法 {}", StpUtil.getLoginId(), e.getPermission());
        } else {
            log.info(e.getMessage());
        }
        return DataResult.error("权限不足！");
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
            defaultMessage = fieldError.getDefaultMessage();
        }
        if (StpUtil.isLogin()) {
            log.warn("用户 {} 触发表单异常 {}", StpUtil.getLoginId(), defaultMessage);
        }
        return DataResult.error(defaultMessage);
    }

    @ExceptionHandler(ParamException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DataResult<Object> paramException(ParamException e) {
        log.warn(e.getMessage());
        return DataResult.error(e.getMessage());
    }
}
