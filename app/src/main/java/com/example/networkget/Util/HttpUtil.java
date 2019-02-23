package com.example.networkget.Util;


import android.widget.Toast;

import com.example.networkget.GSON.Picture;
import com.example.networkget.GSON.PictureResult;
import com.example.networkget.Rxjava.PictureServer;

import org.reactivestreams.Subscription;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by 钱俊华 on 2018/12/28 0028
 */
public class HttpUtil {

    /**
                     HttpURLConnection
     */
    public static String sendHttpURLConnectionRequest(String url) throws Exception {
        URL url1 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        if(conn.getResponseCode() == 200) {
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            byte[] data = out.toByteArray();
            String html = new String(data, "UTF-8");
            in.close();
            conn.disconnect();
            return html;
        }
        return null;
    }

    /**
     *                       OkHttp
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }


    /**
     * Retrofit + Rxjava + OkHttp 的网络框架
     *
     */

    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static Observable<PictureResult> sendRRORequest(String address,int number,int page){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(address)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        PictureServer pictureServer = retrofit.create(PictureServer.class);
        Observable<PictureResult> oPicture = pictureServer.getBeauties(number,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return oPicture;
    }



}
