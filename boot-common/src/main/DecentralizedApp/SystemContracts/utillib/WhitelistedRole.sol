pragma solidity ^0.4.2;

import "./Context.sol";
import "./Roles.sol";
import "./WhitelistAdminRole.sol";

/**
 * @title WhitelistedRole
 * @dev Whitelisted accounts have been approved by a WhitelistAdmin to perform certain actions (e.g. participate in a
 * crowdsale). This role is special in that the only accounts that can add it are WhitelistAdmins (who can also remove
 * it), and not Whitelisteds themselves.
 */
contract WhitelistedRole is Context, WhitelistAdminRole {
    using Roles for Roles.Role;

    event WhitelistedAdded(address indexed account);
    event WhitelistedRemoved(address indexed account);

    Roles.Role private _whitelisteds;
    
    function WhitelistedRole(){
        _addWhitelisted(_msgSender());
    }

    modifier onlyWhitelisted() {
        // require(isWhitelisted(_msgSender()), "WhitelistedRole: caller does not have the Whitelisted role");
        if(!isWhitelisted(_msgSender())){
            throw;
        }
        _;
    }

    function isWhitelisted(address account) public constant returns (bool) {
        return _whitelisteds.has(account);
    }

    function addWhitelisted(address account) public onlyWhitelistAdmin {
        if(!isWhitelistAdmin(_msgSender())){
            throw;
        }
        _addWhitelisted(account);
    }

    function removeWhitelisted(address account) public onlyWhitelistAdmin {
        if(!isWhitelistAdmin(_msgSender())){
            throw;
        }
        _removeWhitelisted(account);
    }

    function renounceWhitelisted() public {
        _removeWhitelisted(_msgSender());
        
        
    }

    function _addWhitelisted(address account) internal {
        _whitelisteds.add(account);
        WhitelistedAdded(account);
    }

    function _removeWhitelisted(address account) internal {
        _whitelisteds.remove(account);
        WhitelistedRemoved(account);
    }
}