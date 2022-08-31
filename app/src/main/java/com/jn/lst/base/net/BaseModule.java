package com.jn.lst.base.net;

import com.blankj.utilcode.util.NetworkUtils;
import com.jn.lst.base.DataEvent;
import com.jn.lst.base.token.TokenManager;
import com.jn.lst.utils.MyLog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Des: 网络请求基类
 */
public class BaseModule {
    private static final String TAG = "BaseModule";

    private static final int CONNECT_TIMEOUT = 300;                  // 网络请求超时时间
    private static final int WRITE_TIMEOUT = 100;                    // 读取超时时长
    private static final int READ_TIMEOUT = 300;                     // 写入超时时长

    private static int CONNECTION_POOL_MAX_IDEL = 20;
    private static int CONNECTION_POOL_KEEP_ALIVE = 20;

    // 参数
    private String putParams(HashMap<String, Object> params) {
        String param = null;
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                Object value = params.get(key);
                if (sb == null) {
                    sb = new StringBuffer();
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            param = sb.toString();
        }
        return param;
    }

    // GET 请求 带参数
    public Call sendGetReqeust(String requestUrl, HashMap<String, Object> params) {
        String url = requestUrl + "?" + putParams(params);
        return sendGetReqeust(url);
    }

    // GET 请求
    public Call sendGetReqeust(String requestUrl) {
        try {
            if (NetworkUtils.isConnected()) {
                String token = TokenManager.getToken();
                ConnectionPool connectionPool = new ConnectionPool(CONNECTION_POOL_MAX_IDEL, CONNECTION_POOL_KEEP_ALIVE, TimeUnit.MINUTES);
                TrustAllManager trustAllManager = new TrustAllManager();
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectionPool(connectionPool)
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .sslSocketFactory(createTrustAllSSLFactory(trustAllManager), trustAllManager)
                        .hostnameVerifier(createTrustAllHostnameVerifier())
                        .retryOnConnectionFailure(true)
                        .build();
                Request request = new Request.Builder()
                        .url(requestUrl)
                        .addHeader("token", token)
                        .get()
                        .build();
                Call call = okHttpClient.newCall(request);
                return call;
            }
            return null;
        } catch (Exception e) {
            EventBus.getDefault().post(new DataEvent(DataEvent.Type.NET_WORK_ERR, e.toString()));
            return null;
        }
    }

    // POST 请求
    public Call sendPostRequest(String requestUrl) {
        return sendPostRequest(requestUrl, null);
    }

    // POST 请求 带参数
    public Call sendPostRequest(String requestUrl, HashMap<String, Object> mParams) {
        try {
            if (NetworkUtils.isConnected()) {
                String token = TokenManager.getToken();
                RequestBody requestBody = null;
                if (mParams != null && mParams.size() > 0) {
                    FormBody.Builder builder = new FormBody.Builder();
                    for (String key : mParams.keySet()) {
                        builder.add(key, String.valueOf(mParams.get(key)));
                    }
                    requestBody = builder.build();
                }
                ConnectionPool connectionPool = new ConnectionPool(CONNECTION_POOL_MAX_IDEL, CONNECTION_POOL_KEEP_ALIVE, TimeUnit.MINUTES);
                TrustAllManager trustAllManager = new TrustAllManager();
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectionPool(connectionPool)
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .sslSocketFactory(createTrustAllSSLFactory(trustAllManager), trustAllManager)
                        .hostnameVerifier(createTrustAllHostnameVerifier())
                        .retryOnConnectionFailure(true)
                        // .addInterceptor(new LoggerInterceptor())
                        // .addNetworkInterceptor(new LoggerInterceptor())
                        .build();
                Request request = new Request.Builder()
                        .url(requestUrl)
                        .addHeader("token", token)
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                return call;
            }
            return null;
        } catch (Exception e) {
            EventBus.getDefault().post(new DataEvent(DataEvent.Type.NET_WORK_ERR, e.toString()));
            return null;
        }
    }

    // DELETE 请求
    public Call sendDeleteReqeust(String requestUrl) {
        try {
            if (NetworkUtils.isConnected()) {
                ConnectionPool connectionPool = new ConnectionPool(CONNECTION_POOL_MAX_IDEL, CONNECTION_POOL_KEEP_ALIVE, TimeUnit.MINUTES);
                TrustAllManager trustAllManager = new TrustAllManager();
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectionPool(connectionPool)
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .sslSocketFactory(createTrustAllSSLFactory(trustAllManager), trustAllManager)
                        .hostnameVerifier(createTrustAllHostnameVerifier())
                        .retryOnConnectionFailure(true)
                        .build();
                Request request = new Request.Builder()
                        .url(requestUrl)
                        .addHeader("token", TokenManager.getToken())
                        .delete()
                        .build();
                Call call = okHttpClient.newCall(request);
                return call;
            }
            return null;
        } catch (Exception e) {
            EventBus.getDefault().post(new DataEvent(DataEvent.Type.NET_WORK_ERR, e.toString()));
            return null;
        }
    }

    // DELETE 请求 带参数
    public Call sendDeleteReqeust(String requestUrl, HashMap<String, Object> params) {
        try {
            if (NetworkUtils.isConnected()) {
                FormBody.Builder builder = new FormBody.Builder();
                for (String key : params.keySet()) {
                    //追加表单信息
                    builder.add(key, String.valueOf(params.get(key)));
                }
                RequestBody requestBody = builder.build();

                ConnectionPool connectionPool = new ConnectionPool(CONNECTION_POOL_MAX_IDEL, CONNECTION_POOL_KEEP_ALIVE, TimeUnit.MINUTES);
                TrustAllManager trustAllManager = new TrustAllManager();
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectionPool(connectionPool)
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .sslSocketFactory(createTrustAllSSLFactory(trustAllManager), trustAllManager)
                        .hostnameVerifier(createTrustAllHostnameVerifier())
                        .retryOnConnectionFailure(true)
                        .build();
                Request request = new Request.Builder()
                        .url(requestUrl)
                        .addHeader("token", TokenManager.getToken())
                        .delete(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                return call;
            }
            return null;
        } catch (Exception e) {
            EventBus.getDefault().post(new DataEvent(DataEvent.Type.NET_WORK_ERR, e.toString()));
            return null;
        }
    }

    /***
     * 上传文件
     */
    public Call sendUpLoadFileRequest(String requestUrl, File file) {
        try {
            if (NetworkUtils.isConnected()) {
                ConnectionPool connectionPool = new ConnectionPool(CONNECTION_POOL_MAX_IDEL, CONNECTION_POOL_KEEP_ALIVE, TimeUnit.MINUTES);
                TrustAllManager trustAllManager = new TrustAllManager();
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectionPool(connectionPool)
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .sslSocketFactory(createTrustAllSSLFactory(trustAllManager), trustAllManager)
                        .hostnameVerifier(createTrustAllHostnameVerifier())
                        .retryOnConnectionFailure(true)
                        .build();
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), fileBody)
                        .build();
                Request request = new Request.Builder()
                        .url(requestUrl)
                        .addHeader("token", TokenManager.getToken())
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                return call;
            }
            return null;
        } catch (Exception e) {
            EventBus.getDefault().post(new DataEvent(DataEvent.Type.NET_WORK_ERR, e.toString()));
            return null;
        }
    }

    protected SSLSocketFactory createTrustAllSSLFactory(TrustAllManager trustAllManager) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustAllManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return ssfFactory;
    }

    // 获取HostnameVerifier
    protected HostnameVerifier createTrustAllHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    protected MyLog mMyLog = new MyLog(this.getClass().getSimpleName());

}
