package com.whu.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticArray;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class VerifyManager extends Contract {
    private static final String BINARY = "606060405261061b806100126000396000f3606060405260e060020a60003504631b206c50811461004a57806365530843146100dd578063aeed8c2014610196578063bb9c6c3e14610196578063de29278914610374575b610002565b34610002576104016004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f810183900483028401830190945283835297999860449892975091909101945090925082915084018382808284375094965061022c95505050505050565b346100025761046f6004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f810183900483028401830190945283835297999860449892975091909101945090925082915084018382808284375094965050505050505061800060405190810160405280610400905b600081526020019060019003908161017c57505092915050565b34610002576104016004808035906020019082018035906020019191908080601f016020809104026020016040519081016040528093929190818152602001838380828437509496505050505050506040805160208181018352600082528251808401909352600683527f7665726966790000000000000000000000000000000000000000000000000000908301529061052290835b6020604051908101604052806000815260200150600060ec90506104938173ffffffffffffffffffffffffffffffffffffffff1663655308438686600060405161800001526040518360e060020a0281526004018080602001806020018381038352858181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156102e15780820380516001836020036101000a031916815260200191505b508381038252848181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f16801561033a5780820380516001836020036101000a031916815260200191505b5094505050505061800060405180830381600087803b156100025760325a03f11561000257505050604051806180000160405261049b9090565b3461000257604080516020808201835260008083523373ffffffffffffffffffffffffffffffffffffffff1681528082528390208054845160026001831615610100026000190190921691909104601f81018490048402820184019095528481526104019490928301828280156105535780601f1061052857610100808354040283529160200191610553565b60405180806020018281038252838181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156104615780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60405180826180008083818460006004610c0ff15090500191505060405180910390f35b949350505050565b6040805160208181018352600080835283519182018452808252925191929091819081908190618000908059106104cf5750595b9080825280602002602001820160405280156104e6575b50945060208501935060008701925061055d848487515b60005b602082106105fc578251845260209384019390920191601f1990910190610500565b92915050565b820191906000526020600020905b81548152906001019060200180831161053657829003601f168201915b5050505050905090565b600091505b84518210156105af57848281518110156100025760209101015160f860020a90819004027fff000000000000000000000000000000000000000000000000000000000000001615156105e7575b816040518059106105bd5750595b9080825280602002602001820160405280156105d4575b50955050602085016105f28185846104fd565b600190910190610562565b5050505050919050565b50905182516020929092036101000a600019018019909116911617905256";

    public static final String FUNC_EXCALL = "exCall";

    public static final String FUNC_EXCALL32K = "exCall32k";

    public static final String FUNC_VERIFYZKP = "verifyZKP";

    public static final String FUNC_VERIFY = "verify";

    public static final String FUNC_GETRESULT = "getResult";

    public static final Event LOGPROOF_EVENT = new Event("LogProof", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
    ;

    @Deprecated
    protected VerifyManager(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VerifyManager(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VerifyManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VerifyManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> exCall(String _func, String _args) {
        final Function function = new Function(FUNC_EXCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_func), 
                new org.web3j.abi.datatypes.Utf8String(_args)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<List> exCall32k(String _func, String _args) {
        final Function function = new Function(FUNC_EXCALL32K, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_func), 
                new org.web3j.abi.datatypes.Utf8String(_args)), 
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

    public RemoteCall<String> verifyZKP(String str) {
        final Function function = new Function(FUNC_VERIFYZKP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(str)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> verify(String str) {
        final Function function = new Function(FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(str)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getResult() {
        final Function function = new Function(FUNC_GETRESULT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<LogProofEventResponse> getLogProofEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGPROOF_EVENT, transactionReceipt);
        ArrayList<LogProofEventResponse> responses = new ArrayList<LogProofEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogProofEventResponse typedResponse = new LogProofEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._blockNum = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._blockHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._uuid = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._userAddr = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._userSig = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse._ringSig = (List<BigInteger>) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogProofEventResponse> logProofEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, LogProofEventResponse>() {
            @Override
            public LogProofEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGPROOF_EVENT, log);
                LogProofEventResponse typedResponse = new LogProofEventResponse();
                typedResponse.log = log;
                typedResponse._blockNum = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._blockHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._uuid = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._userAddr = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._userSig = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse._ringSig = (List<BigInteger>) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogProofEventResponse> logProofEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGPROOF_EVENT));
        return logProofEventFlowable(filter);
    }

    @Deprecated
    public static VerifyManager load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new VerifyManager(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VerifyManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VerifyManager(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VerifyManager load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new VerifyManager(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VerifyManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VerifyManager(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<VerifyManager> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VerifyManager.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VerifyManager> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VerifyManager.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<VerifyManager> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VerifyManager.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VerifyManager> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VerifyManager.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class LogProofEventResponse {
        public Log log;

        public BigInteger _blockNum;

        public byte[] _blockHash;

        public String _uuid;

        public String _userAddr;

        public byte[] _userSig;

        public List<BigInteger> _ringSig;
    }
}
