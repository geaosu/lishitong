package com.jn.lst.base.net;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description: 网络请求拦截器
 */
public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "LoggerInterceptor";

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        // 拦截请求，获取到该次请求的request
        Request request = chain.request();
        // 执行本次网络请求操作，返回response信息


        Response response = chain.proceed(request);
        for (String key : request.headers().toMultimap().keySet()) {
            Log.d(TAG, "------>> header: " + key + " : " + request.headers().toMultimap().get(key));
        }
        Log.d(TAG, "------>> url: " + request.url().uri().toString());


//        ResponseBody responseBody = response.body();
//        if (HttpHeaders.hasBody(response) && responseBody != null) {
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseBody.byteStream(), "utf-8"));
//            String result;
//            while ((result = bufferedReader.readLine()) != null) {
//                Log.d(TAG,"------>> response: " + result);
//            }
//            // 测试代码
//            String string = responseBody.string();
//            Log.d(TAG,"------>> string: " + string);
//        }


        return response.newBuilder()
                // 增加一个缓存头信息，缓存时间为60s
                .header("cache-control", "public, max-age=60")
                // 移除pragma头信息
                .removeHeader("pragma")
                .build();

//        return response;
    }
}
