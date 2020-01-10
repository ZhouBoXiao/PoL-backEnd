package com.whu.web.controller;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.fastjson.JSON;
import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.whu.jni.NativeLib;
import com.whu.service.AdminService;
import com.whu.tools.ServletUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AdminController.class);

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

    @RequestMapping(value = "/deployContract" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ApiOperation(value = "部署智能合约", httpMethod = "POST")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "wallet", value = "钱包", dataType = ApiDataType.STRING,
//                    paramType = ApiParamType.FORM),
//            @ApiImplicitParam(name = "passWord", value = "密码", dataType = ApiDataType.STRING,
//                    paramType = ApiParamType.FORM)
//    })
//    public Map<String, Object> deployContract(@RequestParam(name = "wallet") String wallet,
//                                              @RequestParam(name = "passWord") String password){
    public Map<String, Object> deployContract(){
        boolean res = false;
        Map<String, Object> result = new HashMap<>();
        /*
        // 可在此设置管理员账户，当前为开发版，所以暂定用ju-ethereum/data/keys下的已有钱包文件
            adminWalletFile = wallet;
            adminPassWord = password;
         */
        res = adminService.deployContract();
        if(!res){
            result.put("result", false);
            result.put("message", "部署合约失败!");
        }else{
            result.put("result", true);
            result.put("message", res);
        }
        return result;
    }


    @RequestMapping(value = "/queryByTime" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ApiOperation(value = "queryByTime", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "查询", required = true ,dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM)
//            @ApiImplicitParam(name = "wallet", value = "钱包", required = false, dataType = ApiDataType.STRING,
//                    paramType = ApiParamType.FORM),
//            @ApiImplicitParam(name = "passWord", value = "密码", required = false, dataType = ApiDataType.STRING,
//                    paramType = ApiParamType.FORM)
    })
    public void queryByTime(HttpServletRequest request , HttpServletResponse response){
        Map<String, Object> result = new HashMap<>();
        System.out.println("getParameterNames: " +request.getParameterNames());
        String json = request.getParameter("param");
//        String passWord = "",wallet = "";
//        String startTime = request.getParameter("startTime");
        String res = adminService.queryByTime(json);
        if("".equals(res) || res==null){
            result.put("result", false);
            result.put("message", "查询失败!");
        }
        else {
            result.put("result", true);
            result.put("message", JSON.parseObject(res));
        }
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods","POST, GET, PATCH, DELETE, PUT");
        response.addHeader("Access-Control-Allow-Headers",request.getHeader("Access-Control-Request-Headers"));
        ServletUtil.createSuccessResponse(200, result, response);
    }

    @RequestMapping(value = "/queryBySpace" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ApiOperation(value = "空间查询", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "查询", dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM),
//            @ApiImplicitParam(name = "wallet", value = "钱包", required = true, dataType = ApiDataType.STRING,
//                    paramType = ApiParamType.FORM),
//            @ApiImplicitParam(name = "passWord", value = "密码", required = true, dataType = ApiDataType.STRING,
//                    paramType = ApiParamType.FORM)
    })
    public void queryBySpace(
            HttpServletRequest request , HttpServletResponse response){
//    public Map<String, Object> queryBySpace(@RequestParam(name = "json") String json,
//                                           @RequestParam(name = "wallet") String wallet,
//                                           @RequestParam(name = "passWord") String passWord){
        Map<String, Object> result = new HashMap<>();
        String passWord = "",wallet = "";
        String json = request.getParameter("json");
        String res = adminService.queryBySpace(json);
        if("".equals(res) || res==null){
            result.put("result", false);
            result.put("message", "查询失败!");
        }
        else {
            result.put("result", true);
            result.put("message", JSON.parseObject(res));
        }
        ServletUtil.createSuccessResponse(200, result, response);
    }

//    @RequestMapping(value = "/InitGridIndex" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
//    @ApiOperation(value = "初始化网格索引", httpMethod = "POST")
//
//    public void initGridIndex(HttpServletRequest request , HttpServletResponse response){
//
//        Map<String, Object> result = new HashMap<>();
//
//        boolean res = adminService.initGridIndex();
//        if(!res){
//            result.put("result", false);
//            result.put("message", "失败!");
//        }
//        else {
//            result.put("result", true);
//            result.put("message", "初始化成功!");
//        }
//        ServletUtil.createSuccessResponse(200, result, response);
//    }
//
    @RequestMapping(value = "/getPointsFromRegion" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ApiOperation(value = "根据给定区域查询", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "查询", dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM),
    })
    public void getPointsFromRegion(HttpServletRequest request , HttpServletResponse response){

        Map<String, Object> result = new HashMap<>();

        String json = request.getParameter("json");
        String res = adminService.getPointsFromRegion(json);

        if("".equals(res) || res==null){
            result.put("result", false);
            result.put("message", "查询失败!");
        }
        else {
            result.put("result", true);
            result.put("message", JSON.parseObject(res));
        }
        ServletUtil.createSuccessResponse(200, result, response);
    }

    @RequestMapping(value = "/getPointsFromRegionByGeoHash" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ApiOperation(value = "根据给定区域查询", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "查询", dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM),
    })
    public void getPointsFromRegionByGeoHash(HttpServletRequest request , HttpServletResponse response){

        Map<String, Object> result = new HashMap<>();

        String json = request.getParameter("json");
        String res = adminService.getPointsFromRegionByGeoHash(json);

        if("".equals(res) || res==null){
            result.put("result", false);
            result.put("message", "查询失败!");
        }
        else {
            result.put("result", true);
            result.put("message", JSON.parseObject(res));
        }
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods","POST, GET, PATCH, DELETE, PUT");
        response.addHeader("Access-Control-Allow-Headers",request.getHeader("Access-Control-Request-Headers"));
        ServletUtil.createSuccessResponse(200, result, response);
    }
}