package com.oy.test.presenter;

import android.widget.SearchView;

import com.oy.test.model.MerchantList;

import io.reactivex.Observable;

/**
 * Created by mushoffa on 20/04/17.
 */

public interface HomeContract {

    interface View{
        void onInitialize();

        void onSuccessGetMerchantList(MerchantList merchantList);

        Observable<String> getSearchView();
    }

    interface Presenter extends BasePresenter{


    }
}
