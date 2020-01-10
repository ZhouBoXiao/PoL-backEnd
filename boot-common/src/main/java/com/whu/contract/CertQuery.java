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
    private static final String BINARY = "606060405261000f61001a5b3390565b61003d61004b61000b565b6100566000825b6100c482826000600160a060020a03821615156100f557610002565b611a0b806101156000396000f35b61008d600182610021565b604051600160a060020a038216907f22380c05984257a1cb900161c713dd71d39e74820f1aea43bd3f1bdd2096129990600090a250565b604051600160a060020a038216907fee1504a83b6d4a361f4c1dc78ab59bfa30d6a3b6612c403e86bb01ef2984295f90600090a250565b156100ce57610002565b600160a060020a0381166000908152602083905260409020805460ff191660011790555050565b50600160a060020a03166000908152602091909152604090205460ff169056606060405236156100985760e060020a600035046303447e98811461009d57806310154bad14610112578063291d95491461012a5780632c3560ee146101405780633af32abf146101eb5780634c5a628c146101fb5780637362d9c81461020e57806378a9eeed14610224578063ac9e576014610248578063bb5f747b1461031e578063d6cd94731461032e578063e8c2807514610341575b610002565b34610002576103506004808035906020019082018035906020019191908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965050505050505060408051602081019091526000808252808080806103e6335b6000610f2f600183610852565b34610002576103be60043561080f61081f5b3361034d565b34610002576103be60043561086c61081f610124565b34610002576103506004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f81018390048302840183019094528383529799986044989297509190910194509092508291508401838280828437509496505050505050506040805160208101909152600080825280808080806108a333610105565b34610002576103c0600435610105565b34610002576103be610f35610f37610124565b34610002576103be600435610f4261081f610124565b34610002576103505b604080516020810190915260008082528080610f6733610105565b34610002576103be6004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f8101839004830284018301909452838352979998604498929750919091019450909250829150840183828082843750506040805160209735808a0135601f81018a90048a0283018a01909352828252969897606497919650602491909101945090925082915084018382808284375094965050505050505061107333610105565b34610002576103c060043561081f565b34610002576103be610f3561143c610124565b34610002576103d46003545b90565b60405180806020018281038252838181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156103b05780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b005b604080519115158252519081900360200190f35b60408051918252519081900360200190f35b15156103f157610002565b600260005087604051808280519060200190808383829060006004602084601f0104600302600f01f1509050019150509081526020016040518091039020600050945060009350848054905092506000915061054760206040519081016040528060008152602001505b60408051602081810183526000918290528251606081018452602f81526000805160206119eb833981519152918101919091527f655d5b3039537461636b507573685d0000000000000000000000000000000000928101929092529081808061144184875b602060405190810160405280600081526020015060006000600084518651016040518059106104ec5750595b908082528060200260200182016040528015610503575b5093508350602086019250602085019150602084019050611732818488515b60005b60208210611968578251845260209384019390920191601f1990910190610525565b9350600090505b8281101561058357600082111561069e57604080518082019091526001815260fa60020a600b02602082015261069b90610740565b6107b76107c2855b604080516020818101835260008083528351808301855281905283518083018552818152845180840186528281528551606081018752603081526000805160206119eb833981519152948101949094527f655d5b3130537461636b506f7045785d0000000000000000000000000000000084870152945193949293909282918291908290899080591061061b5750595b908082528060200260200182016040528015610632575b509550602086019450611463611497865b60408051602081019091526000808252808084151561174a5760408051808201909152600181527f3000000000000000000000000000000000000000000000000000000000000000602082015293505b505050919050565b93505b6107a9858281548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f8101859004850283018501909152808252909283018282801561073a5780601f1061070f5761010080835404028352916020019161073a565b820191906000526020600020905b81548152906001019060200180831161071d57829003601f168201915b50505050505b60408051602081810183526000918290528251606081018452603181526000805160206119eb833981519152918101919091527f655d5b3131537461636b417070656e645d000000000000000000000000000000928101929092529081808061144184876104c0565b93506001918201910161054e565b93506108048461058b565b835b60408051808201909152600181527f7b00000000000000000000000000000000000000000000000000000000000000602082015260009061149e9061045b565b979650505050505050565b151561082b57610002565b610f4d335b6000610f2f8183610852565b61083661081f610124565b151561084157610002565b610869816115bb6001825b61190a82825b6000600160a060020a0382161515610f1157610002565b50565b151561087757610002565b61088261081f610124565b151561088d57610002565b610869815b6115f26001825b61193b8282610852565b15156108ae57610002565b6040805160208101909152600080825296508695506108cc9061045b565b6003546040805160208101909152600080825290995091975090945092508383111561096b5760006109778a6004600050600081548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529092830182828015610a2c5780601f10610a0157610100808354040283529160200191610a2c565b610f06610ef18761058b565b60000b12158015610ab7575060048054600091610ab1918b91906000198101908110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529092830182828015610a2c5780601f10610a0157610100808354040283529160200191610a2c565b820191906000526020600020905b815481529060010190602001808311610a0f57829003601f168201915b5050505050610a39909190565b6000805b835181108015610a4d5750825181105b1561162957828181518110156100025790602001015160f860020a900460f860020a02600160f860020a031916848281518110156100025790602001015160f860020a900460f860020a02600160f860020a031916111561163c57600191506116bd565b60000b13155b15610d6b57610ac461022d565b9650610efa565b60000b13155b15610c58575b50825b82811015610d95576000610da18a60046000508481548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529092830182828015610a2c5780601f10610a0157610100808354040283529160200191610a2c565b91506000610bd48a60046000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529092830182828015610a2c5780601f10610a0157610100808354040283529160200191610a2c565b60000b12158015610ad157506000610acb8960046000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529092830182828015610a2c5780601f10610a0157610100808354040283529160200191610a2c565b6000610cd08a60046000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529092830182828015610a2c5780601f10610a0157610100808354040283529160200191610a2c565b60000b1215610ce457600182019350610d6b565b6000610d5c8960046000508581548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529092830182828015610a2c5780601f10610a0157610100808354040283529160200191610a2c565b60000b1315610d6b5781925082505b82841015610ad757610b5a8484600060028084068184060104600283046002850401019050610f2f565b610ee6610ef18761058b565b60000b12158015610e2b57506000610e258960046000508481548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529092830182828015610a2c5780601f10610a0157610100808354040283529160200191610a2c565b60000b13155b15610e66576000851115610e7157604080518082019091526001815260fa60020a600b026020820152610e6e90610740565b95506001909401935b600101610ada565b95505b610e5d60036000508281548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f8101859004850283018501909152808252909283018282801561073a5780601f1061070f5761010080835404028352916020019161073a565b9550610ac48661058b565b866107c4565b96505b50505050505092915050565b9550610ef78661058b565b50600160a060020a03811660009081526020839052604090205460ff165b92915050565b565b6116c4600082610899565b151561081a57610002565b1515610f5857610002565b610869816116fb60008261084c565b1515610f7257610002565b604080516020810190915260008082529350839250610f9090610740565b9250600090505b600354811015610fce576000821115610fdd57604080518082019091526001815260fa60020a600b026020820152610fda90610740565b6110606107c28461058b565b92505b61105260036000508281548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f8101859004850283018501909152808252909283018282801561073a5780601f1061070f5761010080835404028352916020019161073a565b925060019182019101610f97565b925061106b8361058b565b935050505090565b151561107e57610002565b600260005081604051808280519060200190808383829060006004602084601f0104600302600f01f150905001915050908152602001604051809103902060005080548060010182818154818355818115116110eb576000838152602090206110eb91810190830161115a565b5050509190906000526020600020900160008590919091509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106111be57805160ff19168380011785555b506111ee9291506111a6565b50506001015b808211156111ba576000818150805460018160011615610100020316600290046000825580601f1061118c5750611154565b601f01602090049060005260206000209081019061115491905b808211156111ba57600081556001016111a6565b5090565b82800160010185558215611148579182015b828111156111485782518260005055916020019190600101906111d0565b5050506003600050805480600101828181548183558181151161122257600083815260209020611222918101908301611291565b5050509190906000526020600020900160008590919091509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106112e157805160ff19168380011785555b506113119291506111a6565b50506001015b808211156111ba576000818150805460018160011615610100020316600290046000825580601f106112c3575061128b565b601f01602090049060005260206000209081019061128b91906111a6565b8280016001018555821561127f579182015b8281111561127f5782518260005055916020019190600101906112f3565b50505060046000508054806001018281815481835581811511611345576000838152602090206113459181019083016113b4565b5050509190906000526020600020900160008490919091509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061140457805160ff19168380011785555b506114349291506111a6565b50506001015b808211156111ba576000818150805460018160011615610100020316600290046000825580601f106113e657506113ae565b601f0160209004906000526020600020908101906113ae91906111a6565b828001600101855582156113a2579182015b828111156113a2578251826000505591602001919060010190611416565b505050505050565b610892565b80516020909101209695505050505050565b8597505b50505050505050919050565b8051602080830182812060408051938401905260008352939a50975091955093509150839050801561145357819750611457565b88906104c0565b60408051808201909152600581527f746f74616c00000000000000000000000000000000000000000000000000000060208201529091506114e9908360006115b4836117ec84610643565b60408051808201909152600981527f2c2264617461223a5b0000000000000000000000000000000000000000000000602082015290915061152990610740565b905061153483610740565b60408051808201909152600181527f5d00000000000000000000000000000000000000000000000000000000000000602082015290915061157490610740565b60408051808201909152600181527f7d0000000000000000000000000000000000000000000000000000000000000060208201529091506115b490610740565b9392505050565b604051600160a060020a038216907fee1504a83b6d4a361f4c1dc78ab59bfa30d6a3b6612c403e86bb01ef2984295f90600090a250565b604051600160a060020a038216907f270d9b30cf5b0793bbfd54c9d5b94aeb49462b8148399000265144a8722da6b690600090a250565b8251845111156116a457600191506116bd565b828181518110156100025790602001015160f860020a900460f860020a02600160f860020a031916848281518110156100025790602001015160f860020a900460f860020a02600160f860020a031916101561169c5760001991506116bd565b600101610a3d565b8251845110156116b85760001991506116bd565b600091505b5092915050565b604051600160a060020a038216907f0a8eb35e5ca14b3d6f28e4abf2f128dbab231a58b56e89beb5d636115001e16590600090a250565b604051600160a060020a038216907f22380c05984257a1cb900161c713dd71d39e74820f1aea43bd3f1bdd2096129990600090a250565b61174186518201838751610522565b50505092915050565b600092508491505b600082111561176c57600a82600190940193049150611752565b8260ff1660405180591061177d5750595b908082528060200260200182016040528015611794575b5093505060001982015b600085111561069357600a850660300160f860020a0284828060019003935060ff16815181101561000257906020010190600160f860020a031916908160001a905350600a8504945061179e565b60408051602081810183526000918290528251606081018452603981526000805160206119eb833981519152818301527f655d5b3139537461636b417070656e644b657956616c75655d00000000000000818501528351808501909452600a84527f7c242526402a5e23217c000000000000000000000000000000000000000000009184019190915290918290819081906119879085908990896020604051908101604052806000815260200150600060006000600060006000875189518b518d510101016040518059106118be5750595b9080825280602002602001820160405280156118d5575b509650865060208b01955060208a0194506020890193506020880192506020870191506000905061199a818301878d51610522565b1561191457610002565b600160a060020a0381166000908152602083905260409020805460ff191660011790555050565b151561194657610002565b600160a060020a0316600090815260209190915260409020805460ff19169055565b50905182516020929092036101000a6000190180199091169116179052565b8051602090910120979650505050505050565b8a518101905080506119b0818301868c51610522565b89518101905080506119c6818301858b51610522565b88518101905080506119dc818301848a51610522565b50949998505050505050505050565b36396439386436613034633431623436303561616362376264326637346265";

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
