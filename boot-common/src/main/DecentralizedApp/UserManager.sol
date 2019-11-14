pragma solidity ^ 0.4 .2;

//juice的管理库，必须引入
import "./SystemContracts/sysbase/OwnerNamed.sol";

contract UserManager is OwnerNamed, WhitelistedRole {
    //余额（状态变量）
    // uint balance;
    using LibRegisterUser for *;
    mapping(address => RegisterUser) users;
     
    
    // 构造函数
    function UserManager() {
        //把合约注册到JUICE链上, 参数必须和DemoModule.sol中的保持一致；register注册仓库合约参数只支持字符串形式
        register("UserModule", "0.0.1.0", "UserContract", "0.0.1.0");
    }
    //addUser
    
    //deleteUser
    
    //updateUser
    
    
    //增加用户的余额
    // function update(uint amount) public returns(address, uint) {
    //     balance += amount;
    //     return (msg.sender, balance);
    // }

    //查询用户的余额。
    // function get() public constant returns(uint) {
    //     return balance;
    // }
}