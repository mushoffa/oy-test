package com.oy.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.ProgressBar;

import com.google.android.gms.location.LocationSettingsRequest;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.oy.test.OyTestApplication;
import com.oy.test.R;
import com.oy.test.adapter.MerchantListAdapter;
import com.oy.test.common.base.BaseActivity;
import com.oy.test.model.Merchant;
import com.oy.test.model.MerchantList;
import com.oy.test.network.MerchantService;
import com.oy.test.presenter.HomePresenter;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsStates;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by mushoffa on 18/04/17.
 */

public class HomeActivity extends BaseActivity implements HomeView, MerchantListAdapter.OnItemClickListener, SearchView.OnCloseListener {

    @BindView(R.id.view_home_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.view_home_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.view_home_search)
    SearchView searchView;

    @Inject
    MerchantService merchantService;

    String currentQuery;

    HomePresenter presenter;

    List<Merchant> merchants;

    MerchantListAdapter merchantListAdapter;

    MerchantList merchantList;

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((OyTestApplication) getApplication()).getComponent().inject(this);
        presenter = new HomePresenter(this, merchantService);
        presenter.subscribe();
    }

    @Override
    public void onResume() {
        enableLocation();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            default:
                break;
        }
    }

    @Override
    public void onInitialize() {
        searchView.setOnCloseListener(this);

        merchants = new ArrayList<>();
        merchantListAdapter = new MerchantListAdapter(this, merchants, null, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(merchantListAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if(lastVisibleItemPosition == merchantListAdapter.getItemCount() -1){

                    if((merchantList.getPage() < merchantList.getTotalPages()) && !currentQuery.isEmpty()){
                        int nextPage = merchantList.getPage() + 1;
                        presenter.searchMerchantByKeywordAndPage(currentQuery, nextPage);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void enableLocation() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000);

        new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessGetMerchantList(MerchantList merchantList) {

        this.merchantList = merchantList;
        hideLoading();
        if(merchants.size() > 0 ) {
            merchants.addAll(merchants.size(), merchantList.getMerchants());
        } else{
            merchants.addAll(merchantList.getMerchants());
        }
        merchantListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedGetMerchantList(Throwable throwable) {
        hideLoading();
    }

    @Override
    public Observable<String> getSearchView() {
        return RxSearchView.queryTextChanges(searchView)
                .filter(query -> {
                    if(query.length() < 4){
                        merchants.clear();
                        currentQuery = "";
                        merchantListAdapter.notifyDataSetChanged();
                        return false;
                    }
                    return query.length() >= 4;
                })
                .doOnNext(charSequence -> {
                    currentQuery = charSequence.toString();
                    showLoading();})
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .debounce(400, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString);
    }

    @Override
    public void onItemClick(int position, Merchant merchant) {
        Intent intent = new Intent(this, MerchantInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("merchant", Parcels.wrap(merchant));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onClose() {
        return false;
    }
}
