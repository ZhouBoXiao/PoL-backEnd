pragma solidity ^0.4.2;

import "./ExCall.sol";
import "./SystemContracts/utillib/LibString.sol";
// import "./SystemContracts/sysbase/OwnerNamed.sol";


contract VerifyManager is ExCall{
    
    using LibString for *;

    //mapping(address => string) proofs;
    mapping(address => string) verifies;
    bool res;
    
    event LogProof(uint256 _blockNum, bytes32 _blockHash, string _uuid, address _userAddr, bytes _userSig,
    uint256[] _ringSig);
    
    // function VerifyManager() {
    //     register("VerifyModule","0.0.1.0","VerifyManager", "0.0.1.0");
    //     //nizkpp = LibNIZK.nizk_setup();
    //     //nizkpp = "yangzhou";
    // }
    
    function verifyZKP(string str) public  constant returns (string isValid){
        
        // verifies[msg.sender] = exCall("verify",str);     
        return exCall("verify",str);        
    }


    function verify(string str) public constant returns(string isValid){
        
        return exCall("verify", str);
    }
    function getResult() public constant returns(string) {
        return verifies[msg.sender];
    }


}