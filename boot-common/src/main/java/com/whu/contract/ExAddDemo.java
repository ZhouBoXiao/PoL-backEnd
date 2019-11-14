package com.whu.contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticArray;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class ExAddDemo extends Contract {
    private static final String BINARY = "6060604052610b29806100126000396000f3606060405236156100615760e060020a60003504631b206c5081146100665780634f2be91f146100f957806365530843146101c8578063a2375d1e14610281578063b5e7bc60146102ed578063b958abd514610393578063d46300fd1461044d575b610002565b34610002576104bb6004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f810183900483028401830190945283835297999860449892975091909101945090925082915084018382808284375094965061055795505050505050565b3461000257610529604080518082018252600381527f61646400000000000000000000000000000000000000000000000000000000006020808301919091528251808401845260018082527f2c000000000000000000000000000000000000000000000000000000000000008284015280548551601f6002600019610100858716150201909316929092049182018590048502810185019096528086526107269561055794909291908301828280156107b75780601f1061078c576101008083540402835291602001916107b7565b346100025761052b6004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f810183900483028401830190945283835297999860449892975091909101945090925082915084018382808284375094965050505050505061800060405190810160405280610400905b600081526020019060019003908161026757505092915050565b346100025760408051602080820183526000825260028054845160018216156101000260001901909116829004601f81018490048402820184019095528481526104bb94909283018282801561092f5780601f106109045761010080835404028352916020019161092f565b34610002576105296004808035906020019082018035906020019191908080601f016020809104026020016040519081016040528093929190818152602001838380828437509496505050505050508060016000509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061093957805160ff19168380011785555b50610969929150610778565b34610002576104bb6004808035906020019082018035906020019191908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965050505050505060206040519081016040528060008152602001508160006000509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061096e57805160ff19168380011785555b5061099e929150610778565b3461000257604080516020808201835260008083528054845160026001831615610100026000190190921691909104601f81018490048402820184019095528481526104bb94909283018282801561092f5780601f106109045761010080835404028352916020019161092f565b60405180806020018281038252838181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f16801561051b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b005b60405180826180008083818460006004610c0ff15090500191505060405180910390f35b949350505050565b6020604051908101604052806000815260200150600060ec905061054f8173ffffffffffffffffffffffffffffffffffffffff1663655308438686600060405161800001526040518360e060020a0281526004018080602001806020018381038352858181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f16801561060c5780820380516001836020036101000a031916815260200191505b508381038252848181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156106655780820380516001836020036101000a031916815260200191505b5094505050505061800060405180830381600087803b156100025760325a03f11561000257505050604051806180000160405261069f9090565b6040805160208181018352600080835283519182018452808252925191929091819081908190618000908059106106d35750595b9080825280602002602001820160405280156106ea575b509450602085019350600087019250610a32848487515b60005b60208210610b0a578251845260209384019390920191601f1990910190610704565b60026000509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106108cc57805160ff19168380011785555b506108fc9291505b808211156109005760008155600101610778565b820191906000526020600020905b81548152906001019060200180831161079a57829003601f168201915b505060008054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152955091935091508301828280156108455780601f1061081a57610100808354040283529160200191610845565b820191906000526020600020905b81548152906001019060200180831161082857829003601f168201915b505050505061085390929190565b602060405190810160405280600081526020015060006000600060006000865188518a5101016040518059106108865750595b90808252806020026020018201604052801561089d575b509550855060208901945060208801935060208701925060208601915060009050610ad1818301868b51610701565b82800160010185558215610770579182015b828111156107705782518260005055916020019190600101906108de565b5050565b5090565b820191906000526020600020905b81548152906001019060200180831161091257829003601f168201915b5050505050905090565b82800160010185558215610387579182015b8281111561038757825182600050559160200191906001019061094b565b505050565b82800160010185558215610441579182015b82811115610441578251826000505591602001919060010190610980565b50506000805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529291830182828015610a265780601f106109fb57610100808354040283529160200191610a26565b820191906000526020600020905b815481529060010190602001808311610a0957829003601f168201915b50505050509050919050565b600091505b8451821015610a8457848281518110156100025760209101015160f860020a90819004027fff00000000000000000000000000000000000000000000000000000000000000161515610abc575b81604051805910610a925750595b908082528060200260200182016040528015610aa9575b5095505060208501610ac7818584610701565b600190910190610a37565b5050505050919050565b8851810190508050610ae7818301858a51610701565b8751810190508050610afd818301848951610701565b5093979650505050505050565b50905182516020929092036101000a600019018019909116911617905256";

    public static final String FUNC_EXCALL = "exCall";

    public static final String FUNC_ADD = "add";

    public static final String FUNC_EXCALL32K = "exCall32k";

    public static final String FUNC_GETC = "getC";

    public static final String FUNC_SETB = "setB";

    public static final String FUNC_SETA = "setA";

    public static final String FUNC_GETA = "getA";

    @Deprecated
    protected ExAddDemo(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ExAddDemo(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ExAddDemo(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ExAddDemo(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> exCall(String _func, String _args) {
        final Function function = new Function(FUNC_EXCALL, 
                Arrays.<Type>asList(new Utf8String(_func),
                new Utf8String(_args)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> add() {
        final Function function = new Function(
                FUNC_ADD,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<List> exCall32k(String _func, String _args) {
        final Function function = new Function(FUNC_EXCALL32K,
                Arrays.<Type>asList(new Utf8String(_func),
                new Utf8String(_args)),
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<String> getC() {
        final Function function = new Function(FUNC_GETC,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setB(String b) {
        final Function function = new Function(
                FUNC_SETB,
                Arrays.<Type>asList(new Utf8String(b)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> setA(String a) {
        final Function function = new Function(FUNC_SETA,
                Arrays.<Type>asList(new Utf8String(a)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getA() {
        final Function function = new Function(FUNC_GETA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static ExAddDemo load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ExAddDemo(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ExAddDemo load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ExAddDemo(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ExAddDemo load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ExAddDemo(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ExAddDemo load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ExAddDemo(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ExAddDemo> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ExAddDemo.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ExAddDemo> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ExAddDemo.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ExAddDemo> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ExAddDemo.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ExAddDemo> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ExAddDemo.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
