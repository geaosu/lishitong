package com.jn.lst.utils.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.jn.lst.BuildConfig;
import com.jn.lst.base.Constants;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*

==================== 用法 ====================
==================== 用法 ====================
==================== 用法 ====================

  OkhttpUtils.getInstance()
                .setLogMsgClass(mFragment.getClass().getSimpleName())
                .setLogMsgText("获取全部单位数据")
                .setUrl(Constants.RequestUrl.getContactsDepartmentDataUrl)
                .setRequestMethod(OkhttpUtils.REQUEST_MATH_GET) // 该方法可以省略，默认是post请求
                .putParams("parentId", mId) // 如果没有参数可是省略setParams("参数名", "参数值")方法
                .setOnRequestListener(new OkhttpUtils.OnRequestListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSucc(String data) {
                        loadDataSucc(data);
                    }

                    @Override
                    public void onErr(String errMsg) {
                        loadDataErr(errMsg);
                    }
                })
                .sendRequest();
*/

public class OkhttpUtils {

    // debug开关
    private static boolean isDebug = BuildConfig.DEBUG;

    // 请求方式
    public static final String REQUEST_MATH_POST = "POST";
    public static final String REQUEST_MATH_GET = "GET";

    private static OkhttpUtils mInstance;
    // 回调监听器
    private OnRequestListener mOnRequestListener;

    // 请求回调监听器
    public interface OnRequestListener {
        // 开始
        void onStart();

        // 成功
        void onSucc(String data);

        // 失败
        void onErr(String errMsg);
    }

    // debug 过滤标识
    private String mLogMsgClass;                // 作用：过滤所有你想看的网络请求
    private String mLogMsgText;                 // 作用：过滤所有你想看的网络请求
    private String mUrl;                 // 作用：
    private String mRequestMath = REQUEST_MATH_POST;                 // 作用：

    // params
    private HashMap<String, Object> mParams;

    // 获取实例 - 默认POST请求
    public static OkhttpUtils getInstance() {
        if (mInstance == null) {
            mInstance = new OkhttpUtils();
        }
        return mInstance;
    }

    public OkhttpUtils setLogMsgClass(String logMsgClass) {
        if (logMsgClass == null || logMsgClass.length() <= 0) {
            throw new NullPointerException("logMsgClass不能为空");
        }
        mLogMsgClass = logMsgClass;
        return mInstance;
    }

    public OkhttpUtils setLogMsgText(String logMsgText) {
        if (logMsgText == null || logMsgText.length() <= 0) {
            throw new NullPointerException("logMsgText不能为空");
        }
        mLogMsgText = logMsgText;
        return mInstance;
    }

    public OkhttpUtils setUrl(String url) {
        if (url == null || url.length() <= 0) {
            throw new NullPointerException("请求地址不能为空");
        }
        mUrl = url;
        return mInstance;
    }

    public OkhttpUtils setRequestMethod(String requestMath) {
        if (requestMath == null || requestMath.length() <= 0) {
            throw new NullPointerException("请求方法不能为空");
        }
        mRequestMath = requestMath;
        return mInstance;
    }

    public OkhttpUtils putParams(String key, String value) {
        if (mParams == null) {
            mParams = new HashMap<>();
        }
        // 中文会出现乱码情况，这里需要编码
        if (true) {
            try {
                mParams.put(key, URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                mOnRequestListener.onErr("请求参数编码失败");
            }
        } else {
            mParams.put(key, value);
        }
        return mInstance;
    }

    // 设置请求参数
    public OkhttpUtils setParams(HashMap<String, Object> params) {
        if (params != null) {
            mParams = params;
        }
        return mInstance;
    }

    // 设置请求参数
    public OkhttpUtils putParams(String key, Object value) {
        if (mParams == null) {
            mParams = new HashMap<>();
        }
        // 中文会出现乱码情况，这里需要编码
        // mParams.put(key, URLEncoder.encode(value, "UTF-8"));
        mParams.put(key, value);
        return mInstance;
    }

    // 请求结果回调监听
    public OkhttpUtils setOnRequestListener(OnRequestListener listener) {
        if (listener == null) {
            throw new NullPointerException("OnRequestListener 不能为空");
        }
        mOnRequestListener = listener;
        return mInstance;
    }

    // 发送请求
    public void sendRequest() {
        mOnRequestListener.onStart();
        if (mRequestMath.equals(REQUEST_MATH_GET)) {
            // GET 请求
            // sendRequestGet();
            sendRequestGet2();
        } else {
            // POST 请求
            sendRequestPost();
        }
    }

    // 拼接get请求参数
    private void addGetParams() {
        String _getRequestParamsStr = "";
        if (mParams != null && !mParams.isEmpty()) {
            for (Map.Entry<String, Object> entry : mParams.entrySet()) {
                if (_getRequestParamsStr.length() < 1) {
                    _getRequestParamsStr = "?" + entry.getKey() + "=" + entry.getValue();
                } else {
                    _getRequestParamsStr += "&" + entry.getKey() + "=" + entry.getValue();
                }
            }
        }
        if (_getRequestParamsStr != null && _getRequestParamsStr.length() > 0) {
            mUrl = mUrl + _getRequestParamsStr;
        }
    }

    //信任所有的服务器,返回true
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    // 发送GET请求
    public void sendRequestGet() {
        if (NetworkUtils.isConnected()) {
            if (isDebug) {
                Log.d(mLogMsgClass, mLogMsgText + " ------>> url           : " + mUrl);
                if (mParams != null) {
                    Log.d(mLogMsgClass, mLogMsgText + " ------>> params   : " + mParams == null ? "" : mParams.toString());
                }
                Log.d(mLogMsgClass, mLogMsgText + " ------>> math       : " + mRequestMath);
            }
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //.addInterceptor(new LogInterceptor())//添加okhttp日志拦截器
                    .hostnameVerifier(new TrustAllHostnameVerifier())//校验名称,这个对象就是信任所有的主机,也就是信任所有https的请求
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)//连接不上是否重连,false不重连
                    .build();
            StringBuilder builder = new StringBuilder(mUrl);
            if (mParams != null && mParams.keySet().size() > 0) {
                builder.append("?");
                for (String key : mParams.keySet()) {
                    builder.append(key).append("=").append(mParams.get(key)).append("&");
                }
                builder.deleteCharAt(builder.length() - 1);
            }
            if (isDebug) {
                Log.d(mLogMsgClass, mLogMsgText + " ------>> url           : " + mUrl);
            }
            Request request = new Request.Builder()
                    .url(builder.toString()) // 请求地址
                    .addHeader("token", SPUtils.getInstance().getString(Constants.TOKEN))      // session id
                    .get()// 请求方式: POST
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    //请求失败监听: 异步请求(非主线程)
                    if (isDebug) {
                        Log.d(mLogMsgClass, mLogMsgText + " ------>> err           : " + e.getMessage());
                    }
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mOnRequestListener.onErr(e.getMessage());
                        }
                    });
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    try {
                        //请求成功监听: 异步请求(非主线程)
                        String data = response.body().string();
                        if (isDebug) {
                            Log.d(mLogMsgClass, mLogMsgText + " ------>> data        : " + data);
                        }
                        if (data == null) {
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mOnRequestListener.onErr("数据异常");
                                }
                            });
                        } else {
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mOnRequestListener.onSucc(data);
                                }
                            });
                        }
                    } catch (Exception e) {
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mOnRequestListener.onErr("数据异常");
                            }
                        });
                        if (isDebug) {
                            Log.d(mLogMsgClass, mLogMsgText + " ------>> err          : " + "数据异常");
                        }
                    }
                }
            });
        } else {
            if (isDebug) {
                Log.d(mLogMsgClass, mLogMsgText + " ------>> netErr     : " + "网络错误, 请检查网络设置");
            }
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mOnRequestListener.onErr("网络错误, 请检查网络设置");
                }
            });
        }
    }

    private void sendRequestGet2() {
        if (NetworkUtils.isConnected()) {
            String json = new Gson().toJson(mParams);
            Log.d(mLogMsgClass, mLogMsgText + " params   ----->>  " + json);
            Log.d(mLogMsgClass, mLogMsgText + " url      ----->>  " + mUrl);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //.addInterceptor(new LogInterceptor())//添加okhttp日志拦截器
                    .hostnameVerifier(new TrustAllHostnameVerifier())//校验名称,这个对象就是信任所有的主机,也就是信任所有https的请求
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)//连接不上是否重连,false不重连
                    .build();
            StringBuilder builder = new StringBuilder(mUrl);
            if (mParams != null && mParams.keySet().size() > 0) {
                builder.append("?");
                for (String key : mParams.keySet()) {
                    builder.append(key).append("=").append(mParams.get(key)).append("&");
                }
                builder.deleteCharAt(builder.length() - 1);
                mParams.clear();
            }
            Log.d(mLogMsgClass, mLogMsgText + " get url ------>>  " + builder.toString());
            Request request = new Request.Builder()
                    .url(builder.toString()) // 请求地址
                    .addHeader("token", SPUtils.getInstance().getString(Constants.TOKEN))      // session id
                    .get()// 请求方式: POST
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    //请求失败监听: 异步请求(非主线程)
                    Log.d(mLogMsgClass, mLogMsgText + " error   ------>>  " + e.getMessage());
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mOnRequestListener != null) {
                                mOnRequestListener.onErr(e.toString());
                            }
                        }
                    });
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    try {
                        //请求成功监听: 异步请求(非主线程)
                        String data = response.body().string();
                        Log.d(mLogMsgClass, mLogMsgText + " result  ------>>  " + data);
                        if (data == null) {
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mOnRequestListener != null) {
                                        mOnRequestListener.onErr("数据异常");
                                    }
                                }
                            });
                        } else {
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mOnRequestListener != null) {
                                        mOnRequestListener.onSucc(data);
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mOnRequestListener != null) {
                                    mOnRequestListener.onErr("数据异常");
                                }
                            }
                        });
                    }
                }
            });
        } else {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mOnRequestListener != null) {
                        mOnRequestListener.onErr("网络错误, 请检查网络设置");
                    }
                }
            });
        }
    }

    // 拼接Post请求参数
    private String addPostParams() {
        String paramsStr = null;
        if (mParams != null && !mParams.isEmpty()) {
            paramsStr = mParams.toString();
        }
        return paramsStr;
    }

    // 发送POST请求
    private OkhttpUtils sendRequestPost() {
        String paramsStr = addPostParams();

        if (isDebug) {
            Log.d(mLogMsgClass, mLogMsgText + " ------>> url           : " + mUrl);
            Log.d(mLogMsgClass, mLogMsgText + " ------>> params   : " + mParams == null ? "" : mParams.toString());
            Log.d(mLogMsgClass, mLogMsgText + " ------>> math       : " + mRequestMath);
        }

        // 判断请求地址是否正确
        if (mUrl != null && mUrl.length() > 0) {
            // 判断网络是否连接
            if (true) {
                // 连接网络
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mParams.clear();
                            URL url = new URL(mUrl);
                            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
                            // urlCon.setDoOutput(true);
                            urlCon.setDoInput(true);
                            urlCon.setUseCaches(false);
                            urlCon.setConnectTimeout(5 * 1000);
                            urlCon.setRequestMethod(REQUEST_MATH_POST);
                            urlCon.addRequestProperty("Content-Type", "application/json");
                            urlCon.connect();
                            OutputStream os = urlCon.getOutputStream();
                            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                            osw.write(paramsStr == null ? "" : paramsStr);
                            osw.flush();
                            InputStream is = urlCon.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            final String data = br.readLine();
                            if (data != null && data.length() > 0) {
                                if (isDebug) {
                                    Log.d(mLogMsgClass, mLogMsgText + " ------>> data        : " + data);
                                }
                                ThreadUtils.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnRequestListener.onSucc(data);
                                    }
                                });
                            } else {
                                if (isDebug) {
                                    Log.d(mLogMsgClass, mLogMsgText + " ------>> err           : " + "请求失败");
                                }
                                ThreadUtils.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnRequestListener.onErr("请求失败");
                                    }
                                });
                            }
                        } catch (final Exception e) {
                            if (isDebug) {
                                Log.d(mLogMsgClass, mLogMsgText + " ------>> error       : " + e.toString());
                            }
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mOnRequestListener.onErr(e.toString());
                                }
                            });
                        }
                    }
                }).start();
            } else {
                if (isDebug) {
                    Log.d(mLogMsgClass, mLogMsgText + " ------>> netErr      : " + "请检查网络设置");
                }
                mOnRequestListener.onErr("请检查网络设置");
            }
        } else {
            if (isDebug) {
                Log.d(mLogMsgClass, mLogMsgText + " ------>> url           : " + "请求地址错误");
            }
            mOnRequestListener.onErr("请求地址错误");
        }
        return mInstance;
    }

    // 负责线程切换
    private static class ThreadUtils {
        private static Handler mHandler = new Handler(Looper.getMainLooper());

        // 在主线程中运行
        public static void runOnUiThread(Runnable r) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                //主线程
                r.run();
            } else {
                //子线程
                mHandler.post(r);
            }
        }

        // 在子线程中运行
        public static void runOnSubThread(Runnable r) {
            new Thread(r).start();
        }

        // 是否是主线程
        public static boolean isMainThread() {
            return Looper.getMainLooper() == Looper.myLooper();
        }
    }
}
