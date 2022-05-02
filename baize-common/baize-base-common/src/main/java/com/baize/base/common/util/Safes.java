package com.baize.base.common.util;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by shen pan jie  2019/8/3
 */
@Slf4j
public class Safes {
    public Safes() {
    }

    public static <K, V> Map<K, V> of(Map<K, V> source) {
        return (Map) Optional.ofNullable(source).orElse(Maps.newHashMapWithExpectedSize(0));
    }

    public static <T> Collection<T> of(Collection<T> source) {
        return (Collection)Optional.ofNullable(source).orElse(Lists.newArrayListWithCapacity(0));
    }

    public static <T> Iterable<T> of(Iterable<T> source) {
        return (Iterable)Optional.ofNullable(source).orElse(Lists.newArrayListWithCapacity(0));
    }

    public static <T> List<T> of(List<T> source) {
        return (List)Optional.ofNullable(source).orElse(Lists.newArrayListWithCapacity(0));
    }

    public static <T> Set<T> of(Set<T> source) {
        return (Set)Optional.ofNullable(source).orElse(Sets.newHashSetWithExpectedSize(0));
    }

    public static BigDecimal of(BigDecimal source) {
        return (BigDecimal)Optional.ofNullable(source).orElse(BigDecimal.ZERO);
    }

    public static String of(String source) {
        return (String)Optional.ofNullable(source).orElse("");
    }

    public static String of(String source, String defaultStr) {
        return (String)Optional.ofNullable(source).orElse(defaultStr);
    }

    public static <T> T first(Collection<T> source) {
        if(CollectionUtils.isEmpty(source)) {
            return null;
        } else {
            T t = null;
            Iterator<T> iterator = source.iterator();
            if(iterator.hasNext()) {
                t = iterator.next();
            }

            return t;
        }
    }

    public static BigDecimal toBigDecimal(String source, BigDecimal defaultValue) {
        Preconditions.checkNotNull(defaultValue);

        try {
            return new BigDecimal(trimToEmpty(source));
        } catch (Throwable var3) {
            log.warn("未能识别的boolean类型, source:{}", source, var3);
            return defaultValue;
        }
    }

    public static int toInt(String source, int defaultValue) {
        if(!StringUtils.hasText(source)) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(trimToEmpty(source));
            } catch (Throwable var3) {
                log.warn("未能识别的整形 {}", source);
                return defaultValue;
            }
        }
    }

    public static long toLong(String source, long defaultValue) {
        if(!StringUtils.hasText(source)) {
            return defaultValue;
        } else {
            try {
                return Long.parseLong(trimToEmpty(source));
            } catch (Throwable var4) {
                log.warn("未能识别的长整形 {}", source);
                return defaultValue;
            }
        }
    }

    public static boolean toBoolean(String source, boolean defaultValue) {
        if(!StringUtils.hasText(source)) {
            return defaultValue;
        } else {
            try {
                return Boolean.parseBoolean(trimToEmpty(source));
            } catch (Throwable var3) {
                log.warn("未能识别的boolean类型, source:{}", source, var3);
                return defaultValue;
            }
        }
    }

    public static void run(Runnable runnable, Consumer<Throwable> error) {
        try {
            runnable.run();
        } catch (Throwable var3) {
            error.accept(var3);
        }

    }

    static String trimToEmpty(String str) {
        return str == null?"":str.trim();
    }

}
