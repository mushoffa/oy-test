package com.oy.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.oy.test.OyTestApplication;
import com.oy.test.R;
import com.oy.test.adapter.MerchantListAdapter;
import com.oy.test.common.base.BaseActivity;
import com.oy.test.model.Merchant;
import com.oy.test.model.MerchantList;
import com.oy.test.network.MerchantService;
import com.oy.test.presenter.HomeContract;
import com.oy.test.presenter.HomePresenter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by mushoffa on 18/04/17.
 */

public class HomeActivity extends BaseActivity implements HomeView, MerchantListAdapter.OnItemClickListener, SearchView.OnCloseListener{

    @BindView(R.id.view_home_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.view_home_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.view_home_search)
    SearchView searchView;

    @Inject
    MerchantService merchantService;

    List<Merchant> merchants;

    MerchantListAdapter merchantListAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ((OyTestApplication) getApplication()).getComponent().inject(this);
        HomePresenter presenter = new HomePresenter(this, merchantService);
        presenter.subscribe();
    }

    @Override
    public void onInitialize() {
        searchView.setOnCloseListener(this);

        merchants = new ArrayList<>();
        merchantListAdapter = new MerchantListAdapter(this, merchants, null, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(merchantListAdapter);
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
        hideLoading();
        merchants.clear();
        merchants.addAll(merchantList.getMerchants());
        merchantListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedGetMerchantList(Throwable throwable) {
        hideLoading();
    }

    @Override
    public Observable<String> getSearchView() {
        return RxSearchView.queryTextChanges(searchView)
                .filter(query -> query.length() >= 4)
                .doOnNext(charSequence -> showLoading())
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
        Log.d(TAG, "Clear search view");
        return false;
    }
}
