package com.oy.test.common.base;

/**
 * Created by mushoffa on 22/04/17.
 */

public interface IBasePresenter<V extends IBaseView> {

    void onAttach(V baseView);

    void onDetach();
}
