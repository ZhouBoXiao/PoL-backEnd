pragma solidity ^0.4.2;

import "./ExCall.sol";
import "./SystemContracts/utillib/LibString.sol";
// import "./SystemContracts/sysbase/OwnerNamed.sol";


contract VerifyManager is ExCall{
    
    using LibString for *;
    // using LibJson for * ;
    mapping(address => string) proofs;
    mapping(address => string) verifies;
    bool res;
    
    event LogProof(uint256 _blockNum, bytes32 _blockHash, string _uuid, address _userAddr, bytes _userSig,
    uint256[] _ringSig);
    
    // function VerifyManager() {
    //     register("VerifyModule","0.0.1.0","VerifyManager", "0.0.1.0");
    //     //nizkpp = LibNIZK.nizk_setup();
    //     //nizkpp = "yangzhou";
    // }
    
    function verifyZKP(string str) public{
        
        verifies[msg.sender] = exCall("verify",str);
        
    }
    function genProof(string str) public{
        
        //x y z 是使用逗号连接的
        proofs[msg.sender] = exCall("genProof",str);
        
    }
    // function verify(string str) public {
        
    //     string memory res = exCall("verify", str);
        
    //     if(bytes(res).length != 0){
    //         verifies[msg.sender] = res;
    //     }else {
    //         throw;
    //     }
    // }
    function verify(string str) public constant returns(string isValid){
        
        return exCall("verify", str);
    }
    function getResult() public constant returns(string) {
        return verifies[msg.sender];
    }

    // function genProof(string str) public {
    //     string memory res = exCall("genProof", str);
    //     if(bytes(res).length != 0){
    //         proofs[msg.sender] = res;
    //     }else {
    //         throw;
    //     }
        
    // }
    
    function getProof() public constant returns(string) {
        
        return proofs[msg.sender];
    }
    
    // function fromJson(storage _self, string _json) internal returns(bool succ) {
    //     _self.reset();

    //     if (!_json.isJson())
    //         return false;

    //     _self.number = _json.jsonRead("number");
    //     _self.name = _json.jsonRead("name");
    //     _self.price = _json.jsonRead("price");
    //     _self.author = _json.jsonRead("author");
    //     _self.remark = _json.jsonRead("remark");

    //     return true;
    // }
    
    function logProof(uint256 _blockNum, bytes32 _blockHash, string _uid, address _userAddr, bytes _userSig, uint256[] _ringSig)
    public returns(bool success)
    {
        LogProof(_blockNum, _blockHash, _uid, _userAddr, _userSig, _ringSig);
    }
}