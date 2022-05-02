package com.baize.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Api(tags = "白泽测试")
@Slf4j
public class TestController {

    @ApiOperation(value = "测试", notes="打印测试数据")
    @RequestMapping(value = "/baize",method = RequestMethod.GET)
    public String baize(){
        log.info("白泽测试");
        return "白泽测试";
    }
}
