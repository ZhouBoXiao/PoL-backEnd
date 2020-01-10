package com.whu.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
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
public class GeoHashCon extends Contract {
    private static final String BINARY = "6060604052611264806100126000396000f3606060405260e060020a600035046306e63ff8811461003f57806307d05f3a146101c557806311114af114610322578063c83f2e2d1461049a575b610002565b34610002576104ef6004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f81018390048302840183019094528383529799986044989297509190910194509092508291508401838280828437509496505050505050506020604051908101604052806000815260200150602060405190810160405280600081526020015060006106b6846040604051908101604052806001815260200160fa60020a600b02815260200150600060005088604051808280519060200190808383829060006004602084601f0104600302600f01f15090500191505090815260200160405180910390206000508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107835780601f1061075857610100808354040283529160200191610783565b34610002576104f16004808035906020019082018035906020019191908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965050505050505060408051602081810183526000918290528251808201845282815283518085018552838152808301849052845180860186528481528084018590528551808501875285815286519485019096528484528651939592949193909291906009141561055f57600060005087604051808280519060200190808383829060006004602084601f0104600302600f01f15090500191505090815260200160405180910390206000508054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610bdc5780601f10610bb157610100808354040283529160200191610bdc565b34610002576104f16004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750949650505050505050602060405190810160405280600081526020015060006000600160005084604051808280519060200190808383829060006004602084601f0104600302600f01f1509050019150509081526020016040518091039020600050600101600050805490509150600090505b81811015610f6357610f6a600160005085604051808280519060200190808383829060006004602084601f0104600302600f01f15090500191505090815260200160405180910390206000506001016000508281548110156100025760009182526020918290200180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529092830182828015610f9f5780601f10610f7457610100808354040283529160200191610f9f565b34610002576104f16004808035906020019082018035906020019191908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965061056495505050505050565b005b60405180806020018281038252838181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156105515780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610bee875b6020604051908101604052806000815260200150600060006000600160005085604051808280519060200190808383829060006004602084601f0104600302600f01f1509050019150509081526020016040518091039020600050600101600050805490509250600091505b82821015610fc957600160005085604051808280519060200190808383829060006004602084601f0104600302600f01f150905001915050908152602001604051809103902060005060010160005082815481101561000257906000526020600020900160005090508080546001816001161561010002031660029004905060091415610fd157805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815261103292909184918301828280156110645780601f1061103957610100808354040283529160200191611064565b600060005086604051808280519060200190808383829060006004602084601f0104600302600f01f15090500191505090815260200160405180910390206000509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061082b57805160ff19168380011785555b5061085b9291505b8082111561089f5760008155600101610744565b820191906000526020600020905b81548152906001019060200180831161076657829003601f168201915b505050505061079190929190565b602060405190810160405280600081526020015060006000600060006000865188518a5101016040518059106107c45750595b9080825280602002602001820160405280156107db575b5095508550602089019450602088019350602087019250602086019150600090506110d1818301868b515b60005b602082106111ae578251845260209384019390920191601f1990910190610809565b8280016001018555821561073c579182015b8281111561073c57825182600050559160200191906001019061083d565b5050604080516020818101835260008083528351918201909352828152909450925090505b84518110156108a3578015156108aa576108fe856000600184016108b5565b5090565b5050505050565b610917856000600184015b60408051602081019091526000808252845181908590038411156108db57855185900393505b6000841161110a5760408051602081019091526000815292505b50509392505050565b925061090f565b5050505b81925082505b600101610880565b91508150600160005083604051808280519060200190808383829060006004602084601f0104600302600f01f150905001915050908152602001604051809103902060005060000160005082604051808280519060200190808383829060006004602084601f0104600302600f01f150905001915050908152602001604051809103902060009054906101000a900460ff161515610909576001600160005084604051808280519060200190808383829060006004602084601f0104600302600f01f150905001915050908152602001604051809103902060005060000160005083604051808280519060200190808383829060006004602084601f0104600302600f01f150905001915050908152602001604051809103902060006101000a81548160ff021916908360f860020a908102040217905550600160005083604051808280519060200190808383829060006004602084601f0104600302600f01f15090500191505090815260200160405180910390206000506001016000508054806001018281815481835581811511610ac257600083815260209020610ac2918101908301610b31565b5050509190906000526020600020900160008490919091509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b8157805160ff19168380011785555b50610905929150610744565b50506001015b8082111561089f576000818150805460018160011615610100020316600290046000825580601f10610b635750610b2b565b601f016020900490600052602060002090810190610b2b9190610744565b82800160010185558215610b1f579182015b82811115610b1f578251826000505591602001919060010190610b93565b820191906000526020600020905b815481529060010190602001808311610bbf57829003601f168201915b505050505095505b5050505050919050565b9450610c22855b60408051808201825260008082526020918201528151808301909252825182529182019181019190915290565b604080518082019091526001815260fa60020a600b026020820152909450610c4990610bf5565b9250610cc8848460006000826000015161114d85600001518660200151866000015187602001515b6000808080808887116111cd57602087116111df5760018760200360080260020a031980875116888b038a018a96505b818388511614610cbd576001870196819010610ca1578b8b0196505b5050508394506111d3565b600101604051805910610cd85750595b908082528060200260200182016040528015610d1557816020015b604080516020818101909252600081528252600019909201910181610cf35790505b509150600090505b6001825103811015610be457610d78610e518585604080518082019091526000808252602082015261118783838360408051808201909152600080825260208083018290528551868201518651928701516112199390610c71565b828281518110156100025790602001906020020181905250610eb36000600050838381518110156100025790602001906020020151604051808280519060200190808383829060006004602084601f0104600302600f01f15090500191505090815260200160405180910390206000508054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610ee85780601f10610ebd57610100808354040283529160200191610ee8565b60408051602081810183526000808352835191820184528082528451935192939192909190805910610e805750595b908082528060200260200182016040528015610e97575b509150602082019050610f5f8185602001518660000151610806565b9550600101610d1d565b820191906000526020600020905b815481529060010190602001808311610ecb57829003601f168201915b505050505087610ef9909190565b85905b60206040519081016040528060008152602001506000600060008451865101604051805910610f255750595b908082528060200260200182016040528015610f3c575b509350835060208601925060208501915060208401905061119681848851610806565b8192505b5050919050565b92506001016103db565b820191906000526020600020905b815481529060010190602001808311610f8257829003601f168201915b5050604080518082019091526001815260fa60020a600b0260208201528894935091506107919050565b505050919050565b805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815261108e92610ef69285918301828280156110c75780601f1061109c576101008083540402835291602001916110c7565b9350611091565b820191906000526020600020905b81548152906001019060200180831161104757829003601f168201915b5050604080518082019091526001815260fa60020a600b0260208201528994935091506107919050565b93505b6001909101906105d0565b820191906000526020600020905b8154815290600101906020018083116110aa57829003601f168201915b5050505050610564565b88518101905080506110e7818301858a51610806565b87518101905080506110fd818301848951610806565b5093979650505050505050565b836040518059106111185750595b90808252806020026020018201604052801561112f575b50925082506020860191506020830190506108f58186840186610806565b0190505b8351602085015101811161118757825160208086015186519186015160019095019461118e929185039091039084908490610c71565b5092915050565b019050611151565b6111a586518201838751610806565b50505092915050565b50905182516020929092036101000a6000190180199091169116179052565b88880194505b50505050949350505050565b8686208894506000935091505b86890383116111cd575085832081811415611209578394506111d3565b60019384019392909201916111ec565b60208087018051918601919091528051820385528651905191925001811415611245576000855261125b565b8351835186519101900385528351810160208601525b5090939250505056";

    public static final String FUNC_INSERT = "insert";

    public static final String FUNC_BOUNDINGBOXQUERY = "BoundingBoxQuery";

    public static final String FUNC_PRINT = "print";

    public static final String FUNC_QUERY = "Query";

    @Deprecated
    protected GeoHashCon(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected GeoHashCon(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected GeoHashCon(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected GeoHashCon(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> insert(String geohash, String cert) {
        final Function function = new Function(
                FUNC_INSERT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(geohash), 
                new org.web3j.abi.datatypes.Utf8String(cert)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> BoundingBoxQuery(String prefixs) {
        final Function function = new Function(FUNC_BOUNDINGBOXQUERY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(prefixs)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> print(String part) {
        final Function function = new Function(FUNC_PRINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(part)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> Query(String part) {
        final Function function = new Function(FUNC_QUERY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(part)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static GeoHashCon load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new GeoHashCon(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static GeoHashCon load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new GeoHashCon(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static GeoHashCon load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new GeoHashCon(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static GeoHashCon load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new GeoHashCon(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<GeoHashCon> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(GeoHashCon.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<GeoHashCon> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(GeoHashCon.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<GeoHashCon> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(GeoHashCon.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<GeoHashCon> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(GeoHashCon.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
