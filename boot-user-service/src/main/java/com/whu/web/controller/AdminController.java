package com.whu.web.controller;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.whu.jni.NativeLib;
import com.whu.service.AdminService;
import com.whu.tools.ServletUtil;
import com.whu.tools.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
//import org.mybatis.spring.annotation.MapperScan;


@RestController
@RequestMapping("/PoL/admin")
//@MapperScan(basePackages = {"com.whu.mapper"})
@EnableDubbo  //启动Dubbo注解
@Api(tags = "1.2", description = "后台", value = "后台")
public class AdminController {

    @Autowired
    private AdminService adminService;
    private Logger logger = Logger.getLogger(this.getClass());



    @RequestMapping("/uuid")
    @ResponseBody
    public Map<String, Object> greeting() {
        Map<String, Object> result = new HashMap<>();
        String uuid = NativeLib.generateUUID();
        result.put("generateUUID",uuid);
        return result;
    }

    @RequestMapping("/createAccount")
    @ResponseBody
    public Map<String, Object> createAccount(String passWord) {
        Map<String, Object> result = new HashMap<>();

        String fileName = null;
        fileName = adminService.create(passWord);
        result.put("fileName",fileName);
        return result;
    }

}