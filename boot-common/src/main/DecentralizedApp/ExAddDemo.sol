pragma solidity ^0.4.2;

import "./ExCall.sol";
import "./SystemContracts/utillib/LibString.sol";

contract ExAddDemo is ExCall {
    using LibString for *;

    string A;
    string B;
    storage string C;

    function setA(string a) public constant returns(string){
        A = a;
        return A;
    }


    function setB(string b) public {
        B = b;
    }

    function add() public {
        C = exCall("add", A.concat(",", B));
    }

    function getC() public constant returns(string) {
        return C;
    }
    function getA() public constant returns(string) {
        return A;
    }
}
