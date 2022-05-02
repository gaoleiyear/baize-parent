package com.baize.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("BaizeController")
@Api(value = "白泽",tags = "白泽")
public class BaizeController {

    @ApiOperation(value = "2022-04-16")
    @RequestMapping(value = "/Test2",method = RequestMethod.GET)
    public String Test2(){

        return "白泽测试";
        log.info(" ");
    }
}
