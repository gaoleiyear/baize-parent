package com.baize.base.common.model;


import com.baize.base.common.code.ApiCode;
import com.baize.base.common.exception.RdcException;
import com.baize.base.common.util.MapBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lqw
 * @date 2020-10-9
 * @what api 返回结果集
 */
@Data
@ApiModel("通用返回结果")
public class ApiResult<T> implements Serializable {
    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private int code;
    /**
     * 结果信息
     */
    @ApiModelProperty("结果信息")
    private String message;
    /**
     * 结果数据
     */
    private T data;
    /**
     * debug信息(例如异常堆栈)
     */
    @ApiModelProperty("debug信息(例如异常堆栈)")
    private Object debug;

    public ApiResult() {
    }

    public ApiResult(ApiCode apiCode) {
        this(apiCode.getCode(), apiCode.getMessage());
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ApiResult(ApiCode apiCode, T data) {
        this(apiCode.getCode(), apiCode.getMessage());
        this.data = data;
    }

    private ApiResult(ApiCode apiCode, T data, Object debug) {
        this(apiCode.getCode(), apiCode.getMessage());
        this.data = data;
        this.debug = debug;
    }

    public static ApiResult ok() {
        return new ApiResult(ApiCode.SUCCESS);
    }

    public static <T> ApiResult<T> ok(T value) {
        return new ApiResult<>(ApiCode.SUCCESS, value);
    }

    public static ApiResult ok(String name, Object value) {
        return new ApiResult<>(ApiCode.SUCCESS, MapBuilder.<String, Object>builder().put(name, value).build());
    }

    public static ApiResult ok(Map<String, Object> data) {
        return new ApiResult<>(ApiCode.SUCCESS, data);
    }

    public static ApiResult error(RdcException exception) {
        return new ApiResult(exception.getCode() == null ? 500 : exception.getCode(), exception.getMessage());
    }

    public static ApiResult error(ApiCode code, String message) {
        return new ApiResult(code.getCode(), message);
    }

    public static ApiResult error(ApiCode code) {
        return new ApiResult(code);
    }

    public static <T> ApiResult<T> error(ApiCode code, T value) {
        return new ApiResult<>(code, value);
    }

    public static ApiResult error(ApiCode code, String name, Object value) {
        return new ApiResult<>(code, MapBuilder.builder().put(name, value).build());
    }

    public static ApiResult error(ApiCode code, Map<String, Object> debug) {
        return new ApiResult<>(code, null, debug);
    }
}

