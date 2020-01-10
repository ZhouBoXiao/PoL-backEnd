package com.whu.web.controller;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.whu.jni.NativeLib;
import com.whu.service.UserService;
import com.whu.tools.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//import org.mybatis.spring.annotation.MapperScan;


@RestController
@RequestMapping("/PoL")
//@MapperScan(basePackages = {"com.whu.mapper"})
@EnableDubbo  //启动Dubbo注解
@Api(tags = "1.1", description = "后台", value = "后台")
public class UserController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @RequestMapping("/uuid")
    @ResponseBody
    public Map<String, Object> greeting() {
        Map<String, Object> result = new HashMap<>();
        String uuid = NativeLib.generateUUID();
        result.put("generateUUID",uuid);
        return result;
    }

    @RequestMapping(value = "/verify",method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "验证证书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "证书", required = true, dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM),
            @ApiImplicitParam(name = "wallet", value = "钱包", required = true, dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM),
            @ApiImplicitParam(name = "passWord", value = "密码", required = true, dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM)
    })
    public Map<String, Object> verify(@RequestParam(name = "json") String json){
        String res = null;
        Map<String, Object> jo = new HashMap<>();
        logger.info("json: " + json );

        JSONObject cert = JSON.parseObject(json);

        res = userService.verify(cert);

        if(res == null || "".equals(res)) {
            jo.put("result", false);
            jo.put("message", "证书验证失败!");
        }else{
            jo.put("message", JSON.parseObject(res));
            jo.put("result", true);
        }
        return jo;
    }


    @RequestMapping(value = "/genProof" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Map<String, Object> genProof(String position){

        String res = null;
        Map<String, Object> result = new HashMap<>();
        logger.info("position:  "+position);
        if(StringUtil.isNull(position)){
            result.put("message", "不能为空!");
            result.put("result", false);
            return result;
        }

        res = userService.genProof(position);
        if(res == null || "".equals(res)) {
            result.put("result", false);
            result.put("message", "证明生成失败!");
        }
        else {
            result.put("result", true);
            result.put("message", JSON.parseObject(res));
        }
        return result;
    }

    @RequestMapping(value = "/search" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ApiOperation(value = "查询某个用户的证书", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "查询", dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM)

    })
    public Map<String, Object> search(@RequestParam(name = "json") String json){
        Map<String, Object> result = new HashMap<>();

        String res = userService.search(json);
        if("".equals(res) || res==null){
            result.put("result", false);
            result.put("message", "查询失败!");
        }
        else {
            result.put("result", true);
            result.put("message", JSON.parseObject(res));
        }
        return result;
    }

    @RequestMapping(value = "/deployContract" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ApiOperation(value = "部署智能合约", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wallet", value = "钱包", required = true, dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM)
    })
    public Map<String, Object> deployContract(@RequestParam(name = "wallet") String wallet,
                                              @RequestParam(name = "username") String username){
        String res = null;
        Map<String, Object> result = new HashMap<>();

        res = userService.deployContract(wallet, username);
        if("".equals(res) || res==null){
            result.put("result", false);
            result.put("message", "部署合约失败!");
        }else{
            result.put("result", true);
            result.put("message", res);
        }
        return result;
    }


}