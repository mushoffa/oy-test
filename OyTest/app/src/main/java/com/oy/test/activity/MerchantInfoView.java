package com.oy.test.activity;

import com.oy.test.model.Merchant;

/**
 * Created by mushoffa on 23/04/17.
 */

public interface MerchantInfoView {

    void onInitialize();

    void showLoading();

    void hideLoading();

    void onSuccessGetMerchant(Merchant merchant);

    void onFailedGetMerchant(Throwable throwable);
}
