package com.oy.test.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oy.test.OyTestApplication;
import com.oy.test.R;
import com.oy.test.common.base.BaseActivity;
import com.oy.test.model.Merchant;
import com.oy.test.network.MerchantService;
import com.oy.test.presenter.MerchantInfoPresenter;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by mushoffa on 20/04/17.
 */

public class MerchantInfoActivity extends BaseActivity implements MerchantInfoView, OnMapReadyCallback {

    @BindView(R.id.text_merchant_info_name)
    TextView merchantAddress;

    @BindView(R.id.text_merchant_info_place)
    TextView merchantName;

    @BindView(R.id.text_merchant_info_phone)
    TextView merchantPhone;

    @Inject
    MerchantService merchantService;

    GoogleMap googleMap;

    Merchant merchant;

    MerchantInfoPresenter merchantInfoPresenter;

    @Override
    public int getLayout() {
        return R.layout.activity_merchant_info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((OyTestApplication) getApplication()).getComponent().inject(this);
        merchantInfoPresenter = new MerchantInfoPresenter(this, merchantService);
        merchantInfoPresenter.subscribe();

        merchant = Parcels.unwrap(getIntent().getParcelableExtra("merchant"));
        merchantInfoPresenter.getMerchantInfo(merchant.getMerchantId());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public void onInitialize() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onSuccessGetMerchant(Merchant merchant) {
        merchantAddress.setText(merchant.getAddress());
        merchantName.setText(merchant.getMerchantName());
        merchantPhone.setText(merchant.getPhone());

        googleMap.addMarker(new MarkerOptions()
                .position(merchant.getGeoLocation())
                .icon(BitmapDescriptorFactory.defaultMarker()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(merchant.getGeoLocation(), 14));
    }

    @Override
    public void onFailedGetMerchant(Throwable throwable) {
        throwable.printStackTrace();
    }
}
