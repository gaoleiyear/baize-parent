package com.baize.base.common.enums;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

public enum OrderDirection implements BaseEnumInterface {

    ASC(1, "升序", "asc"),
    DESC(2, "降序", "desc");

    private static Map<Integer, OrderDirection> VALUE_MAP = Maps.newHashMap();
    private static Map<String, OrderDirection> DIRECTION_MAP = Maps.newHashMap();

    static {
        for (OrderDirection element : OrderDirection.values()) {
            VALUE_MAP.put(element.getValue(), element);
            DIRECTION_MAP.put(element.getDirection(), element);
        }
        VALUE_MAP = Collections.unmodifiableMap(VALUE_MAP);
        DIRECTION_MAP = Collections.unmodifiableMap(DIRECTION_MAP);
    }

    private int value;
    private String name;
    private String direction;

    OrderDirection(int value, String name, String direction) {
        this.value = value;
        this.name = name;
        this.direction = direction;
    }

    public static OrderDirection get(Integer value) {
        return VALUE_MAP.get(value);
    }

    public static OrderDirection get(String direction) {
        return DIRECTION_MAP.get(direction);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getValue() {
        return value;
    }

    public String getDirection() {
        return direction;
    }
}
