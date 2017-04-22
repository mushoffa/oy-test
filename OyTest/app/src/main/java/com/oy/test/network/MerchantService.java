package com.oy.test.network;

import com.oy.test.model.Merchant;
import com.oy.test.model.MerchantList;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mushoffa on 19/04/17.
 */

public interface MerchantService {

    @GET("api/v1/merchants")
    Observable<MerchantList> getMerchantByKeyword(@Query("keywords") String keyword);

    @GET("api/v1/merchants")
    Observable<List<MerchantList>> getMerchantByKeywordAndPage(@Query("keyword") String keyword,
                                                               @Query("page") int page);

    @GET("api/v1/merchant/")
    Observable<Merchant> getMerchantProfile(@Query("merchant_id") String merchantId);
}
