pragma solidity ^0.4.2;

/**
 * @title Roles
 * @dev Library for managing addresses assigned to a Role.
 */
library Roles {
    struct Role {
        mapping (address => bool) bearer;
    }

    /**
     * @dev Give an account access to this role.
     */
    function add(Role storage role, address account) internal {
        // require(!has(role, account), "Roles: account already has role");
        if (has(role, account)) {
            throw;
        }
        role.bearer[account] = true;
    }

    /**
     * @dev Remove an account's access to this role.
     */
    function remove(Role storage role, address account) internal {
        // require(has(role, account), "Roles: account does not have role");
        if (!has(role, account)) {
            throw;
        }
        role.bearer[account] = false;
    }

    /**
     * @dev Check if an account has this role.
     * @return bool
     */
    function has(Role storage role, address account) internal constant returns (bool) {
        // require(account != address(0), "Roles: account is the zero address");
        if (account == address(0)) {
            throw;
        }
        return role.bearer[account];
    }
}