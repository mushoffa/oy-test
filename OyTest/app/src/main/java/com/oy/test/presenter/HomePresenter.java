package com.oy.test.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jakewharton.rxbinding2.widget.RxSearchView;
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
    private final HomeContract.View homeView;

    @NonNull
    private final MerchantService merchantService;

    public HomePresenter(@NonNull HomeContract.View homeView, @NonNull MerchantService merchantService){
        this.homeView = homeView;
        this.merchantService = merchantService;
    }

    @Override
    public void subscribe() {
        compositeDisposable.add(searchMerchantByKeyword());
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    public Disposable searchMerchantByKeyword(){
        return homeView.getSearchView()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .switchMap(merchantService::getMerchantByKeyword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MerchantList>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull MerchantList merchantLists) throws Exception {
                        Log.d("HOME PRESENTER", "Pages: " + merchantLists.getTotalPages());
                        homeView.onSuccessGetMerchantList(merchantLists);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Log.d("HOME PRESENTER", "Throwable");
                    }
                });

    }

    public void getData(){

    }
}
