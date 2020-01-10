package com.whu.contract.singleton;

import com.whu.contract.*;
import com.whu.tools.Constant;

import java.io.IOException;


public class ContractApi {

    public GeoHashCon geoHashCon;
    public UserManager userManager;
    public VerifyManager verifyManager;
    private ContractApi() {

        geoHashCon = GeoHashCon.load(Constant.geoHashConContractAddress,
                EthereumApi.getInstance().web3j,
                EthereumApi.getInstance().transactionManager,
                EthereumApi.getInstance().contractGasProvider
        );
        userManager = UserManager.load(Constant.userManagerContractAddress,
                EthereumApi.getInstance().web3j,
                EthereumApi.getInstance().transactionManager,
                EthereumApi.getInstance().contractGasProvider);

        verifyManager = VerifyManager.load(Constant.verifyManagerContractAddress,
                EthereumApi.getInstance().web3j,
                EthereumApi.getInstance().transactionManager,
                EthereumApi.getInstance().contractGasProvider);


    }
    /**
     * 静态内部类单例模式
     * @returnb
     */
    public static ContractApi getInstance(){
        return ContractApi.Inner.instance;
    }



    private static class Inner {
        private static final ContractApi instance = new ContractApi();
    }
}
