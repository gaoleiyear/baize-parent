package com.baize.base.common.filter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baize.base.common.model.ContentType;
import com.baize.base.common.util.DateUtil;
import com.baize.base.common.util.IpSolver;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lqw
 * @date 2020/09/21 10:25
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "logFilter", urlPatterns = "/*")
public class LoggerFilter implements Filter {

    private static final String REQUEST_ID = "request-id";
    private static final String REQUEST_TIME = "request-time";
    private static final String OPERATION_COST_TIME = "cost-time";
    private static final String URL = "url";
    private static final String CONTENT_TYPE = "content-type";
    private static final String RESPONSE_URL = "request-url";
    private static final String METHOD = "method";
    private static final String HEAD = "head";
    private static final String COOKIES = "cookies";
    private static final String PARAMS = "params";
    private static final String BODY = "body";
    private static final String STATUS = "status";
    private static final List<String> PASSWORD_KEYS = Lists.newArrayList("password");

    /**
     * entity最大打印长度
     */
    private static final int BODY_PRINT_LENGTH = 500;

    private SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.QuoteFieldNames};

    private static final List<String> HEAD_NOT_INCLUDE = Arrays
            .asList("Accept", "Accept-Encoding", "Accept-Charset", "Accept-Language", "Connection", "Content-Encoding",
                    "Content-Type", "Vary", "Cache-Control", "Cookie", "Host", "accept", "accept-encoding",
                    "accept-charset", "accept-language", "connection", "content-encoding", "content-type", "vary",
                    "cache-control", "cookie", "host");

    private final AtomicLong id = new AtomicLong(0);

    /**
     * request log
     *
     * @param request
     * @return
     */
    private Map<String, Object> requestLogger(RequestWrapper request) {
        //请求参数
        Map<String, Object> param = Maps.newHashMap();
        //打印参数，有序
        Map<String, Object> map = Maps.newLinkedHashMap();

        Long requestId = id.getAndIncrement();
        param.put(REQUEST_ID, requestId);
        map.put(REQUEST_ID, requestId);

        param.put(METHOD, request.getMethod());
        map.put(METHOD, request.getMethod());

        map.put(CONTENT_TYPE, request.getContentType());

        param.put(REQUEST_TIME, System.currentTimeMillis());
        map.put(REQUEST_TIME, DateUtil.currDateTime());

        StringBuffer url = request.getRequestURL();
        param.put(URL, url);
        String queryString = request.getQueryString();
        if (StringUtils.isNotBlank(queryString)) {
            url.append("?").append(request.getQueryString());
        }
        map.put(URL, url);

        Enumeration<String> e = request.getHeaderNames();
        Map<String, Object> headers = Maps.newHashMap();
        while (e.hasMoreElements()) {
            String headName = e.nextElement();
            if (!HEAD_NOT_INCLUDE.contains(headName)) {
                headers.put(headName, request.getHeader(headName));
            }
        }
        map.put(HEAD, headers);

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            Map<String, Object> m = Maps.newHashMap();
            for (Cookie c : cookies) {
                m.put(c.getName(), c.getValue());
            }
            map.put(COOKIES, m);
        }

        String body = "";
        RequestWrapper wrapper = WebUtils.getNativeRequest(request, RequestWrapper.class);
        if (wrapper != null) {
            // params
            Map<String, Object> paramEntity = Maps.newHashMap();
            Enumeration<String> enumeration = wrapper.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String paramName = enumeration.nextElement();
                paramEntity.put(paramName, wrapper.getParameter(paramName));
            }
            map.put(PARAMS, paramEntity);

            // body
            // 文件不打印内容
            if (!Optional.of(request).map(RequestWrapper::getContentType).orElse("").contains(ContentType.MULTIPART)) {
                body = getRequestBody(wrapper);
            }
            //替换密码为******   PASSWORD_KEYS为预设定的密码字段keys
            body = PASSWORD_KEYS.stream().reduce(body, (result, next) -> result.replaceAll("(\"" + next + "\":\")(.*?)(\")", "$1******$3"));
            try {
                Object jsonObject = JSONObject.parse(body);
                map.put(BODY, jsonObject);
            } catch (Exception ex) {
                log.error("json parse error. msg={}", ex.getMessage());
            }

            //map.put(BODY, body);
        }

        log.info(JSON.toJSONString(map, features));

        return param;
    }

    /**
     * 获取body
     *
     * @param wrapper
     */
    private String getRequestBody(RequestWrapper wrapper) {
        try {
            StringBuilder body = new StringBuilder();
            BufferedReader reader = wrapper.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line.trim());
            }
            reader.close();
            return body.toString();
        } catch (IOException e) {
            log.error("body reader error");
        }

        //byte[] buf = wrapper.getContentAsByteArray();
        //if (buf.length > 0) {
        //    String payload;
        //    try {
        //        payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
        //    } catch (UnsupportedEncodingException e) {
        //        payload = "[unknown]";
        //    }
        //    return payload.replaceAll("\\n", "").replaceAll(" ", "");
        //}
        return "";
    }

    /**
     * response log
     *
     * @param param
     * @param response
     */
    private void responseLogger(Map<String, Object> param, HttpServletResponse response) {
        try {
            ContentCachingResponseWrapper wrapper = WebUtils
                    .getNativeResponse(response, ContentCachingResponseWrapper.class);
            //打印参数，有序
            Map<String, Object> map = Maps.newLinkedHashMap();

            Long requestId = (Long) param.get(REQUEST_ID);
            map.put(REQUEST_ID, requestId);

            map.put(STATUS, wrapper.getStatusCode());

            map.put(CONTENT_TYPE, response.getContentType());

            StringBuffer url = (StringBuffer) param.get(URL);
            map.put(RESPONSE_URL, url);

            Long requestTime = (Long) param.get(REQUEST_TIME);
            if (requestTime == null) {
                requestTime = 0L;
            }
            long costTime = System.currentTimeMillis() - requestTime;
            map.put(OPERATION_COST_TIME, costTime + "ms");
            byte[] body = wrapper.getContentAsByteArray();
            String entity = "";
            if (body != null) {
                //只打印json日志
                if (response.getContentType() != null && response.getContentType().contains(ContentType.JSON)) {
                    if (body.length<200){
                        entity = new String(body);
                        Object object = JSON.parse(entity);
                        map.put(BODY, object);
                    }
                } else {
                    entity = response.getContentType();
                    map.put(BODY, entity);
                }
            }
            log.info(JSON.toJSONString(map, features));
        } catch (Exception e) {
            log.error("responseLogger error, msg={}", e.getMessage());
        }
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper = WebUtils
                .getNativeResponse(response, ContentCachingResponseWrapper.class);
        responseWrapper.copyBodyToResponse();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 转为wrapper，使流信息可重复读
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
                (HttpServletResponse) response);

        //过滤swagger
        String uri = requestWrapper.getRequestURI();
        String referer = requestWrapper.getHeader("referer");
        String ip = IpSolver.getIpAddress(requestWrapper);
        String str = uri + referer;
        boolean isSwagger = str.contains("swagger") || str.contains("api-docs");
        if (isSwagger) {
            log.info("过滤swagger. uri={}, referer={}, ip={}", uri, referer, ip);
            chain.doFilter(requestWrapper, response);
            return;
        }

        //处理request
        Map<String, Object> reqParam = requestLogger(requestWrapper);
        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            //处理response
            responseLogger(reqParam, responseWrapper);
            updateResponse(responseWrapper);
        }
    }

}
