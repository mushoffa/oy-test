package com.oy.test.presenter;

import android.support.annotation.NonNull;

import com.oy.test.activity.MerchantInfoView;
import com.oy.test.network.MerchantService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mushoffa on 23/04/17.
 */

public class MerchantInfoPresenter implements BasePresenter{

    private CompositeDisposable compositeDisposable;

    @NonNull
    private MerchantInfoView merchantInfoView;

    @NonNull
    private MerchantService merchantService;

    public MerchantInfoPresenter(@NonNull MerchantInfoView merchantInfoView,
                                 @NonNull MerchantService merchantService){
        this.merchantInfoView = merchantInfoView;
        this.merchantService = merchantService;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        merchantInfoView.onInitialize();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }

    public void getMerchantInfo(String merchantId){
        compositeDisposable.add(
                merchantService.getMerchantProfile(merchantId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(merchantInfoView::onSuccessGetMerchant, merchantInfoView::onFailedGetMerchant)
        );
    }
}
