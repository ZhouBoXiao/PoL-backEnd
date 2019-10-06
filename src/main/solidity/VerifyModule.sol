/**
 * @file      VerifyModule.sol
 * @author    boxiao
 * @time      2019-09-21
 * @desc      给用户展示如何编写一个自己的模块。
 *            VerifyModule本身也是一个合约，它需要部署到链上；同时，它又负责管理用户的合约。只有添加到模块中的用户合约，用户才能在dapp中调用这些合约
 */
pragma solidity ^ 0.4 .2;

import "./SystemContracts/sysbase/OwnerNamed.sol";
import "./SystemContracts/sysbase/BaseModule.sol";

import "./SystemContracts/library/LibModule.sol";
import "./SystemContracts/library/LibContract.sol";

//juice提供的string库
import "./SystemContracts/utillib/LibString.sol";

//juice提供的log库
import "./SystemContracts/utillib/LibLog.sol";

contract VerifyModule is BaseModule {

    using LibModule
    for * ;
    using LibContract
    for * ;
    using LibString
    for * ;
    using LibInt
    for * ;
    using LibLog
    for * ;

    LibModule.Module tmpModule;
    LibContract.Contract tmpContract;

    //定义Demo模块中的错误信息
    enum MODULE_ERROR {
        NO_ERROR
    }

    //定义Demo模块中用的事件，可以用于返回错误信息，也可以返回其他信息
    event Notify(uint _code, string _info);

    // module : predefined data
    function VerifyModule() {
        uint ret = 0;

        //定义模块合约名称
        string memory moduleName = "VerifyModule";

        //定义模块合约名称
        string memory moduleDesc = "验证模块";

        //定义模块合约版本号
        string memory moduleVersion = "0.0.1.0";

        //指定模块合约ID
        string memory moduleId = moduleName.concat("_", moduleVersion);

        string memory moduleDescription = moduleName.concat(moduleVersion, "-Verify-DAPP");

        //把合约注册到JUICE链上
        LibLog.log("register VerifyModule");
        register(moduleName, moduleVersion);

        tmpModule.moduleId = moduleId;
        tmpModule.moduleName = moduleName;
        tmpModule.moduleVersion = moduleVersion;
        tmpModule.icon = "";
        tmpModule.moduleEnable = 0;
        tmpModule.moduleType = 2;
        tmpModule.moduleUrl = "http://202.114.114.46:30001/PoL/";
        tmpModule.moduleText = moduleDesc;
        tmpModule.moduleDescription = moduleDescription;

        uint nowTime = now * 1000;
        tmpModule.moduleCreateTime = nowTime;
        tmpModule.moduleUpdateTime = nowTime;

        tmpModule.moduleCreator = msg.sender;

        tmpModule.publishTime = nowTime;

        //把模块合约本身添加到系统的模块管理合约中。这一步是必须的，只有这样，用户的dapp才能调用添加到此模块合约的相关合约。
        LibLog.log("publish VerifyModule , exec insert ...");
        ret = addModule(tmpModule.toJson());
        moduleId = ret.recoveryToString();

        //添加用户合约到模块合约中
        LibLog.log("add VerifyManager to VerifyModule");
        ret = initContract(moduleName, moduleVersion, "VerifyManager", "验证合约", "0.0.1.0");
        if (ret != 0) {
            LibLog.log("add VerifyManager to VerifyModule failed");
            return;
        }


        //返回消息，以便控制台能看到是否部署成功
        Notify(0, "deploy VerifyModule success");
    }

    /**
     * 初始化用户自定义合约。
     * 如果用户有多个合约文件，则需要多次调用此方法。
     * @param moduleName        约合所属模块名
     * @param moduleVersion     约合所属模块版本
     * @param contractName      约合名
     * @param contractDesc      约合描述
     * @param contractVersion   约合版本
     * @return return 0 if success;
     */
    function initContract(string moduleName, string moduleVersion, string contractName, string contractDesc, string contractVersion) private returns(uint) {
        tmpContract.moduleName = moduleName;
        tmpContract.moduleVersion = moduleVersion;

        //合约名称
        tmpContract.cctName = contractName;
        //合约版本
        tmpContract.cctVersion = contractVersion;
        //合约描述
        tmpContract.description = contractDesc;


        //保持false
        tmpContract.deleted = false;
        //保持0
        tmpContract.enable = 0;

        uint nowTime = now * 1000;
        //合约创建时间
        tmpContract.createTime = nowTime;
        //合约修改时间
        tmpContract.updateTime = nowTime;

        //合约创建人
        tmpContract.creator = msg.sender;
        //预约块高
        tmpContract.blockNum = block.number;

        uint ret = addContract(tmpContract.toJson());
        return ret;
    }

}