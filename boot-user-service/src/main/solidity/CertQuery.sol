pragma solidity ^0.4.2;

import "./SystemContracts/utillib/LibString.sol";
import "./SystemContracts/utillib/LibJson.sol";
import "./SystemContracts/utillib/LibStack.sol";
import "./SystemContracts/utillib/LibLog.sol";


contract CertQuery  {
    using LibString
    for * ;
    using LibJson for * ;
    // string result;
    // string proof;
    // mapping(address => string) proofs;
    // mapping(address => string) verifies;

    string[] certificates;
    string[] issuanceDates;

    // 构造函数，在合约发布时会被触发调用
    function CertQuery() {
        LibLog.log("deploy CertQuery....");

    }

    function sumOfCerts() public constant returns(uint){
        return certificates.length;
    }

    function addCertificate(string json, string issuanceDate) public {
        LibLog.log("Certificate into add..");
        certificates.push(json);
        issuanceDates.push(issuanceDate);
        LibLog.log("add a certificate success", "CertQuery");

    }

    function listAll() constant public returns(string _json) {
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
    function test(string startTime) public constant returns(int _json){
        return issuanceDates[0].compare(startTime);
    }

    function search(string startTime, string endTime) public constant returns(string _json){
        uint len = 0;
        uint counter = 0;
        len = LibStack.push("");
        for (uint i = 0 ; i < certificates.length ; ++i) {

            if( (issuanceDates[i].compare(startTime) == 0 ||
                issuanceDates[i].compare(startTime) == 1) &&
                (issuanceDates[i].compare(endTime) == 0 ||
                issuanceDates[i].compare(endTime) == -1) ) {
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