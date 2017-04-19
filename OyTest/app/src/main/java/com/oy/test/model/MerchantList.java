package com.oy.test.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by mushoffa on 19/04/17.
 */

@Parcel
public class MerchantList {

    @SerializedName("merchants")
    List<Merchant> merchants;

    @SerializedName("page")
    int page;

    @SerializedName("results_per_page")
    int resultsPerPage;

    @SerializedName("total_pages")
    int totalPages;

    public List<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
