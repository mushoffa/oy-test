package com.oy.test.activity;

import com.oy.test.common.base.IBaseView;
import com.oy.test.model.MerchantList;

import io.reactivex.Observable;

/**
 * Created by mushoffa on 22/04/17.
 */

public interface HomeView extends IBaseView{

    void onInitialize();

    void enableLocation();

    Observable<String> getSearchView();

    void showLoading();

    void hideLoading();

    void onSuccessGetMerchantList(MerchantList merchantList);

    void onFailedGetMerchantList(Throwable throwable);
}
