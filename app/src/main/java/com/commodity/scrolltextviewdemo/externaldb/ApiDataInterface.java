package com.commodity.scrolltextviewdemo.externaldb;

import org.json.XML;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiDataInterface {
    @GET("convertcsv.xml")
    Call<String> getData();


}
