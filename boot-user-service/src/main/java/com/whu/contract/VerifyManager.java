package com.whu.contract;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
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

import java.math.BigInteger;
import java.util.ArrayList;
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
public class VerifyManager extends Contract {
    private static final String BINARY = "6060604052610afe806100126000396000f36060604052361561006c5760e060020a600035046310ddd81e81146100715780631b206c501461029d5780634c128e85146103305780635c5d625e146104f45780636553084314610574578063aeed8c201461062d578063bb9c6c3e146106a6578063de2927891461072c575b610002565b3461000257604080516020600460443581810135601f81018490048402850184019095528484526107b194823594602480359560649492939190920191819084018382808284375050604080516020608435808b0135601f810183900483028401830190945283835297999835989760a4975091955060249190910193509091508190840183828082843750506040805196358089013560208181028a81018201909452818a5297999860c498909750602492909201955093508392508501908490808284375094965050505050505060007f7a7e2c206372f5ff70b41ed2d0e3bf84f0986c30300f105eb9c56a13b300c4ee87878787878760405180878152602001866000191681526020018060200185600160a060020a0316815260200180602001806020018481038452888181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156101f15780820380516001836020036101000a031916815260200191505b508481038352868181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f16801561024a5780820380516001836020036101000a031916815260200191505b508481038252858181518152602001915080519060200190602002808383829060006004602084601f0104600302600f01f150905001995050505050505050505060405180910390a19695505050505050565b34610002576107c56004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f81018390048302840183019094528383529799986044989297509190910194509092508291508401838280828437509496506103b995505050505050565b34610002576108336004808035906020019082018035906020019191908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965050505050505060408051808201909152600881527f67656e50726f6f6600000000000000000000000000000000000000000000000060208201526108e890825b6020604051908101604052806000815260200150600060ec905061085981600160a060020a031663655308438686600060405161800001526040518360e060020a0281526004018080602001806020018381038352858181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156104615780820380516001836020036101000a031916815260200191505b508381038252848181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156104ba5780820380516001836020036101000a031916815260200191505b5094505050505061800060405180830381600087803b156100025760325a03f1156100025750505060405180618000016040526108619090565b34610002576040805160208082018352600080835233600160a060020a031681528082528390208054845160026001831615610100026000190190921691909104601f81018490048402820184019095528481526107c59490928301828280156109c55780601f1061099a576101008083540402835291602001916109c5565b34610002576108356004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750506040805160208835808b0135601f810183900483028401830190945283835297999860449892975091909101945090925082915084018382808284375094965050505050505061800060405190810160405280610400905b600081526020019060019003908161061357505092915050565b34610002576108336004808035906020019082018035906020019191908080601f01602080910402602001604051908101604052809392919081815260200183838082843750949650505050505050604080518082019091526006815260d060020a657665726966790260208201526109cf90826103b9565b34610002576107c56004808035906020019082018035906020019191908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965050505050505060408051602081810183526000825282518084019093526006835260d060020a65766572696679029083015290610a3a90836103b9565b34610002576040805160208082018352600080835233600160a060020a0316815260018083529084902080548551600293821615610100026000190190911692909204601f81018490048402830184019095528482526107c59491929091908301828280156109c55780601f1061099a576101008083540402835291602001916109c5565b604080519115158252519081900360200190f35b60405180806020018281038252838181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156108255780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b005b60405180826180008083818460006004610c0ff15090500191505060405180910390f35b949350505050565b6040805160208181018352600080835283519182018452808252925191929091819081908190618000908059106108955750595b9080825280602002602001820160405280156108ac575b509450602085019350600087019250610a40848487515b60005b60208210610adf578251845260209384019390920191601f19909101906108c6565b600160a060020a033316600090815260208181526040822083518154828552938390209194601f6002600019610100600189161502019096169590950485018490048301949193019083901061096157805160ff19168380011785555b506109919291505b80821115610996576000815560010161094d565b82800160010185558215610945579182015b82811115610945578251826000505591602001919060010190610973565b505050565b5090565b820191906000526020600020905b8154815290600101906020018083116109a857829003601f168201915b5050505050905090565b6001600050600033600160a060020a031681526020019081526020016000206000509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061096157805160ff1916838001178555610945565b92915050565b600091505b8451821015610a9257848281518110156100025760209101015160f860020a90819004027fff00000000000000000000000000000000000000000000000000000000000000161515610aca575b81604051805910610aa05750595b908082528060200260200182016040528015610ab7575b5095505060208501610ad58185846108c3565b600190910190610a45565b5050505050919050565b50905182516020929092036101000a600019018019909116911617905256";

    public static final String FUNC_LOGPROOF = "logProof";

    public static final String FUNC_EXCALL = "exCall";

    public static final String FUNC_GENPROOF = "genProof";

    public static final String FUNC_GETPROOF = "getProof";

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

    public RemoteCall<TransactionReceipt> logProof(BigInteger _blockNum, byte[] _blockHash, String _uid, String _userAddr, byte[] _userSig, List<BigInteger> _ringSig) {
        final Function function = new Function(
                FUNC_LOGPROOF, 
                Arrays.<Type>asList(new Uint256(_blockNum),
                new Bytes32(_blockHash),
                new Utf8String(_uid),
                new Address(_userAddr),
                new DynamicBytes(_userSig),
                new DynamicArray<Uint256>(
                        Uint256.class,
                        org.web3j.abi.Utils.typeMap(_ringSig, Uint256.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> exCall(String _func, String _args) {
        final Function function = new Function(FUNC_EXCALL,
                Arrays.<Type>asList(new Utf8String(_func),
                new Utf8String(_args)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> genProof(String str) {
        final Function function = new Function(
                FUNC_GENPROOF,
                Arrays.<Type>asList(new Utf8String(str)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getProof() {
        final Function function = new Function(FUNC_GETPROOF,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<TransactionReceipt> verifyZKP(String str) {
        final Function function = new Function(
                FUNC_VERIFYZKP,
                Arrays.<Type>asList(new Utf8String(str)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> verify(String str) {
        final Function function = new Function(FUNC_VERIFY,
                Arrays.<Type>asList(new Utf8String(str)),
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
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGPROOF_EVENT, transactionReceipt);
        ArrayList<LogProofEventResponse> responses = new ArrayList<LogProofEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
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
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGPROOF_EVENT, log);
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
