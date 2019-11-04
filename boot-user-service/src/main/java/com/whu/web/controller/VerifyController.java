package com.whu.web.controller;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.whu.jni.NativeLib;
import com.whu.service.VerifyService;
import com.whu.tools.ServletUtil;
import com.whu.tools.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
//import org.mybatis.spring.annotation.MapperScan;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/PoL")
//@MapperScan(basePackages = {"com.whu.mapper"})
@EnableDubbo  //启动Dubbo注解
@Api(tags = "1.1", description = "后台", value = "后台")
public class VerifyController {

    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private VerifyService verifyService;


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
    public Map<String, Object> verify(@RequestParam(name = "json") String json,
                                      @RequestParam(name = "wallet") String wallet,
                                      @RequestParam(name = "passWord") String passWord){
        String res = null;
        Map<String, Object> jo = new HashMap<>();
        logger.info("json: " + json );
//        String walletfile = "/home/lmars/PoL/PoL-Juice/ju-ethereum/data/keys/826b383b-96be-6651-dd23-619231977052.json";
//        String walletfile = request.getParameter("walletfile");
//        String passWord = request.getParameter("passWord");
//        String passWord = "12345678";

        JSONObject cert = JSON.parseObject(json);

        res = verifyService.verify(cert, wallet, passWord);

        if(res == null || "".equals(res)) {
            jo.put("result", false);
            jo.put("message", "证书验证失败!");
        }else{
            jo.put("message", JSON.parseObject(res));
            jo.put("result", true);
        }
        return jo;
    }
    /**
     *  @param request
     * @param response
     */
    @RequestMapping(value = "/generateWallet",method = RequestMethod.GET)
    public void generateWallet(HttpServletRequest request , HttpServletResponse response){
        JSONObject result=new JSONObject();
        String passWord = request.getParameter("passWord");
        if(StringUtil.isNull(passWord)){
            result.put("message", "不能为空!");
            result.put("result", false);
            ServletUtil.createSuccessResponse(200, result, response);
            return;
        }
        logger.info("passWord:  "+passWord);
        String fileName = verifyService.create(passWord);
        if(!StringUtil.isNull(fileName)) {
            result.put("message", fileName);
            result.put("result", true);
        }else{
            result.put("result", false);
            result.put("message", "钱包创建失败!");
        }
        ServletUtil.createSuccessResponse(200, result, response);
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

        res = verifyService.genProof(position);
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
    @ApiOperation(value = "查询证书", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "查询", dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM)
    })
    public Map<String, Object> search(@RequestParam(name = "json") String json){
        Map<String, Object> result = new HashMap<>();

        String res = verifyService.search(json);
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
            @ApiImplicitParam(name = "passWord", value = "密码", required = true, dataType = ApiDataType.STRING,
                    paramType = ApiParamType.FORM)
    })
    public Map<String, Object> deployContract(@RequestParam(name = "wallet") String wallet,
                                              @RequestParam(name = "passWord") String passWord){
        String res = null;
        Map<String, Object> result = new HashMap<>();

        res = verifyService.deployContract(wallet, passWord);
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