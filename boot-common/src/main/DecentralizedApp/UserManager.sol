pragma solidity ^0.4.2;

import "./SystemContracts/utillib/LibString.sol";
// import "./SystemContracts/utillib/LibJson.sol";
import "./SystemContracts/utillib/LibStack.sol";
import "./SystemContracts/utillib/LibLog.sol";
// import "./SystemContracts/utillib/WhitelistedRole.sol";
import "./SystemContracts/utillib/IterableMapping.sol";

contract UserManager{
    using LibString
    for * ;

    using IterableMapping for *;
    using LibLog for *;

    IterableMapping.itmap data;

    // 构造函数
    function UserManager() {
        LibLog.log("deploy UserManager....");
    }

    // add or update User
    function insertUser(address key, address value) public returns(bool) {
        bool replaced = IterableMapping.insert(data, key, value);
        return replaced;
    }

    // remove User
    function removeUser(address key) public returns(bool) {
        bool flag = IterableMapping.remove(data, key);
        return flag;
    }

    // get user address list
    function getUserList () public constant returns(string _json) {
        string memory strAddr = "0x";
        uint len = 0;
        uint256 counter = 0;
        len = LibStack.push("{");
        len = LibStack.append("\"userList\":[");
        for(var i = IterableMapping.iterate_start(data);
            IterableMapping.iterate_valid(data, i);
            i = IterableMapping.iterate_next(data, i)) {
            var (key ,value) = IterableMapping.iterate_get(data, i);
            if (counter > 0) {
                len = LibStack.append(",");
            }
            len = LibStack.append(strAddr.concat(key.addrToAsciiString()));
            counter++;
        }
        len = LibStack.append("]");
        len = LibStack.append("}");
        _json = LibStack.popex(len);
    }

    //  get contract address list
    function getConAddressList() public constant returns(string _json) {
        string memory strAddr = "0x";
        uint len = 0;
        uint256 counter = 0;
        len = LibStack.push("{");
        len = LibStack.append("\"contractList\":[");
        for(var i = IterableMapping.iterate_start(data);
            IterableMapping.iterate_valid(data, i);
            i = IterableMapping.iterate_next(data, i)) {
            var (key ,value) = IterableMapping.iterate_get(data, i);
            if (counter > 0) {
                len = LibStack.append(",");
            }
            len = LibStack.append(strAddr.concat(value.addrToAsciiString()));
            counter++;
        }
        len = LibStack.append("]");
        len = LibStack.append("}");
        _json = LibStack.popex(len);
    }

}