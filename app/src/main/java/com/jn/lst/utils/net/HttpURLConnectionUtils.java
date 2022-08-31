package com.jn.lst.utils.net;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.jn.lst.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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

    /*
    =========================== 该工具类的用法 ===========================
    =========================== 该工具类的用法 ===========================
    =========================== 该工具类的用法 ===========================

        // GET 请求 - 如果没有参数可是省略setParams("参数名", "参数值")方法
        String url = SPUtils.getInstance().getString(Constants.NET_BASE_URL)+UrlManager.RequestUrl.commonWebsUrl;
        HttpURLConnectionUtils.getInstance(mFragment.getClass().getSimpleName(), "常用网站")
            .setUrl(url)
            .get() // 该方法可以省略，默认是post请求
            .setParams("参数名", "参数值") // 如果没有参数可是省略setParams("参数名", "参数值")方法
            .setParams("参数名", "参数值")
            .setParams("参数名", "参数值")
            .setRequestListener(new HttpURLConnectionUtils.OnHttpURLConnectionRequestListener() {
                @Override
                public void onResult(String data) {
                    // 回调函数在主线程中执行
                    tvLog.append("\n\n" + data);
                }

                @Override
                public void onError(int errCode, String errMsg) {
                    // 回调函数在主线程中执行
                    tvLog.append("\n\nerrCode: " + errCode + "\nerrMsg: " + errMsg);
                }
            })
            .sendRequest();


        // POST 请求 - 如果没有参数可是省略setParams("参数名", "参数值")方法
        String url = SPUtils.getInstance().getString(Constants.NET_BASE_URL)+UrlManager.RequestUrl.commonWebsUrl;
        HttpURLConnectionUtils.getInstance(mFragment.getClass().getSimpleName(), "常用网站")
            .setUrl(url)
            .post() // 该方法可以省略，默认是post请求
            .setParams("参数名", "参数值") // 如果没有参数可是省略setParams("参数名", "参数值")方法
            .setParams("参数名", "参数值")
            .setParams("参数名", "参数值")
            .setRequestListener(new HttpURLConnectionUtils.OnHttpURLConnectionRequestListener() {
                @Override
                public void onResult(String data) {
                    // 回调函数在主线程中执行
                    tvLog.append("\n\n" + data);
                }

                @Override
                public void onError(int errCode, String errMsg) {
                    // 回调函数在主线程中执行
                    tvLog.append("\n\nerrCode: " + errCode + "\nerrMsg: " + errMsg);
                }
            })
            .sendRequest();


        // 下载文件
        String url = SPUtils.getInstance().getString(Constants.NET_BASE_URL)+UrlManager.RequestUrl.smartisanBtUrl;
        HttpURLConnectionUtils.getInstance(mActivity.getClass().getSimpleName(), "下载锤子便签")
            .setUrl(url)
            .setFileName("锤子便签.apk")
            .setOnDownloadListener(new HttpURLConnectionUtils.OnDownloadListener() {
                @Override
                public void onStart() {
                    // 回调函数在主线程中执行
                    tvTestHttpGet.setEnabled(false);
                }

                @Override
                public void onProgress(String fileTotalSize, int progress, String downloadSpeed) {
                    // 回调函数在主线程中执行
                    tvLog.setText(progress + "%");
                }

                @Override
                public void onFinish(File file) {
                    // 回调函数在主线程中执行
                    tvTestHttpGet.setEnabled(true);
                }

                @Override
                public void onError(String errMsg) {
                    // 回调函数在主线程中执行
                    tvTestHttpGet.setEnabled(true);
                }
            });


        // 上传文件
        ==================================== 上传文件 暂时没实现 ==========================================
        ==================================== 上传文件 暂时没实现 ==========================================
        ==================================== 上传文件 暂时没实现 ==========================================

        String url =SPUtils.getInstance().getString(Constants.NET_BASE_URL)+ UrlManager.RequestUrl.smartisanBtUrl;
        HttpURLConnectionUtils.getInstance(mActivity.getClass().getSimpleName(), "下载锤子便签")
            .setUrl(url)
            .setParams("", file)
            .setFileName("锤子便签.apk")
            .setOnUploadListener(new HttpURLConnectionUtils.OnUploadListener() {
                @Override
                public void onStart() {
                    // 回调函数在主线程中执行
                    tvTestHttpGet.setEnabled(false);
                }

                @Override
                public void onProgress(String fileTotalSize, int progress, String downloadSpeed) {
                    // 回调函数在主线程中执行
                    tvLog.setText(progress + "%");
                }

                @Override
                public void onFinish(File file) {
                    // 回调函数在主线程中执行
                    tvTestHttpGet.setEnabled(true);
                }

                @Override
                public void onError(String errMsg) {
                    // 回调函数在主线程中执行
                    tvTestHttpGet.setEnabled(true);
                }
            });


    =========================== 该工具类的用法 ===========================
    =========================== 该工具类的用法 ===========================
    =========================== 该工具类的用法 ===========================*/

/**
 * HttpURLConnection 请求工具类， 支持post，get请求，支持请求回调监听(回调函数在主线程中运行)
 */
public class HttpURLConnectionUtils {

    // debug开关
    private static boolean isDebug = BuildConfig.DEBUG;

    // 请求错误码
    public static final int ERR_CODE_JAVA = 1;              // 操作失败
    public static final int ERR_CODE_NET_WORK = 2;          // 网络连接失败
    public static final int ERR_CODE_URL = 3;               // 请求地址错误
    public static final int ERR_CODE_PARAMS = 4;            // 请求参数错误
    public static final int ERR_CODE_RESULT = 5;            // 服务器返回数据错误
    public static final int ERR_CODE_LISTENER = 6;          // 回调监听器错误
    public static final int ERR_CODE_DEBUG = 7;             // DebugMsg错误
    public static final int ERR_CODE_DEBUG_TAG = 8;         // DebugTag错误
    public static final int ERR_CODE_CLASS = 9;             // Class错误
    public static final int ERR_CODE_REQUEST_METHOD = 10;   // 请求方式错误
    public static final int ERR_CODE_URL_ENCODER = 11;      // URL编码错误
    public static final int ERR_CODE_HTTP_CONNECTION = 12;  // Http请求连接错误
    public static final int ERR_CODE_DIR_PATH = 13;         // 文件夹路径错误
    public static final int ERR_CODE_FILE_NAME = 14;        // 文件名称错误

    // 请求错误码信息
    public static final String ERR_MSG_JAVA = "code is err";
    public static final String ERR_MSG_NET_WORK = "Network is err";
    public static final String ERR_MSG_URL = "Request url is a null object";
    public static final String ERR_MSG_PARAMS = "Request params name is a null object";
    public static final String ERR_MSG_RESULT = "Result data is a null object";
    public static final String ERR_MSG_LISTENER = "OnHttpURLConnectionRequestListener is a null object";
    public static final String ERR_MSG_DEBUG = "getInstance()方法参数错误: Debug Msg is a null object";
    public static final String ERR_MSG_DEBUG_TAG = "Debug Tag is a null object";
    public static final String ERR_MSG_CLASS = "getInstance()方法参数错误: Current Class is a null object";
    public static final String ERR_MSG_REQUEST_METHOD = "getInstance()方法参数错误: Request type is a null object";
    public static final String ERR_MSG_URL_ENCODER = "URL Encoder err";
    public static final String ERR_MSGE_HTTP_CONNECTION = "HTTP Connection err";
    public static final String ERR_MSG_DIR_PATH = "Folder path err";
    public static final String ERR_MSG_FILE_NAME = "File Name is err";
    public static final String ERR_MSG_DOWNLOAD_LISTENER = "OnDownloadListener is a null object";
    public static final String ERR_MSG_UPLOAD_LISTENER = "OnUploadListener is a null object";

    // 请求方式
    public static final String REQUEST_TYPE_POST = "POST";
    public static final String REQUEST_TYPE_GET = "GET";
    private static String REQUEST_TYPE_DEFAULT = REQUEST_TYPE_POST;

    // debug 过滤标识
    private static String mCurrentClass;                // 作用：过滤所有你想看的网络请求
    private static final String mDebugTag = "GSLog";    // 作用：过滤所有网络请求
    private static String mDebugMsg;                    // 作用：过滤所有你想看的网络请求

    // 回调监听器
    private OnRequestListener mOnRequestListener;
    private OnDownloadListener mOnDownloadListener;

    // 全局变量
    private static HttpURLConnectionUtils mInstance;
    private String mUrl = "";
    private String mGetRequestParamsStr = "";
    private String mPostRequestParamsStr = "";
    private String mFileName;
    private final String mDefaultDriPath = "/sdcard/Download/";
    private String mDriPath = mDefaultDriPath;
    private HashMap<String, String> mParams = new HashMap<>();

    // 请求回调监听器
    public interface OnRequestListener {
        void onStart();

        void onResult(String data);

        void onError(int errCode, String errMsg);
    }

    // 请求回调监听器，所有回调函数都运行在主线程
    public interface OnDownloadListener {
        // 开始下载
        void onStart();

        // 正在下载
        void onProgress(String fileTotalSize, int progress, String downloadSpeed);

        // 下载完成
        void onFinish(File file);

        // 下载失败
        void onError(String errMsg);
    }

    // 请求回调监听器，所有回调函数都运行在主线程
    public interface OnUploadListener {
        // 开始下载
        void onStart();

        // 正在下载
        void onProgress(String fileTotalSize, int progress, String uploadSpeed);

        // 下载完成
        void onFinish(File file);

        // 下载失败
        void onError(String errMsg);
    }

    // 获取实例 - 默认POST请求
    public static HttpURLConnectionUtils getInstance(String currentClass, String debugMsg) {
        if (currentClass == null || currentClass.length() <= 0) {
            throw new NullPointerException(ERR_MSG_DEBUG);
        }
        if (debugMsg == null || debugMsg.length() <= 0) {
            throw new NullPointerException(ERR_MSG_DEBUG);
        }
        mCurrentClass = currentClass;
        mDebugMsg = debugMsg;
        REQUEST_TYPE_DEFAULT = REQUEST_TYPE_POST;
        if (mInstance == null) {
            mInstance = new HttpURLConnectionUtils();
        }
        return mInstance;
    }

    // 恢复默认设置
    private void restoreDefaultSet() {
        mUrl = "";
        mPostRequestParamsStr = "";
        mParams.clear();
        mFileName = "";
        REQUEST_TYPE_DEFAULT = REQUEST_TYPE_POST;
    }

    // 请求地址
    public HttpURLConnectionUtils setUrl(String url) {
        restoreDefaultSet();
        if (url == null || url.length() <= 0) {
            throw new NullPointerException(ERR_MSG_URL);
        }
        mUrl = url;
        return mInstance;
    }

    // 设置请求参数
    public HttpURLConnectionUtils setParams(String key, String value) {
        if (mParams == null) {
            mParams = new HashMap<>();
        }
        // 中文会出现乱码情况，这里需要编码
        if (true) {
            try {
                mParams.put(key, URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                mOnRequestListener.onError(ERR_CODE_URL_ENCODER, ERR_MSG_URL_ENCODER);
            }
        } else {
            mParams.put(key, value);
        }
        return mInstance;
    }

    // 拼接get请求参数
    private void addGetParams() {
        String _getRequestParamsStr = "";
        if (mParams != null && !mParams.isEmpty()) {
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
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

    // 拼接Post请求参数
    private void addPostParams() {
        if (mParams != null && !mParams.isEmpty()) {
            mPostRequestParamsStr = mParams.toString();
        }
    }

    // 设置请求方式 - get
    public HttpURLConnectionUtils get() {
        REQUEST_TYPE_DEFAULT = REQUEST_TYPE_GET;
        return mInstance;
    }

    // 设置请求方式 - post
    public HttpURLConnectionUtils post() {
        REQUEST_TYPE_DEFAULT = REQUEST_TYPE_POST;
        return mInstance;
    }

    // 请求结果回调监听
    public HttpURLConnectionUtils setOnRequestListener(OnRequestListener listener) {
        if (listener == null) {
            throw new NullPointerException(ERR_MSG_LISTENER);
        }
        mOnRequestListener = listener;
        return mInstance;
    }

    // 发送请求
    public void sendRequest() {
        mOnRequestListener.onStart();
        if (REQUEST_TYPE_DEFAULT.equals(REQUEST_TYPE_GET)) {
            // GET 请求
            sendRequestGet();
        } else {
            // POST 请求
            sendRequestPost();
        }
    }

    // 发送GET请求
    private HttpURLConnectionUtils sendRequestGet() {
        // 拼接请求参数
        addGetParams();
        if (isDebug) {
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> url      : " + mUrl);
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> params   : " + mParams.toString());
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> type     : " + REQUEST_TYPE_DEFAULT);
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
                            URL url = new URL(mUrl);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");// 清零重新赋值
                            // httpURLConnection.addRequestProperty();// 在原来key的基础上继续添加其他value
                            httpURLConnection.setRequestProperty("Content-Type", "plain/text; charset=UTF-8");
                            httpURLConnection.setRequestMethod(REQUEST_TYPE_GET);
                            httpURLConnection.setDoOutput(true);//////////////
                            httpURLConnection.setDoInput(true);//////////////
                            httpURLConnection.setConnectTimeout(40000);//////////////
                            httpURLConnection.setReadTimeout(4000);//////////////
                            httpURLConnection.setUseCaches(false);//////////////

                            httpURLConnection.connect();

                            //BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                            final String data = in.readLine();
                            in.close();
                            httpURLConnection.disconnect();

//                             urlConnection.getInputStream();
                            if (data != null && data.length() > 0) {
                                if (isDebug) {
                                    Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> result   : " + data);
                                }
                                ThreadUtils.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnRequestListener.onResult(data);
                                    }
                                });
                            } else {
                                if (isDebug) {
                                    Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : " + ERR_MSG_RESULT + ": data = " + data);
                                }
                                ThreadUtils.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnRequestListener.onError(ERR_CODE_RESULT, ERR_MSG_RESULT);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (isDebug) {
                                Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : " + ERR_MSGE_HTTP_CONNECTION + ": " + e.toString());
                            }
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mOnRequestListener.onError(ERR_CODE_HTTP_CONNECTION, ERR_MSGE_HTTP_CONNECTION + ": " + e.toString());
                                }
                            });
                        }
                    }
                }).start();
            } else {
                if (isDebug) {
                    Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : " + ERR_MSG_NET_WORK);
                }
                mOnRequestListener.onError(ERR_CODE_NET_WORK, ERR_MSG_NET_WORK);
            }
        } else {
            if (isDebug) {
                Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : " + ERR_MSG_URL);
            }
            mOnRequestListener.onError(ERR_CODE_URL, ERR_MSG_URL);
        }
        return mInstance;
    }

    // 发送POST请求
    private HttpURLConnectionUtils sendRequestPost() {
        addPostParams();

        if (isDebug) {
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> url      : " + mUrl);
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> params   : " + mParams.toString());
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> type     : " + REQUEST_TYPE_DEFAULT);
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
                            URL url = new URL(mUrl);
                            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
                            // urlCon.setDoOutput(true);
                            urlCon.setDoInput(true);
                            urlCon.setUseCaches(false);
                            urlCon.setConnectTimeout(5 * 1000);
                            urlCon.setRequestMethod(REQUEST_TYPE_POST);
                            urlCon.addRequestProperty("Content-Type", "application/json");
                            urlCon.connect();
                            OutputStream os = urlCon.getOutputStream();
                            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                            osw.write(mPostRequestParamsStr);
                            osw.flush();
                            BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
                            final String data = br.readLine();
                            if (data != null && data.length() > 0) {
                                if (isDebug) {
                                    Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> result   : " + data);
                                }
                                ThreadUtils.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnRequestListener.onResult(data);
                                    }
                                });
                            } else {
                                if (isDebug) {
                                    Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : " + ERR_MSG_RESULT + ": data = " + data);
                                }
                                ThreadUtils.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnRequestListener.onError(ERR_CODE_RESULT, ERR_MSG_RESULT);
                                    }
                                });
                            }
                        } catch (final Exception e) {
                            if (isDebug) {
                                Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : " + ERR_MSGE_HTTP_CONNECTION + ": " + e.toString());
                            }
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mOnRequestListener.onError(ERR_CODE_HTTP_CONNECTION, ERR_MSGE_HTTP_CONNECTION + ": " + e.toString());
                                }
                            });
                        }
                    }
                }).start();
            } else {
                if (isDebug) {
                    Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : " + ERR_MSG_NET_WORK);
                }
                mOnRequestListener.onError(ERR_CODE_NET_WORK, ERR_MSG_NET_WORK);
            }
        } else {
            if (isDebug) {
                Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : " + ERR_MSG_URL);
            }
            mOnRequestListener.onError(ERR_CODE_URL, ERR_MSG_URL);
        }
        return mInstance;
    }

    // 上传文件
    public void uploadFile(OnUploadListener listener) {
        if (isDebug) {
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> url      : " + mUrl);
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> params   : " + mParams.toString());
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> driPath  : " + mDriPath);
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> fileName : " + mFileName);
        }

        if (TextUtils.isEmpty(mUrl)) {
            listener.onError(ERR_MSG_URL);
            return;
        }
        if (TextUtils.isEmpty(mDriPath)) {
            listener.onError(ERR_MSG_DIR_PATH);
            return;
        }
        if (TextUtils.isEmpty(mFileName)) {
            listener.onError(ERR_MSG_FILE_NAME);
            return;
        }

        // 判断网络是否连接
        if (true) {
            // 连接网络
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // your code
                }
            }).start();
        } else {
            if (isDebug) {
            }
            listener.onError(ERR_MSG_NET_WORK);
        }
    }

    // 下载文件
    public void downloadFile(OnDownloadListener listener) {
        if (isDebug) {
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> url      : " + mUrl);
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> params   : " + mParams.toString());
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> driPath  : " + mDriPath);
            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> fileName : " + mFileName);
        }

        if (TextUtils.isEmpty(mUrl)) {
            listener.onError(ERR_MSG_URL);
            return;
        }
        if (TextUtils.isEmpty(mDriPath)) {
            listener.onError(ERR_MSG_DIR_PATH);
            return;
        }
        if (TextUtils.isEmpty(mFileName)) {
            listener.onError(ERR_MSG_FILE_NAME);
            return;
        }

        // 判断网络是否连接
        if (true) {
            // 连接网络
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isDebug) {
                                Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> start    : 开始下载");
                            }
                            listener.onStart();
                        }
                    });

                    // 下载准备
                    int downloadedSize = 0;        // 已经下载的文件大小
                    int fileTotalSize = 0;         // 文件总大小
                    // 文件夹准备
                    File file = new File(mDriPath);
                    if (!file.exists()) {           // 判断文件夹是否存在
                        if (!file.mkdirs()) {       // 文件夹不存在, 创建
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (isDebug) {
                                        Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : 无法创建文件夹");
                                    }
                                    listener.onError("无法创建文件夹");
                                }
                            });
                            return;
                        }
                    }
                    // 文件全路径 = 文件夹+文件名+后缀
                    file = new File(mDriPath + File.separator + mFileName);
                    if (file == null) {
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isDebug) {
                                    Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : 无法创建文件");
                                }
                                listener.onError("无法创建文件");
                            }
                        });
                        return;
                    }
                    // 流和链接
                    InputStream inputStream = null;
                    FileOutputStream outputStream = null;
                    HttpURLConnection connection = null;
                    try {
                        URL url = new URL(mUrl);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(10 * 1000);
                        connection.setReadTimeout(10 * 1000);
                        connection.connect();
                        // 获取要下载的文件信息
                        fileTotalSize = connection.getContentLength();          // 文件总大小
                        inputStream = connection.getInputStream();
                        outputStream = new FileOutputStream(file);
                        byte[] buffer = new byte[1024 * 8];
                        int len;
                        long startTime = System.currentTimeMillis(); // 开始下载时获取开始时间
                        while ((len = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, len);
                            downloadedSize += len;

                            long curTime = System.currentTimeMillis();
                            int usedTime = (int) ((curTime - startTime) / 1000);
                            if (usedTime == 0) usedTime = 1;
                            // int downloadSpeed = (downloadedSize / usedTime) / 1024; // 下载速度
                            int downloadSpeed = (downloadedSize / usedTime); // 下载速度
                            String printSize = HttpURLConnectionUtils.SizeUtils.getPrintSize(downloadSpeed);

                            // 下载速度 - 方法1
                            //                        long startTime = System.currentTimeMillis(); // 开始下载时获取开始时间
                            //                        long curTime = System.currentTimeMillis();
                            //                        int usedTime = (int) ((curTime - startTime) / 1000);
                            //                        if (usedTime == 0) usedTime = 1;
                            // int downloadSpeed = (downloadedSize / usedTime) / 1024; // 下载速度
                            //                        int downloadSpeed = (downloadedSize / usedTime) ; // 下载速度
                            //                        String printSize = SizeUtils.getPrintSize(downloadSpeed);

                            // 下载速度 - 方法2 平均速度, 越往后面越稳定
                            //                        long start = System.nanoTime();   //开始时间
                            //                        long totalRead = 0;  //总共下载了多少
                            //                        final double NANOS_PER_SECOND = 1000000000.0;  //1秒=10亿nanoseconds
                            //                        final double BYTES_PER_MIB = 1024 * 1024;    //1M=1024*1024byte
                            //                        while (((len = is.read(buffler, 0, 1024)) >0)) {
                            //                            totalRead += len;
                            //                            double speed = NANOS_PER_SECOND / BYTES_PER_MIB * totalRead / (System.nanoTime() - start + 1);
                            //                        }

                            // 下载速度 - 方法3
                            // https://blog.csdn.net/public_calss/article/details/45534121

                            // 下载进度
                            final int progress = (int) (downloadedSize * 1.0f / fileTotalSize * 100);
                            String finalFileTotalSize = HttpURLConnectionUtils.SizeUtils.getPrintSize(fileTotalSize);
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (isDebug) {
                                        Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> download : " + progress + "%");
                                    }
                                    listener.onProgress(finalFileTotalSize, progress, printSize);
                                }
                            });
                        }
                        // 下载成功
                        File finalFile = file;
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isDebug) {
                                    Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> finish   : 下载完成 " + finalFile.getAbsolutePath());
                                }
                                listener.onFinish(finalFile);
                            }
                        });
                    } catch (Exception e) {
                        if (file.exists()) {
                            if (file.delete()) {
                                ThreadUtils.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isDebug) {
                                            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : 下载失败, 失败信息: " + e.getMessage());
                                        }
                                        listener.onError("下载失败, 失败信息: " + e.getMessage());
                                    }
                                });
                            } else {
                                ThreadUtils.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isDebug) {
                                            Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : 下载失败, 失败信息: " + e.getMessage());
                                        }
                                        listener.onError("下载失败, 失败信息: " + e.getMessage());
                                    }
                                });
                            }
                        } else {
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (isDebug) {
                                        Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : 下载失败, 失败信息: " + e.getMessage());
                                    }
                                    listener.onError("下载失败, 失败信息: " + e.getMessage());
                                }
                            });
                        }
                    } finally {
                        try {
                            if (inputStream != null)
                                inputStream.close();
                            if (outputStream != null)
                                outputStream.close();
                            if (connection != null)
                                connection.disconnect();
                        } catch (final Exception e) {
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (isDebug) {
                                        Log.d(mDebugTag, mCurrentClass + " - " + mDebugMsg + " ------>> error    : IO流关闭失败, 失败信息: " + e.getMessage());
                                    }
                                    listener.onError("IO流关闭失败, 失败信息: " + e.getMessage());
                                }
                            });
                        }
                    }
                }
            }).start();
        } else {
            if (isDebug) {
            }
            listener.onError(ERR_MSG_NET_WORK);
        }
    }

    // 设置下载文件夹
    public HttpURLConnectionUtils setDirPath(String dirPath) {
        if (dirPath == null || dirPath.length() <= 0) {
            throw new NullPointerException(ERR_MSG_DIR_PATH);
        }
        mDriPath = dirPath;
        return mInstance;
    }

    // 设置下载文件名称 - 注意：名称需要带上文件类型后缀，例如：锤子便签.apk     (需要带上.apk)
    public HttpURLConnectionUtils setFileName(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            throw new NullPointerException(ERR_MSG_FILE_NAME);
        }
        mFileName = fileName;
        return mInstance;
    }

    // 设置下载进度回调监听
    public void setOnDownloadListener(OnDownloadListener listener) {
        if (listener == null) {
            throw new NullPointerException(ERR_MSG_DOWNLOAD_LISTENER);
        }
        downloadFile(listener);
    }

    // 设置下载进度回调监听
    public void setOnUploadListener(OnUploadListener listener) {
        if (listener == null) {
            throw new NullPointerException(ERR_MSG_UPLOAD_LISTENER);
        }
        uploadFile(listener);
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

    // 文件大小转换 - B, KB, MB, GB, TB
    private static class SizeUtils {
        public static String getPrintSize(long size) {
            //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
            if (size < 1024) {
                return String.valueOf(size) + "B";
            } else {
                size = size / 1024;
            }
            //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
            //因为还没有到达要使用另一个单位的时候
            //接下去以此类推
            if (size < 1024) {
                return String.valueOf(size) + "KB";
            } else {
                size = size / 1024;
            }
            if (size < 1024) {
                //因为如果以MB为单位的话，要保留最后1位小数，
                //因此，把此数乘以100之后再取余
                size = size * 100;
                return String.valueOf((size / 100)) + "."
                        + String.valueOf((size % 100)) + "MB";
            } else {
                //否则如果要以GB为单位的，先除于1024再作同样的处理
                size = size * 100 / 1024;
                return String.valueOf((size / 100)) + "."
                        + String.valueOf((size % 100)) + "GB";
            }
        }
    }
}
