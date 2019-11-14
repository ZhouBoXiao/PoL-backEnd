pragma solidity ^0.4.2;

library LibEcUtils {
    
    using LibEcUtils for *;
    
    function memcpy(uint dest, uint src, uint len) private {
        // Copy word-length chunks while possible
        for(; len >= 32; len -= 32) {
            assembly {
                mstore(dest, mload(src))
            }
            dest += 32;
            src += 32;
        }

        // Copy remaining bytes
        uint mask = 256 ** (32 - len) - 1;
        assembly {
            let srcpart := and(mload(src), not(mask))
            let destpart := and(mload(dest), mask)
            mstore(dest, or(destpart, srcpart))
        }
    }
    
    function toString(uint[32] _self) internal returns (string _str) {
        string memory str = new string(_self.length * 32);

        uint strptr;
        uint byteptr;
        assembly {
            strptr := add(str, 0x20)
            byteptr := add(_self, 0x00)
        }

        memcpy(strptr, byteptr, bytes(str).length);

        for (uint i=0; i<bytes(str).length; ++i) {
            if (bytes(str)[i] == 0)
                break;
        }

        _str = new string(i);

        uint _strptr;
        assembly {
            _strptr := add(_str, 0x20)
        }

        memcpy(_strptr, strptr, i);
    }

    function toString(uint[64] _self) internal returns (string _str) {
        string memory str = new string(_self.length * 32);

        uint strptr;
        uint byteptr;
        assembly {
            strptr := add(str, 0x20)
            byteptr := add(_self, 0x00)
        }

        memcpy(strptr, byteptr, bytes(str).length);

        for (uint i=0; i<bytes(str).length; ++i) {
            if (bytes(str)[i] == 0)
                break;
        }

        _str = new string(i);

        uint _strptr;
        assembly {
            _strptr := add(_str, 0x20)
        }

        memcpy(_strptr, strptr, i);
    }

    function toString(uint[128] _self) internal returns (string _str) {
        string memory str = new string(_self.length * 32);

        uint strptr;
        uint byteptr;
        assembly {
            strptr := add(str, 0x20)
            byteptr := add(_self, 0x00)
        }

        memcpy(strptr, byteptr, bytes(str).length);

        for (uint i=0; i<bytes(str).length; ++i) {
            if (bytes(str)[i] == 0)
                break;
        }

        _str = new string(i);

        uint _strptr;
        assembly {
            _strptr := add(_str, 0x20)
        }

        memcpy(_strptr, strptr, i);
    }

    function toString(uint[256] _self) internal returns (string _str) {
        string memory str = new string(_self.length * 32);

        uint strptr;
        uint byteptr;
        assembly {
            strptr := add(str, 0x20)
            byteptr := add(_self, 0x00)
        }

        memcpy(strptr, byteptr, bytes(str).length);

        for (uint i=0; i<bytes(str).length; ++i) {
            if (bytes(str)[i] == 0)
                break;
        }

        _str = new string(i);

        uint _strptr;
        assembly {
            _strptr := add(_str, 0x20)
        }

        memcpy(_strptr, strptr, i);
    }

    function toString(uint[512] _self) internal returns (string _str) {
        string memory str = new string(_self.length * 32);

        uint strptr;
        uint byteptr;
        assembly {
            strptr := add(str, 0x20)
            byteptr := add(_self, 0x00)
        }

        memcpy(strptr, byteptr, bytes(str).length);

        for (uint i=0; i<bytes(str).length; ++i) {
            if (bytes(str)[i] == 0)
                break;
        }

        _str = new string(i);

        uint _strptr;
        assembly {
            _strptr := add(_str, 0x20)
        }

        memcpy(_strptr, strptr, i);
    }

    function toString(uint[1024] _self) internal returns (string _str) {
        string memory str = new string(_self.length * 32);

        uint strptr;
        uint byteptr;
        assembly {
            strptr := add(str, 0x20)
            byteptr := add(_self, 0x00)
        }

        memcpy(strptr, byteptr, bytes(str).length);

        for (uint i=0; i<bytes(str).length; ++i) {
            if (bytes(str)[i] == 0)
                break;
        }

        _str = new string(i);

        uint _strptr;
        assembly {
            _strptr := add(_str, 0x20)
        }

        memcpy(_strptr, strptr, i);
    }
}