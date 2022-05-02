package com.baize.base.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baize.base.common.util.Safes;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Data
public class Page<T> implements Serializable {
    private static final long serialVersionUID = -5215301829268317718L;
    protected int num;
    protected int size;
    protected int count;
    protected Map<String, Integer> orders;
    protected List<T> list;

    public Page() {
        this.num = 1;
        this.size = 20;
        this.count = 0;
        this.list = Lists.newArrayList();
    }

    public Page(int num) {
        this(num, 0, Lists.newArrayList());
    }

    public Page(int num, int size) {
        this(num, size, Lists.newArrayList());
    }

    public Page(int num, int size, List<T> list) {
        this(num, size, null, list);
    }

    public Page(int num, int size, Map<String, Integer> orders, List<T> list) {
        this.num = 1;
        this.size = 20;
        this.count = 0;
        this.list = Lists.newArrayList();
        if (num > 0) {
            this.num = num;
        }

        if (size > 0) {
            this.size = size;
        }

        if (orders != null) {
            this.orders = orders;
        }

        if (list != null) {
            this.list = list;
        }

    }

    public Page(Page<T> page) {
        this(page.getNum(), page.getSize(), page.getOrders(), page.list);
    }

    public static <T, K> Page<K> reNew(Page<T> oldPage, List<K> newList) {
        Page newPage = new Page<>(oldPage.getNum(), oldPage.getSize(), oldPage.getOrders(), newList);
        newPage.setCount(oldPage.getCount());
        return newPage;
    }

    public static <T, R> Page<R> reNew(Page<T> oldPage, Class<R> clazz) {
        List<R> newList = new ArrayList<>();
        if (oldPage.getList()!=null && oldPage.getList().size()>0){
            newList = Safes.of(oldPage.getList()).stream().map(
                    x -> {
                        try {
                            R obj = clazz.newInstance();
                            BeanUtils.copyProperties(x, obj);
                            return obj;
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("对象转换异常 page={}, class={}", oldPage, clazz);
                            throw new RuntimeException("对象转换异常", e);
                        }
                    }
            ).collect(Collectors.toList());
        }
        Page<R> newPage = new Page<>(oldPage.getNum(), oldPage.getSize(), oldPage.getOrders(), newList);
        newPage.setCount(oldPage.getCount());
        newPage.setList(newList);
        return newPage;
    }

    public int getStart() {
        return (this.num - 1) * this.size;
    }

    @JSONField(
            serialize = false
    )
    public int getEnd() {
        return this.num * this.size - 1;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        if (num > 0) {
            this.num = num;
        }

    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Map<String, Integer> getOrders() {
        return this.orders;
    }

    public void setOrders(Map<String, Integer> orders) {
        this.orders = orders;
    }

    public int getPageCount() {
        return (count + size - 1) / size;
    }
}
