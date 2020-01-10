package com.whu.web.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Api(value = "admin", description = "the admin API")
public interface AdminApi {
    @RequestMapping(value = "/deployContract",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ApiOperation(value = "部署管理智能合约", notes="部署管理智能合约",  httpMethod = "POST", produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })
    Map<String, Object> deployContract();

    @RequestMapping(value = "/queryByTime",method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "queryByTime" , notes="时间查询", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "查询", required = true, paramType="form" , dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })
    Map<String, Object> queryByTime(String param);

    @RequestMapping(value = "/queryBySpace" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ApiOperation(value = "空间查询", notes="空间查询", httpMethod = "POST", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "查询", required = true, paramType="query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })
    void queryBySpace(
            HttpServletRequest request , HttpServletResponse response);
//    @RequestMapping(value = "/deployContract" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
//    @ResponseBody
//    @ApiOperation(value = "部署智能合约", notes="部署智能合约", httpMethod = "POST", produces="application/json")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "successful operation") })
//
//    Map<String, Object> deployContract(
//            @NotNull @ApiParam(value = "钱包" ,required=true ) @Valid @RequestParam("wallet") String wallet,
//            @NotNull @ApiParam(value = "用户名" ,required=true ) @Valid @RequestParam("username") String username);
}
