package com.baize.base.common.code;


import com.baize.base.common.enums.CodeConfigEnum;

import java.io.Serializable;

/**
 * 枚举常见的返回码
 */
public enum ApiCode implements BaseCodeInterface, Serializable {

    SUCCESS(200, "OK"),
    PARAM_ERROR(1101, "校验错误"),
    REQUEST_PARAM_ERROR(1000, "传入参数错误"),
    REQUEST_PARAM_MISSING(1001, "缺少参数"),
    COOKIE_ERROR(1100, "cookie错误"),
    BIZ_EXCEPTION(1200, "业务异常"),
    INTERNAL_SERVER_ERROR(1300, "服务器错误"),
    SERVICE_UNAVAILABLE(1400, "操作失败，请重试"),
    TOKEN_ERROR(1500, "token校验失败"),
    SIGN_ERROR(1600, "签名校验失败"),
    USER_ERROR(1700, "用户校验失败"),

    APP_ID_NOT_FOUND(2000, "appId不能为空"),
    PROFILE_ID_NOT_FOUND(2001, "profileId不能为空"),
    TOKEN_NOT_FOUND(2002, "token不能为空"),
    GID_NOT_FOUND(2003, "gid不能为空"),
    ID_NOT_FOUND(2004, "id不能为空"),
    PROJECT_NUM_EXIST(2005, "项目编号已存在"),
    CALIBRATION_SUBMIT_ERROR(2006, "迁移自动化重复提交"),
    OEM_PARAM_NAME_EXIST(2007, "分组名已存在"),
    EP_NH_RESULT_FAILURE(2008, "率定失败，请重新测量"),
    OEM_MACHINE_FAILURE(2009, "已生成报告，无法删除"),

    BAD_REQUEST(5000, "请求错误"),
    UNAUTHORIZED(5001, "未认证"),
    FORBIDDEN(5002, "没有权限访问"),
    NOT_FOUND(5003, "请求资源不存在"),
    TOO_MANY_REQUESTS(5004, "请求次数超过限制"),
    THIRD_SERVER_ERROR(5006, "第三方服务器错误"),
    INVALID_REQUEST(5007, "不支持的请求方式"),
    USERNAME_PASSWORD_ERROR(5008, "用户名或密码错误"),
    OSS_UPLOAD_ERROR(5009, "OSS上传错误"),
    DATA_ABSORBANCE_EP_ERROR(5010, "数据有误，请重新测量"),
    PYTHON_ERROR(5011, "调用算法失败"),

    ;

    private Integer code;
    private String message;

    ApiCode(Integer code, String messasge) {
        this.code = code;
        this.message = messasge;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getSelfCode() {
        return code;
    }

    @Override
    public CodeConfigEnum getConfig() {
        return CodeConfigEnum.API;
    }

}

