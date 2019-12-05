package com.whu.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
    private static final String BINARY = "606060405261000f61001a5b3390565b61003d61004b61000b565b6100566000825b6100c482826000600160a060020a03821615156100f557610002565b61199b806101156000396000f35b61008d600182610021565b604051600160a060020a038216907f22380c05984257a1cb900161c713dd71d39e74820f1aea43bd3f1bdd2096129990600090a250565b604051600160a060020a038216907fee1504a83b6d4a361f4c1dc78ab59bfa30d6a3b6612c403e86bb01ef2984295f90600090a250565b156100ce57610002565b600160a060020a0381166000908152602083905260409020805460ff191660011790555050565b50600160a060020a03166000908152602091909152604090205460ff169056606060405236156100985760e060020a600035046303447e98811461009d57806310154bad1461010d578063291d9549146101235780632c3560ee146101395780633af32abf146101e65780634c5a628c146101ff5780637362d9c81461021257806378a9eeed14610228578063ac9e57601461024e578063bb5f747b14610326578063d6cd94731461033e578063e8c2807514610351575b610002565b34610002576103606004808035906020019082018035906020019191908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965050505050505060408051602081019091526000808252808080806103f66101f25b3361035d565b34610002576103ce60043561081e610332610107565b34610002576103ce600435610853610332610107565b34610002576103606004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f81018390048302840183019094528383529799986044989297509190910194509092508291508401838280828437509496505050505050506040805160208101909152600080825280808080806108736101f2610107565b34610002576103d06004355b6000610ecd600183610839565b34610002576103ce610ed3610ed5610107565b34610002576103ce600435610ee0610332610107565b34610002576103605b604080516020810190915260008082528080610ef96101f2610107565b34610002576103ce6004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f8101839004830284018301909452838352979998604498929750919091019450909250829150840183828082843750506040805160209735808a0135601f81018a90048a0283018a0190935282825296989760649791965060249190910194509092508291508401838280828437509496505050505050506110046101f2610107565b34610002576103d06004355b6000610ecd8183610839565b34610002576103ce610ed36113cc610107565b34610002576103e46003545b90565b60405180806020018281038252838181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156103c05780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b005b604080519115158252519081900360200190f35b60408051918252519081900360200190f35b1561040057610002565b600260005087604051808280519060200190808383829060006004602084601f0104600302600f01f1509050019150509081526020016040518091039020600050945060009350848054905092506000915061055660206040519081016040528060008152602001505b60408051602081810183526000918290528251606081018452602f815260008051602061197b833981519152918101919091527f655d5b3039537461636b507573685d000000000000000000000000000000000092810192909252908180806113d184875b602060405190810160405280600081526020015060006000600084518651016040518059106104fb5750595b908082528060200260200182016040528015610512575b50935083506020860192506020850191506020840190506116c2818488515b60005b602082106118f8578251845260209384019390920191601f1990910190610534565b9350600090505b828110156105925760008211156106ad57604080518082019091526001815260fa60020a600b0260208201526106aa9061074f565b6107c66107d1855b6040805160208181018352600080835283518083018552819052835180830185528181528451808401865282815285516060810187526030815260008051602061197b833981519152948101949094527f655d5b3130537461636b506f7045785d0000000000000000000000000000000084870152945193949293909282918291908290899080591061062a5750595b908082528060200260200182016040528015610641575b5095506020860194506113f3611427865b6040805160208101909152600080825280808415156116da5760408051808201909152600181527f3000000000000000000000000000000000000000000000000000000000000000602082015293505b505050919050565b93505b6107b8858281548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156107495780601f1061071e57610100808354040283529160200191610749565b820191906000526020600020905b81548152906001019060200180831161072c57829003601f168201915b50505050505b604080516020818101835260009182905282516060810184526031815260008051602061197b833981519152918101919091527f655d5b3131537461636b417070656e645d00000000000000000000000000000092810192909252908180806113d184876104cf565b93506001918201910161055d565b93506108138461059a565b835b60408051808201909152600181527f7b00000000000000000000000000000000000000000000000000000000000000602082015260009061142e9061046a565b979650505050505050565b1561082857610002565b6108508161154b6001825b61189a82825b6000600160a060020a0382161515610eaf57610002565b50565b1561085d57610002565b610850815b6115826001825b6118cb8282610839565b1561087d57610002565b60408051602081019091526000808252965086955061089b9061046a565b60035460048054929850600096509094508591610920918c9184908110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156109d55780601f106109aa576101008083540402835291602001916109d5565b60000b12158015610a60575060048054600091610a5a918b91906000198101908110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156109d55780601f106109aa576101008083540402835291602001916109d5565b820191906000526020600020905b8154815290600101906020018083116109b857829003601f168201915b50505050506109e2909190565b6000805b8351811080156109f65750825181105b156115b957828181518110156100025790602001015160f860020a900460f860020a02600160f860020a031916848281518110156100025790602001015160f860020a900460f860020a02600160f860020a03191611156115cc576001915061164d565b60000b13155b15610d2357610a6d610231565b9650610a77565b96505b50505050505092915050565b60000b13155b15610c10575b50825b82811015610d4d576000610d598a60046000508481548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156109d55780601f106109aa576101008083540402835291602001916109d5565b91506000610b8c8a60046000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156109d55780601f106109aa576101008083540402835291602001916109d5565b60000b12158015610a8957506000610a838960046000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156109d55780601f106109aa576101008083540402835291602001916109d5565b6000610c888a60046000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156109d55780601f106109aa576101008083540402835291602001916109d5565b60000b1215610c9c57600182019350610d23565b6000610d148960046000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156109d55780601f106109aa576101008083540402835291602001916109d5565b60000b1315610d235781925082505b82841015610a8f57610b128484600060028084068184060104600283046002850401019050610ecd565b610e9e610ea98761059a565b60000b12158015610de357506000610ddd8960046000508481548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156109d55780601f106109aa576101008083540402835291602001916109d5565b60000b13155b15610e1e576000851115610e2957604080518082019091526001815260fa60020a600b026020820152610e269061074f565b95506001909401935b600101610a92565b95505b610e1560036000508281548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156107495780601f1061071e57610100808354040283529160200191610749565b9550610a748661059a565b866107d3565b50600160a060020a03811660009081526020839052604090205460ff165b92915050565b565b611654600082610869565b15610eea57610002565b6108508161168b600082610833565b15610f0357610002565b604080516020810190915260008082529350839250610f219061074f565b9250600090505b600354811015610f5f576000821115610f6e57604080518082019091526001815260fa60020a600b026020820152610f6b9061074f565b610ff16107d18461059a565b92505b610fe360036000508281548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825290928301828280156107495780601f1061071e57610100808354040283529160200191610749565b925060019182019101610f28565b9250610ffc8361059a565b935050505090565b1561100e57610002565b600260005081604051808280519060200190808383829060006004602084601f0104600302600f01f1509050019150509081526020016040518091039020600050805480600101828181548183558181151161107b5760008381526020902061107b9181019083016110ea565b5050509190906000526020600020900160008590919091509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061114e57805160ff19168380011785555b5061117e929150611136565b50506001015b8082111561114a576000818150805460018160011615610100020316600290046000825580601f1061111c57506110e4565b601f0160209004906000526020600020908101906110e491905b8082111561114a5760008155600101611136565b5090565b828001600101855582156110d8579182015b828111156110d8578251826000505591602001919060010190611160565b505050600360005080548060010182818154818355818115116111b2576000838152602090206111b2918101908301611221565b5050509190906000526020600020900160008590919091509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061127157805160ff19168380011785555b506112a1929150611136565b50506001015b8082111561114a576000818150805460018160011615610100020316600290046000825580601f10611253575061121b565b601f01602090049060005260206000209081019061121b9190611136565b8280016001018555821561120f579182015b8281111561120f578251826000505591602001919060010190611283565b505050600460005080548060010182818154818355818115116112d5576000838152602090206112d5918101908301611344565b5050509190906000526020600020900160008490919091509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061139457805160ff19168380011785555b506113c4929150611136565b50506001015b8082111561114a576000818150805460018160011615610100020316600290046000825580601f10611376575061133e565b601f01602090049060005260206000209081019061133e9190611136565b82800160010185558215611332579182015b828111156113325782518260005055916020019190600101906113a6565b505050505050565b610862565b80516020909101209695505050505050565b8597505b50505050505050919050565b8051602080830182812060408051938401905260008352939a5097509195509350915083905080156113e3578197506113e7565b88906104cf565b60408051808201909152600581527f746f74616c0000000000000000000000000000000000000000000000000000006020820152909150611479908360006115448361177c84610652565b60408051808201909152600981527f2c2264617461223a5b000000000000000000000000000000000000000000000060208201529091506114b99061074f565b90506114c48361074f565b60408051808201909152600181527f5d0000000000000000000000000000000000000000000000000000000000000060208201529091506115049061074f565b60408051808201909152600181527f7d0000000000000000000000000000000000000000000000000000000000000060208201529091506115449061074f565b9392505050565b604051600160a060020a038216907fee1504a83b6d4a361f4c1dc78ab59bfa30d6a3b6612c403e86bb01ef2984295f90600090a250565b604051600160a060020a038216907f270d9b30cf5b0793bbfd54c9d5b94aeb49462b8148399000265144a8722da6b690600090a250565b825184511115611634576001915061164d565b828181518110156100025790602001015160f860020a900460f860020a02600160f860020a031916848281518110156100025790602001015160f860020a900460f860020a02600160f860020a031916101561162c57600019915061164d565b6001016109e6565b82518451101561164857600019915061164d565b600091505b5092915050565b604051600160a060020a038216907f0a8eb35e5ca14b3d6f28e4abf2f128dbab231a58b56e89beb5d636115001e16590600090a250565b604051600160a060020a038216907f22380c05984257a1cb900161c713dd71d39e74820f1aea43bd3f1bdd2096129990600090a250565b6116d186518201838751610531565b50505092915050565b600092508491505b60008211156116fc57600a826001909401930491506116e2565b8260ff1660405180591061170d5750595b908082528060200260200182016040528015611724575b5093505060001982015b60008511156106a257600a850660300160f860020a0284828060019003935060ff16815181101561000257906020010190600160f860020a031916908160001a905350600a8504945061172e565b604080516020818101835260009182905282516060810184526039815260008051602061197b833981519152818301527f655d5b3139537461636b417070656e644b657956616c75655d00000000000000818501528351808501909452600a84527f7c242526402a5e23217c000000000000000000000000000000000000000000009184019190915290918290819081906119179085908990896020604051908101604052806000815260200150600060006000600060006000875189518b518d5101010160405180591061184e5750595b908082528060200260200182016040528015611865575b509650865060208b01955060208a0194506020890193506020880192506020870191506000905061192a818301878d51610531565b156118a457610002565b600160a060020a0381166000908152602083905260409020805460ff191660011790555050565b15156118d657610002565b600160a060020a0316600090815260209190915260409020805460ff19169055565b50905182516020929092036101000a6000190180199091169116179052565b8051602090910120979650505050505050565b8a51810190508050611940818301868c51610531565b8951810190508050611956818301858b51610531565b885181019050805061196c818301848a51610531565b50949998505050505050505050565b36396439386436613034633431623436303561616362376264326637346265";

    public static final String FUNC_SEARCHBYLOCCODE = "searchByLocCode";

    public static final String FUNC_ADDWHITELISTED = "addWhitelisted";

    public static final String FUNC_REMOVEWHITELISTED = "removeWhitelisted";

    public static final String FUNC_SEARCH = "search";

    public static final String FUNC_ISWHITELISTED = "isWhitelisted";

    public static final String FUNC_RENOUNCEWHITELISTADMIN = "renounceWhitelistAdmin";

    public static final String FUNC_ADDWHITELISTADMIN = "addWhitelistAdmin";

    public static final String FUNC_LISTALL = "listAll";

    public static final String FUNC_ADDCERTIFICATE = "addCertificate";

    public static final String FUNC_ISWHITELISTADMIN = "isWhitelistAdmin";

    public static final String FUNC_RENOUNCEWHITELISTED = "renounceWhitelisted";

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

    public RemoteCall<String> searchByLocCode(String locCode) {
        final Function function = new Function(FUNC_SEARCHBYLOCCODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(locCode)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<TransactionReceipt> addCertificate(String json, String issuanceDate, String locCode) {
        final Function function = new Function(
                FUNC_ADDCERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(json), 
                new org.web3j.abi.datatypes.Utf8String(issuanceDate), 
                new org.web3j.abi.datatypes.Utf8String(locCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    @Deprecated
    public static RemoteCall<CertQuery> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CertQuery.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CertQuery> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CertQuery.class, web3j, transactionManager, contractGasProvider, BINARY, "");
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
