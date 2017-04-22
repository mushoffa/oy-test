package com.oy.test.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.oy.test.activity.HomeView;
import com.oy.test.model.MerchantList;
import com.oy.test.network.MerchantService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mushoffa on 18/04/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull
    private final HomeView homeView;

    @NonNull
    private final MerchantService merchantService;

    public HomePresenter(@NonNull HomeView homeView, @NonNull MerchantService merchantService) {
        this.homeView = homeView;
        this.merchantService = merchantService;
    }

    @Override
    public void subscribe() {
        homeView.onInitialize();
        compositeDisposable.add(searchMerchantByKeyword());
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }

    public Disposable searchMerchantByKeyword() {
        return homeView.getSearchView()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .switchMap(merchantService::getMerchantByKeyword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MerchantList>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull MerchantList merchantLists) throws Exception {

                        homeView.onSuccessGetMerchantList(merchantLists);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

    }

    public void searchMerchantByKeywordAndPage(String keyword, int page) {
        compositeDisposable.add(
                merchantService.getMerchantByKeywordAndPage(keyword, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(homeView::onSuccessGetMerchantList, homeView::onFailedGetMerchantList)

        );
    }
}
