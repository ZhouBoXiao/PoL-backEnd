package com.whu.contract;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class CertQuery extends Contract {
    private static final String BINARY = "606060405261000f61001a5b3390565b61003d6101a661000b565b6101c06000825b6103e282826000600160a060020a038216151561048e57610002565b60408051808201909152601481527f6465706c6f79204365727451756572792e2e2e2e00000000000000000000000060208201526101b19060408051602081810183526000918290528251606081018452602b81527f5b36396439386436613034633431623436303561616362376264326637346265818301527f655d5b3035766d6c6f675d000000000000000000000000000000000000000000818501528351808501909452600184527f7c0000000000000000000000000000000000000000000000000000000000000091840191909152909182908190819061022e9085905b6020604051908101604052806000815260200150600060006000845186510160405180591061014b5750595b908082528060200260200182016040528015610162575b5093508350602086019250602085019150602084019050610413818488515b60005b602082106104ae578251845260209384019390920191601f1990910190610184565b6101f7600182610021565b506119e1806104cd6000396000f35b604051600160a060020a038216907f22380c05984257a1cb900161c713dd71d39e74820f1aea43bd3f1bdd2096129990600090a250565b604051600160a060020a038216907fee1504a83b6d4a361f4c1dc78ab59bfa30d6a3b6612c403e86bb01ef2984295f90600090a250565b93506103806103c2600160a060020a033016602060405190810160405280600081526020015060006000602a6040518059106102675750595b90808252806020026020018201604052801561027e575b50925082507f3000000000000000000000000000000000000000000000000000000000000000836000815181101561000257906020010190600160f860020a031916908160001a9053507f7800000000000000000000000000000000000000000000000000000000000000836001815181101561000257906020010190600160f860020a031916908160001a905350602991505b600260ff83161061042b57506010830492600f16600a81101561043257806030017f010000000000000000000000000000000000000000000000000000000000000002838360ff16815181101561000257906020010190600160f860020a031916908160001a905350610482565b60408051808201909152600181527f7c0000000000000000000000000000000000000000000000000000000000000060208201529094506103c990859061011f565b859061011f565b93506103d5848761011f565b5060009695505050505050565b156103ec57610002565b600160a060020a0381166000908152602083905260409020805460ff191660011790555050565b61042286518201838751610181565b50505092915050565b5050919050565b600a81036061017f010000000000000000000000000000000000000000000000000000000000000002838360ff16815181101561000257906020010190600160f860020a031916908160001a9053505b60001990910190610312565b50600160a060020a03166000908152602091909152604090205460ff1690565b50905182516020929092036101000a6000190180199091169116179052566060604052361561008d5760e060020a600035046310154bad8114610092578063291d9549146100aa5780632c3560ee146100c05780633af32abf1461016d5780634c5a628c146101865780637362d9c81461019957806378a9eeed146101af578063bb5f747b146101d5578063d6cd9473146101ed578063e24f2ce714610200578063e8c2807514610298575b610002565b34610002576102a760043561033d6101e15b336102a4565b34610002576102a76004356103726101e16100a4565b34610002576102a96004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f81018390048302840183019094528383529799986044989297509190910194509092508291508401838280828437509496505050505050506040805160208101909152600080825280808080806103926101796100a4565b34610002576103176004355b6000610c9d600183610358565b34610002576102a7610ca3610ca56100a4565b34610002576102a7600435610cb06101e16100a4565b34610002576102a95b604080516020810190915260008082528080610cc96101796100a4565b34610002576103176004355b6000610c9d8183610358565b34610002576102a7610ca3610dda6100a4565b34610002576102a76004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f8101839004830284018301909452838352979998604498929750919091019450909250829150840183828082843750949650505050505050610ddf6101796100a4565b346100025761032b6002545b90565b005b60405180806020018281038252838181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156103095780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b604080519115158252519081900360200190f35b60408051918252519081900360200190f35b1561034757610002565b61036f816111f16001825b61174082825b6000600160a060020a0382161515610c7f57610002565b50565b1561037c57610002565b61036f815b6112286001825b6117718282610358565b1561039c57610002565b6040805160208101909152600080825296508695506104a2905b60408051602081810183526000918290528251606081018452602f81526000805160206119c1833981519152918101919091527f655d5b3039537461636b507573685d0000000000000000000000000000000000928101929092529081808061125f84875b602060405190810160405280600081526020015060006000600084518651016040518059106104475750595b90808252806020026020018201604052801561045e575b509350835060208601925060208501915060208401905061179e818488515b60005b6020821061198f578251845260209384019390920191601f1990910190610480565b60025460038054929850600096509094508591610527918c9184908110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156105dc5780601f106105b1576101008083540402835291602001916105dc565b60000b12158015610667575060038054600091610661918b91906000198101908110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156105dc5780601f106105b1576101008083540402835291602001916105dc565b820191906000526020600020905b8154815290600101906020018083116105bf57829003601f168201915b50505050506105e9909190565b6000805b8351811080156105fd5750825181105b1561127157828181518110156100025790602001015160f860020a900460f860020a02600160f860020a031916848281518110156100025790602001015160f860020a900460f860020a02600160f860020a03191611156112845760019150611305565b60000b13155b1561092a576106746101b8565b965061067e565b96505b50505050505092915050565b60000b13155b15610817575b50825b82811015610954576000610a538a60036000508481548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156105dc5780601f106105b1576101008083540402835291602001916105dc565b915060006107938a60036000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156105dc5780601f106105b1576101008083540402835291602001916105dc565b60000b121580156106905750600061068a8960036000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156105dc5780601f106105b1576101008083540402835291602001916105dc565b600061088f8a60036000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156105dc5780601f106105b1576101008083540402835291602001916105dc565b60000b12156108a35760018201935061092a565b600061091b8960036000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156105dc5780601f106105b1576101008083540402835291602001916105dc565b60000b131561092a5781925082505b82841015610696576107198484600060028084068184060104600283046002850401019050610c9d565b610c32610c3d875b604080516020818101835260008083528351808301855281905283518083018552818152845180840186528281528551606081018752603081526000805160206119c1833981519152948101949094527f655d5b3130537461636b506f7045785d000000000000000000000000000000008487015294519394929390928291829190829089908059106109ec5750595b908082528060200260200182016040528015610a03575b50955060208601945061131c611350865b6040805160208101909152600080825280808415156117b657604080518082019091526001815260fc60020a600302602082015293505b505050919050565b60000b12158015610add57506000610ad78960036000508481548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156105dc5780601f106105b1576101008083540402835291602001916105dc565b60000b13155b15610b18576000851115610b2357604080518082019091526001815260fa60020a600b026020820152610b2090610bc9565b95506001909401935b600101610699565b95505b610b0f60026000508281548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529092830182828015610bc35780601f10610b9857610100808354040283529160200191610bc3565b820191906000526020600020905b815481529060010190602001808311610ba657829003601f168201915b50505050505b60408051602081810183526000918290528251606081018452603181526000805160206119c1833981519152918101919091527f655d5b3131537461636b417070656e645d000000000000000000000000000000928101929092529081808061125f848761041b565b955061067b8661095c565b865b60408051808201909152600181527f7b000000000000000000000000000000000000000000000000000000000000006020820152600090611357906103b6565b50600160a060020a03811660009081526020839052604090205460ff165b92915050565b565b611474600082610388565b15610cba57610002565b61036f816114ab600082610352565b15610cd357610002565b604080516020810190915260008082529350839250610cf1906103b6565b9250600090505b600254811015610d2f576000821115610d3e57604080518082019091526001815260fa60020a600b026020820152610d3b90610bc9565b610dc1610dcc8461095c565b92505b610db360026000508281548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529092830182828015610bc35780601f10610b9857610100808354040283529160200191610bc3565b925060019182019101610cf8565b9250610dd28361095c565b83610c3f565b935050505090565b610381565b15610de957610002565b60408051808201909152601681527f436572746966696361746520696e746f206164642e2e000000000000000000006020820152610ea49060408051602081810183526000918290528251606081018452602b81526000805160206119c1833981519152818301527f655d5b3035766d6c6f675d0000000000000000000000000000000000000000008185015283518085019094526001845260fa60020a601f029184019190915290918290819081906114e290859061041b565b5060028054600181018083558281838015829011610ed357600083815260209020610ed3918101908301610f42565b5050509190906000526020600020900160008490919091509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610fa657805160ff19168380011785555b50610fd6929150610f8e565b50506001015b80821115610fa2576000818150805460018160011615610100020316600290046000825580601f10610f745750610f3c565b601f016020900490600052602060002090810190610f3c91905b80821115610fa25760008155600101610f8e565b5090565b82800160010185558215610f30579182015b82811115610f30578251826000505591602001919060010190610fb8565b5050506003600050805480600101828181548183558181151161100a5760008381526020902061100a918101908301611079565b5050509190906000526020600020900160008390919091509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106110c957805160ff19168380011785555b506110f9929150610f8e565b50506001015b80821115610fa2576000818150805460018160011615610100020316600290046000825580601f106110ab5750611073565b601f0160209004906000526020600020908101906110739190610f8e565b82800160010185558215611067579182015b828111156110675782518260005055916020019190600101906110db565b5050604080518082018252601981527f61646420612063657274696669636174652073756363657373000000000000006020808301919091528251808401909352600983527f4365727451756572790000000000000000000000000000000000000000000000908301526111ec92509060408051602081810183526000918290528251606081018452602b81526000805160206119c1833981519152818301527f655d5b3035766d6c6f675d0000000000000000000000000000000000000000008185015283518085019094526001845260fa60020a601f0291840191909152909182908190819061164990859061041b565b505050565b604051600160a060020a038216907fee1504a83b6d4a361f4c1dc78ab59bfa30d6a3b6612c403e86bb01ef2984295f90600090a250565b604051600160a060020a038216907f270d9b30cf5b0793bbfd54c9d5b94aeb49462b8148399000265144a8722da6b690600090a250565b80516020909101209695505050505050565b8251845111156112ec5760019150611305565b828181518110156100025790602001015160f860020a900460f860020a02600160f860020a031916848281518110156100025790602001015160f860020a900460f860020a02600160f860020a03191610156112e4576000199150611305565b6001016105ed565b825184511015611300576000199150611305565b600091505b5092915050565b8597505b50505050505050919050565b8051602080830182812060408051938401905260008352939a50975091955093509150839050801561130c57819750611310565b889061041b565b60408051808201909152600581527f746f74616c00000000000000000000000000000000000000000000000000000060208201529091506113a29083600061146d8361185884610a14565b60408051808201909152600981527f2c2264617461223a5b000000000000000000000000000000000000000000000060208201529091506113e290610bc9565b90506113ed83610bc9565b60408051808201909152600181527f5d00000000000000000000000000000000000000000000000000000000000000602082015290915061142d90610bc9565b60408051808201909152600181527f7d00000000000000000000000000000000000000000000000000000000000000602082015290915061146d90610bc9565b9392505050565b604051600160a060020a038216907f0a8eb35e5ca14b3d6f28e4abf2f128dbab231a58b56e89beb5d636115001e16590600090a250565b604051600160a060020a038216907f22380c05984257a1cb900161c713dd71d39e74820f1aea43bd3f1bdd2096129990600090a250565b9350611600611629600160a060020a0330165b602060405190810160405280600081526020015060006000602a60405180591061151c5750595b908082528060200260200182016040528015611533575b509250825060fc60020a600302836000815181101561000257906020010190600160f860020a031916908160001a9053507f7800000000000000000000000000000000000000000000000000000000000000836001815181101561000257906020010190600160f860020a031916908160001a905350602991505b600260ff8316106118f757506010830492600f16600a8110156118fe578060300160f860020a02838360ff16815181101561000257906020010190600160f860020a031916908160001a905350611932565b604080518082019091526001815260fa60020a601f02602082015290945061163090859061041b565b859061041b565b935061163c848761041b565b5060009695505050505050565b9350611660611629600160a060020a0330166114f5565b604080518082019091526001815260fa60020a601f02602082015290945061168990859061041b565b604080518082019091526001815260fd60020a60208201529094506117329085908990895b6020604051908101604052806000815260200150600060006000600060006000875189518b518d510101016040518059106116e65750595b9080825280602002602001820160405280156116fd575b509650865060208b01955060208a0194506020890193506020880192506020870191506000905061193e818301878d5161047d565b506000979650505050505050565b1561174a57610002565b600160a060020a0381166000908152602083905260409020805460ff191660011790555050565b151561177c57610002565b600160a060020a0316600090815260209190915260409020805460ff19169055565b6117ad8651820183875161047d565b50505092915050565b600092508491505b60008211156117d857600a826001909401930491506117be565b8260ff166040518059106117e95750595b908082528060200260200182016040528015611800575b5093505060001982015b6000851115610a4b57600a850660300160f860020a0284828060019003935060ff16815181101561000257906020010190600160f860020a031916908160001a905350600a8504945061180a565b60408051602081810183526000918290528251606081018452603981526000805160206119c1833981519152818301527f655d5b3139537461636b417070656e644b657956616c75655d00000000000000818501528351808501909452600a84527f7c242526402a5e23217c000000000000000000000000000000000000000000009184019190915290918290819081906119ae9085908990896116ae565b5050919050565b600a810360610160f860020a02838360ff16815181101561000257906020010190600160f860020a031916908160001a9053505b600019909101906115ae565b8a51810190508050611954818301868c5161047d565b895181019050805061196a818301858b5161047d565b8851810190508050611980818301848a5161047d565b50949998505050505050505050565b50905182516020929092036101000a6000190180199091169116179052565b8051602090910120979650505050505050565b36396439386436613034633431623436303561616362376264326637346265";

    public static final String FUNC_ADDWHITELISTED = "addWhitelisted";

    public static final String FUNC_REMOVEWHITELISTED = "removeWhitelisted";

    public static final String FUNC_SEARCH = "search";

    public static final String FUNC_ISWHITELISTED = "isWhitelisted";

    public static final String FUNC_RENOUNCEWHITELISTADMIN = "renounceWhitelistAdmin";

    public static final String FUNC_ADDWHITELISTADMIN = "addWhitelistAdmin";

    public static final String FUNC_LISTALL = "listAll";

    public static final String FUNC_ISWHITELISTADMIN = "isWhitelistAdmin";

    public static final String FUNC_RENOUNCEWHITELISTED = "renounceWhitelisted";

    public static final String FUNC_ADDCERTIFICATE = "addCertificate";

    public static final String FUNC_SUMOFCERTS = "sumOfCerts";

    public static final Event WHITELISTEDADDED_EVENT = new Event("WhitelistedAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event WHITELISTEDREMOVED_EVENT = new Event("WhitelistedRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event WHITELISTADMINADDED_EVENT = new Event("WhitelistAdminAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event WHITELISTADMINREMOVED_EVENT = new Event("WhitelistAdminRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected CertQuery(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CertQuery(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CertQuery(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CertQuery(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> addWhitelisted(String account) {
        final Function function = new Function(
                FUNC_ADDWHITELISTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeWhitelisted(String account) {
        final Function function = new Function(
                FUNC_REMOVEWHITELISTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> search(String startTime, String endTime) {
        final Function function = new Function(FUNC_SEARCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(startTime), 
                new org.web3j.abi.datatypes.Utf8String(endTime)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> isWhitelisted(String account) {
        final Function function = new Function(FUNC_ISWHITELISTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> renounceWhitelistAdmin() {
        final Function function = new Function(
                FUNC_RENOUNCEWHITELISTADMIN, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addWhitelistAdmin(String account) {
        final Function function = new Function(
                FUNC_ADDWHITELISTADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> listAll() {
        final Function function = new Function(FUNC_LISTALL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> isWhitelistAdmin(String account) {
        final Function function = new Function(FUNC_ISWHITELISTADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> renounceWhitelisted() {
        final Function function = new Function(
                FUNC_RENOUNCEWHITELISTED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addCertificate(String json, String issuanceDate) {
        final Function function = new Function(
                FUNC_ADDCERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(json), 
                new org.web3j.abi.datatypes.Utf8String(issuanceDate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> sumOfCerts() {
        final Function function = new Function(FUNC_SUMOFCERTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<WhitelistedAddedEventResponse> getWhitelistedAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WHITELISTEDADDED_EVENT, transactionReceipt);
        ArrayList<WhitelistedAddedEventResponse> responses = new ArrayList<WhitelistedAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WhitelistedAddedEventResponse typedResponse = new WhitelistedAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WhitelistedAddedEventResponse> whitelistedAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WhitelistedAddedEventResponse>() {
            @Override
            public WhitelistedAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WHITELISTEDADDED_EVENT, log);
                WhitelistedAddedEventResponse typedResponse = new WhitelistedAddedEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WhitelistedAddedEventResponse> whitelistedAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WHITELISTEDADDED_EVENT));
        return whitelistedAddedEventFlowable(filter);
    }

    public List<WhitelistedRemovedEventResponse> getWhitelistedRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WHITELISTEDREMOVED_EVENT, transactionReceipt);
        ArrayList<WhitelistedRemovedEventResponse> responses = new ArrayList<WhitelistedRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WhitelistedRemovedEventResponse typedResponse = new WhitelistedRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WhitelistedRemovedEventResponse> whitelistedRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WhitelistedRemovedEventResponse>() {
            @Override
            public WhitelistedRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WHITELISTEDREMOVED_EVENT, log);
                WhitelistedRemovedEventResponse typedResponse = new WhitelistedRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WhitelistedRemovedEventResponse> whitelistedRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WHITELISTEDREMOVED_EVENT));
        return whitelistedRemovedEventFlowable(filter);
    }

    public List<WhitelistAdminAddedEventResponse> getWhitelistAdminAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WHITELISTADMINADDED_EVENT, transactionReceipt);
        ArrayList<WhitelistAdminAddedEventResponse> responses = new ArrayList<WhitelistAdminAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WhitelistAdminAddedEventResponse typedResponse = new WhitelistAdminAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WhitelistAdminAddedEventResponse> whitelistAdminAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WhitelistAdminAddedEventResponse>() {
            @Override
            public WhitelistAdminAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WHITELISTADMINADDED_EVENT, log);
                WhitelistAdminAddedEventResponse typedResponse = new WhitelistAdminAddedEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WhitelistAdminAddedEventResponse> whitelistAdminAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WHITELISTADMINADDED_EVENT));
        return whitelistAdminAddedEventFlowable(filter);
    }

    public List<WhitelistAdminRemovedEventResponse> getWhitelistAdminRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WHITELISTADMINREMOVED_EVENT, transactionReceipt);
        ArrayList<WhitelistAdminRemovedEventResponse> responses = new ArrayList<WhitelistAdminRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WhitelistAdminRemovedEventResponse typedResponse = new WhitelistAdminRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WhitelistAdminRemovedEventResponse> whitelistAdminRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WhitelistAdminRemovedEventResponse>() {
            @Override
            public WhitelistAdminRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WHITELISTADMINREMOVED_EVENT, log);
                WhitelistAdminRemovedEventResponse typedResponse = new WhitelistAdminRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WhitelistAdminRemovedEventResponse> whitelistAdminRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WHITELISTADMINREMOVED_EVENT));
        return whitelistAdminRemovedEventFlowable(filter);
    }

    @Deprecated
    public static CertQuery load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CertQuery(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CertQuery load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CertQuery(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CertQuery load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CertQuery(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CertQuery load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CertQuery(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CertQuery> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CertQuery.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<CertQuery> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CertQuery.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CertQuery> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CertQuery.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CertQuery> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CertQuery.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class WhitelistedAddedEventResponse {
        public Log log;

        public String account;
    }

    public static class WhitelistedRemovedEventResponse {
        public Log log;

        public String account;
    }

    public static class WhitelistAdminAddedEventResponse {
        public Log log;

        public String account;
    }

    public static class WhitelistAdminRemovedEventResponse {
        public Log log;

        public String account;
    }
}
