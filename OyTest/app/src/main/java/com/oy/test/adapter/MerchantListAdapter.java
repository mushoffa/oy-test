package com.oy.test.adapter;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.oy.test.R;
import com.oy.test.model.Merchant;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mushoffa on 20/04/17.
 */

public class MerchantListAdapter extends RecyclerView.Adapter<MerchantListAdapter.ViewHolder>{

    private Context context;

    private List<Merchant> merchants;

    private Location currentLocation = new Location(LocationManager.GPS_PROVIDER);

    private OnItemClickListener listener;

    public MerchantListAdapter(Context context,
                               @NonNull List<Merchant> merchants,
                               @Nullable LatLng currentLocation,
                               @NonNull OnItemClickListener listener){
        this.context = context;
        this.merchants = merchants;
//        this.currentLocation.setLatitude(currentLocation.latitude);
//        this.currentLocation.setLongitude(currentLocation.longitude);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MerchantListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_merchant_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Merchant merchant = merchants.get(position);

//        Location merchantLocation = new Location(LocationManager.GPS_PROVIDER);
//        merchantLocation.setLatitude(merchant.getGeoLocation().latitude);
//        merchantLocation.setLongitude(merchant.getGeoLocation().longitude);
//        float distance = currentLocation.distanceTo(merchantLocation);

        holder.textMerchantAddress.setText(merchant.getAddress());
//        holder.textMerchantDistance.setText(String.format(Locale.ENGLISH, "%.2f km",distance));
        holder.textMerchantName.setText(merchant.getMerchantName());

        holder.itemView.setOnClickListener(view ->listener.onItemClick(position, merchant));
    }

    @Override
    public int getItemCount() {
        return merchants.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_merchant_address)
        TextView textMerchantAddress;

        @BindView(R.id.text_merchant_distance)
        TextView textMerchantDistance;

        @BindView(R.id.text_merchant_name)
        TextView textMerchantName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position, Merchant merchant);
    }
}
