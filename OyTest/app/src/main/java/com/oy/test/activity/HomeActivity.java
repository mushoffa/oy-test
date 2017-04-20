package com.oy.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.oy.test.OyTestApplication;
import com.oy.test.R;
import com.oy.test.adapter.MerchantListAdapter;
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

public class HomeActivity extends AppCompatActivity implements HomeContract.View, MerchantListAdapter.OnItemClickListener{

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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ((OyTestApplication) getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        HomePresenter presenter = new HomePresenter(this, merchantService);
        presenter.subscribe();
        onInitialize();
    }

    @Override
    public void onInitialize() {
        merchants = new ArrayList<>();
        merchantListAdapter = new MerchantListAdapter(this, merchants, null, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(merchantListAdapter);
    }

    @Override
    public void onSuccessGetMerchantList(MerchantList merchantList) {
        merchants.clear();
        merchants.addAll(merchantList.getMerchants());
        merchantListAdapter.notifyDataSetChanged();
    }

    @Override
    public Observable<String> getSearchView() {
        return RxSearchView.queryTextChanges(searchView)
                .filter(query -> query.length() >= 4)
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
}
