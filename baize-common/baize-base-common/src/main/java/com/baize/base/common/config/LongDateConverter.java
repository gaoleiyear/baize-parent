package com.baize.base.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @what:
 */
@Slf4j
public class LongDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        if(StringUtils.isBlank(s)){
            return null;
        }
        try {
            return new Date(Long.parseLong(s));
        } catch (Exception e) {
            return null;
        }
    }
}