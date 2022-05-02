package com.baize.base.common.enums;


import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

public enum EnvType implements BaseEnumInterface {

    LOCAL(1, "本地", "local"),
    DEV(2, "开发", "dev"),
    TEST(3, "测试", "test"),
    PROD(4, "生产", "prod");

    private static Map<Integer, EnvType> VALUE_MAP = Maps.newHashMap();

    static {
        for (EnvType element : EnvType.values()) {
            VALUE_MAP.put(element.getValue(), element);
        }
        VALUE_MAP = Collections.unmodifiableMap(VALUE_MAP);
    }

    private int value;
    private String name;
    private String active;

    EnvType(int value, String name, String active) {
        this.value = value;
        this.name = name;
        this.active = active;
    }

    public static EnvType get(Integer value) {
        return VALUE_MAP.get(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getValue() {
        return value;
    }

    public String getActive() {
        return active;
    }
}
