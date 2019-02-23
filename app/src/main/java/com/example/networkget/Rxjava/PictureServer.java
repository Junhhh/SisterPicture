package com.example.networkget.Rxjava;

import com.example.networkget.GSON.Picture;
import com.example.networkget.GSON.PictureResult;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Create by 钱俊华 on 2019/1/24 0024
 */
public interface PictureServer {

    @GET("data/福利/{number}/{page}")
    Observable<PictureResult> getBeauties(@Path("number") int number, @Path("page") int page0);
}
