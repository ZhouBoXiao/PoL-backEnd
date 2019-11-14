pragma solidity ^0.4.2;
import "./ExCall.sol";
import "./SystemContracts/utillib/LibString.sol";
contract ExAddDemo is ExCall 
{    
	using LibString for *;    
	string result;    
	function cfQuery(string str, string no) public constant returns(string) 
	{        
		result = exCall("cfQuery", str.concat(",", no));     
		return result;
		//A.concat(",", B)    
	}    
	function getResult() public constant returns(string) 
	{        
		return result;    
	}
}