package com.oy.test.common.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by mushoffa on 21/04/17.
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    private final CompositeDisposable compositeDisposable;

    private V baseView;

    public BasePresenter(){
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(V baseView) {
        this.baseView = baseView;
    }

    @Override
    public void onDetach(){
        compositeDisposable.dispose();
        baseView = null;
    }

    public CompositeDisposable getCompositeDisposable(){
        return compositeDisposable;
    }

    public V getUiView(){
        return baseView;
    }
}
