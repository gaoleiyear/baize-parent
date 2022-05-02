package com.baize.base.common.exception;

import com.baize.base.common.code.ApiCode;
import com.baize.base.common.enums.EnvType;
import com.baize.base.common.model.ApiResult;
import com.baize.base.common.util.MapBuilder;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: lqw
 * @date: 2020/10/9
 * @what: 方法中可以加参数: HttpServletRequest request,HttpServletResponse response
 * 定义异常无需按顺序. spring会按继承关系处理
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdviceHandler {
    @Value("${spring.profiles.active:prod}")
    private String env;

    /**
     * Valid异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exception(BindException e) {
        StringBuilder msg = new StringBuilder();
        Iterator<ObjectError> iterator = e.getBindingResult().getAllErrors().iterator();
        while (iterator.hasNext()) {
            msg.append(iterator.next().getDefaultMessage());
            if (iterator.hasNext()) {
                msg.append(", ");
            }
        }
        return ApiResult.error(ApiCode.REQUEST_PARAM_ERROR, msg.toString());
    }

    /**
     * 没有对应的请求
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exception(NoHandlerFoundException e) {
        Map<String, Object> exceptionInfo = getExceptionInfo(e);
        return ApiResult.error(ApiCode.SERVICE_UNAVAILABLE, exceptionInfo);
    }

    /**
     * 参数转换错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exception(MethodArgumentTypeMismatchException e) {
        Map<String, Object> exceptionInfo = getExceptionInfo(e);
        return ApiResult.error(ApiCode.REQUEST_PARAM_ERROR, exceptionInfo);
    }

    /**
     * 参数转换错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentConversionNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exception(MethodArgumentConversionNotSupportedException e) {
        Map<String, Object> exceptionInfo = getExceptionInfo(e);
        return ApiResult.error(ApiCode.REQUEST_PARAM_ERROR, exceptionInfo);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exception(MissingServletRequestParameterException e) {
        return ApiResult.error(ApiCode.REQUEST_PARAM_MISSING, "缺少参数:" + e.getParameterName());
    }

    /**
     * 不支持的请求方式
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exception(HttpRequestMethodNotSupportedException e) {
        Map<String, Object> exceptionInfo = getExceptionInfo(e);
        return ApiResult.error(ApiCode.SERVICE_UNAVAILABLE, exceptionInfo);
    }

    /**
     * rdc service异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RdcException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exception(RdcException e) {
        return ApiResult.error(e);
    }

    /**
     * 参数校验
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exception(ConstraintViolationException e) {
        StringBuilder msg = new StringBuilder();
        Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator();
        while (it.hasNext()) {
            msg.append(it.next().getMessage());
            if (it.hasNext()) {
                msg.append(", ");
            }
        }
        return ApiResult.error(ApiCode.REQUEST_PARAM_ERROR, msg.toString());
    }

    /**
     * cookie错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exception(MissingRequestCookieException e) {
        return ApiResult.error(ApiCode.COOKIE_ERROR);
    }

    /**
     * 枚举反序列化
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exception(MethodArgumentNotValidException e) {
        StringBuilder msg = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();
        Iterator<ObjectError> it = bindingResult.getAllErrors().iterator();
        while (it.hasNext()) {
            msg.append(it.next().getDefaultMessage());
            if (it.hasNext()) {
                msg.append(", ");
            }
        }
        return ApiResult.error(ApiCode.REQUEST_PARAM_ERROR, msg.toString());
    }

    /**
     * 兜底
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exception(Exception e) {
        log.error(ExceptionReader.readMessage(e), e);
        Map<String, Object> exceptionInfo = getExceptionInfo(e);
        return ApiResult.error(ApiCode.INTERNAL_SERVER_ERROR, exceptionInfo);
    }

    private Map<String, Object> getExceptionInfo(Exception e) {
        Map<String, Object> exceptionInfo;
        if (EnvType.PROD.getActive().equals(env)) {
            exceptionInfo = Maps.newHashMap();
        } else {
            exceptionInfo = MapBuilder.<String, Object>builder().put("exception", e.toString()).put("trace",
                    Arrays.stream(e.getStackTrace()).map(trace -> trace.toString()).collect(Collectors.toList())).build();
        }
        return exceptionInfo;
    }
}

