package com.baize.base.common.model;

import com.baize.base.common.enums.OrderDirection;
import com.baize.base.common.util.Pair;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class Order implements Serializable {
    private List<Pair<String, OrderDirection>> orders;

    private Order(List<Pair<String, OrderDirection>> orders) {
        this.orders = orders;
    }

    public static Order create(List<Pair<String, OrderDirection>> orders) {
        return new Order(orders);
    }

    public static Order create(Pair<String, OrderDirection>... orders) {
        return new Order(Lists.newArrayList(orders));
    }

    /**
     * ex: "id asc,code desc" or "code desc"
     *
     * @param orderSegment
     * @return
     */
    public static Order create(String orderSegment) {
        if (orderSegment == null || orderSegment.trim().equals("")) {
            return new Order(null);
        }
        List<Pair<String, OrderDirection>> results = Lists.newArrayList();
        String[] orderSegments = orderSegment.trim().split(",");
        for (int i = 0; i < orderSegments.length; i++) {
            String element = orderSegments[i];
            if (element == null || element.trim().equals("") ||
                    element.startsWith("null.") || element.startsWith(".")) {
                continue;
            }

            String[] array = element.trim().split(" ");
            if (array.length != 1 && array.length != 2) {
                throw new IllegalArgumentException("element pattern must be {property} {direction}, input is: " + element);
            }

            Pair<String, OrderDirection> pair = Pair.of(array[0], array.length == 2 ? OrderDirection.get(array[1]) : OrderDirection.ASC);
            results.add(pair);
        }
        return new Order(results);
    }

    public static boolean isEmpty(Order order) {
        return order == null || order.orders == null || order.orders.isEmpty();
    }

    public String toMybatis() {
        return orders.stream().map(p -> p.getKey() + "." + p.getValue()).collect(Collectors.joining(","));
    }

    public static void main(String[] args) {
        log.info("Order:{}", Order.create("id asc,code desc"));
    }
}

