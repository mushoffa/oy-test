package com.oy.test.presenter;

import com.oy.test.activity.HomeView;
import com.oy.test.common.base.IBasePresenter;

/**
 * Created by mushoffa on 22/04/17.
 */

public interface IHomePresenter<V extends HomeView> extends IBasePresenter<V>{

    void searchMerchantList();
}
