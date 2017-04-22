package com.oy.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.oy.test.OyTestApplication;
import com.oy.test.R;
import com.oy.test.common.base.BaseActivity;
import com.oy.test.model.Merchant;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mushoffa on 20/04/17.
 */

public class MerchantInfoActivity extends BaseActivity implements MerchantInfoView, OnMapReadyCallback{

    @BindView(R.id.text_merchant_info_name)
    TextView merchantAddress;

    @BindView(R.id.text_merchant_info_place)
    TextView merchantName;

    @BindView(R.id.text_merchant_info_phone)
    TextView merchantPhone;

    GoogleMap googleMap;

    Merchant merchant;

    @Override
    public int getLayout(){
        return R.layout.activity_merchant_info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ((OyTestApplication) getApplication()).getComponent().inject(this);

        merchant = Parcels.unwrap(getIntent().getParcelableExtra("merchant"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public void onInitialize() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
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

    }

    @Override
    public void onFailedGetMerchant(Throwable throwable) {

    }
}
