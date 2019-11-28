pragma solidity ^0.4.2;

import "./SystemContracts/utillib/LibString.sol";
import "./SystemContracts/utillib/LibJson.sol";
import "./SystemContracts/utillib/LibStack.sol";
import "./SystemContracts/utillib/LibLog.sol";
import "./SystemContracts/utillib/WhitelistedRole.sol";
import "./SystemContracts/math/Math.sol";


contract CertQuery is WhitelistedRole {
    using LibString
    for * ;
    using LibJson 
    for * ;

    mapping(string => string[]) locCode_certs;
    string[] certificates;
    string[] issuanceDates;
    //address owner;
    
    //function owned() private { owner = msg.sender; }
    
    // modifier onlyOwner{
    //     if(_msgSender() != owner) throw;
    //     _;
    // }
    
    // 构造函数，在合约发布时会被触发调用
    function CertQuery() {
        LibLog.log("deploy CertQuery....");
    }
    
    function sumOfCerts() public constant returns(uint){
        return certificates.length;
    }
    
    function addCertificate(string json, string issuanceDate, string locCode) onlyWhitelisted public {
        LibLog.log("Certificate into add..");
        locCode_certs[locCode].push(json);
        certificates.push(json);
        issuanceDates.push(issuanceDate);
        LibLog.log("add a certificate success", "CertQuery");
    }
    
    function listAll() onlyWhitelisted constant public returns(string _json) {
        uint len = 0;
        uint counter = 0;   
        len = LibStack.push("");
        for (uint i = 0; i < certificates.length; i++) {
            if (counter > 0) {
                len = LibStack.append(",");
            }
            len = LibStack.append(certificates[i]);
            counter++;
        }
        len = itemsStackPush(LibStack.popex(len), counter);
        _json = LibStack.popex(len);
    }

    function searchByLocCode(string locCode)  public constant returns(string _json){  //onlyWhitelisted
        string[] certs = locCode_certs[locCode];
        uint256 len = 0;
        uint256 num = certs.length;
        uint256 counter = 0;
        len = LibStack.push("");
        for(uint i = 0; i < num; i++)
        {
            if (counter > 0) {
                len = LibStack.append(",");
            }
            len = LibStack.append(certs[i]);
            counter++;
        }

        len = itemsStackPush(LibStack.popex(len), counter);
        _json = LibStack.popex(len);
        return _json;
    }

    function itemsStackPush(string _items, uint _total) constant private returns(uint len) {
        len = 0;
        len = LibStack.push("{");
        //len = LibStack.appendKeyValue("result", "true");
        len = LibStack.appendKeyValue("total", _total);
        len = LibStack.append(",\"data\":[");
        len = LibStack.append(_items);
        len = LibStack.append("]");
        len = LibStack.append("}");
        return len;
    }
    
    /*function test(string startTime) public constant returns(int _json){
        return issuanceDates[0].compare(startTime);
        onlyWhitelisted
    }*/
    
    function search(string startTime, string endTime) onlyWhitelisted public constant returns(string _json){
        uint256 len = 0;
        uint256 counter = 0;
        len = LibStack.push("");
        uint256 low = 0;
        uint256 high = certificates.length;
        
        // startTime <  issuanceDates[0]  && endTime > issuanceDates[-1]
        if (issuanceDates[0].compare(startTime) >= 0 && issuanceDates[issuanceDates.length-1].compare(endTime) <= 0){   
            _json = listAll();
            return _json;
        } else{ 
     
            while (low < high) {
                uint256 mid = Math.average(low, high);
                if( issuanceDates[mid].compare(startTime) >= 0  && 
                    issuanceDates[mid].compare(endTime) <= 0 ){
                    break;   
                }
                if (issuanceDates[mid].compare(startTime) < 0){
                    low = mid + 1;
                } else if(issuanceDates[mid].compare(endTime) > 0){
                    high = mid;
                }
                
            }
            
            for (uint i = low ; i < high ; ++i) {
                
                if( issuanceDates[i].compare(startTime) >= 0  && 
                    issuanceDates[i].compare(endTime) <= 0 ) {
                        if (counter > 0) {
                            len = LibStack.append(",");
                        }
                        len = LibStack.append(certificates[i]);
                        counter++;
                        
                }
            }
            
            len = itemsStackPush(LibStack.popex(len), counter);
            _json = LibStack.popex(len);
        }
    }
   
}