package com.jn.lst.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.jn.lst.base.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Request;
import okhttp3.Response;

/*
下载工具类
*/

/**
 * 下载工具
 */
public class DownloadUtils {
    private static final String TAG = "DownloadUtils";

    private static int CONNECTION_POOL_MAX_IDEL = 20;
    private static int CONNECTION_POOL_KEEP_ALIVE = 20;

    private static Context mContext;
    private static DownloadUtils mDownloadUtils;
    private static ThreadUtils mThreadUtils;

    private OKHttpClientBuilder okHttpClient;

    private String mDownloadUrl;
    private String mDriPath;
    private String mFileName;


    private DownloadUtils() {

    }


    // 获取对象
    public static DownloadUtils getInstance() {
        if (mThreadUtils == null) {
            mThreadUtils = new ThreadUtils();
        }
        if (mDownloadUtils == null) {
            mDownloadUtils = new DownloadUtils();
        }
        return mDownloadUtils;
    }


    // 设置下载地址
    public DownloadUtils setUrl(String downloadUrl) {
        if (downloadUrl == null) {
            throw new NullPointerException("url is a null");
        }
        this.mDownloadUrl = downloadUrl;
        return mDownloadUtils;
    }


    // 设置下载文件夹
    public DownloadUtils setDirPath(String dirPath) {
        if (dirPath == null) {
            throw new NullPointerException("dirPath is a null");
        }
        this.mDriPath = dirPath;
        return mDownloadUtils;
    }


    // 设置下载文件名称
    public DownloadUtils setFileName(String fileName) {
        if (fileName == null) {
            throw new NullPointerException("fileName is a null");
        }
        this.mFileName = fileName;
        return mDownloadUtils;
    }


    // 设置下载进度回调监听
    public void setOnDownloadListener(OnDownloadListener listener) {
        if (listener == null) {
            throw new NullPointerException("OnDownloadListener is a null");
        }
        downLoadFile(mDownloadUrl, mDriPath, mFileName, listener);
    }


    /**
     * 下载文件
     *
     * @param downloadUrl
     * @param driPath
     * @param fileName
     * @param listener
     * @return file对象
     */
    public void downLoadFile(String downloadUrl, String driPath, String fileName, OnDownloadListener listener) {
        okHttpClient = new OKHttpClientBuilder();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 先判空, 保证参数无误
                if (TextUtils.isEmpty(downloadUrl) || TextUtils.isEmpty(driPath) || TextUtils.isEmpty(fileName)) {
                    throw new NullPointerException("downloadUrl is a null");
                }
                if (TextUtils.isEmpty(driPath) || TextUtils.isEmpty(fileName)) {
                    throw new NullPointerException("driPath is a null");
                }
                if (TextUtils.isEmpty(fileName)) {
                    throw new NullPointerException("fileName is a null");
                }
                if (listener == null) {
                    throw new NullPointerException("OnDownloadListener is a null object");
                }

                // 输出一下下载信息
                Log.d(TAG + " ------>", "下载地址: " + downloadUrl);
                Log.d(TAG + " ------>", "文件位置: " + driPath);
                Log.d(TAG + " ------>", "文件名称: " + fileName);

                mThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onStart();
                    }
                });

                // 文件夹准备
                File file = new File(driPath);
                if (!file.exists()) {           // 判断文件夹是否存在
                    if (!file.mkdirs()) {       // 文件夹不存在, 创建
                        mThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError("无法创建文件夹");
                            }
                        });
                        return;
                    }
                }


                // 文件全路径 = 文件夹+文件名+后缀
//                file = new File(driPath + File.separator + fileName);
                if (file == null) {
                    mThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError("无法创建文件");
                        }
                    });
                }

                ConnectionPool connectionPool = new ConnectionPool(CONNECTION_POOL_MAX_IDEL, CONNECTION_POOL_KEEP_ALIVE, TimeUnit.MINUTES);
//                TrustAllManager trustAllManager = new TrustAllManager();
                Request request = new Request.Builder()
                        .addHeader("userToken", SPUtils.getInstance().getString(Constants.TOKEN))
                        .url(downloadUrl).build();
                okHttpClient
                        .buildOKHttpClient()
                        .connectTimeout(300, TimeUnit.SECONDS)
                        .writeTimeout(300, TimeUnit.SECONDS)
                        .readTimeout(300, TimeUnit.SECONDS)
                        //https请求忽略证书。
                        .connectionPool(connectionPool)
//                        .sslSocketFactory(createTrustAllSSLFactory(trustAllManager), trustAllManager)
//                        .hostnameVerifier(createTrustAllHostnameVerifier())
                        .retryOnConnectionFailure(true)
                        .build()
                        .newCall(request)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                // 下载失败
                                mThreadUtils.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onError(e.toString());
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                File downloadFile = new File(driPath, fileName);
                                InputStream is = null;
                                byte[] buf = new byte[2048];
                                int len = 0;
                                FileOutputStream fos = null;
                                // 储存下载文件的目录
                                try {
                                    is = response.body().byteStream();
                                    long total = response.body().contentLength();
                                    fos = new FileOutputStream(downloadFile);
                                    long sum = 0;
                                    while ((len = is.read(buf)) != -1) {
                                        fos.write(buf, 0, len);
                                        sum += len;
                                        int progress = (int) (sum * 1.0f / total * 100);
                                        // 下载中
                                        mThreadUtils.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                listener.onProgress(Math.toIntExact(total), progress);

                                            }
                                        });
                                    }
                                    fos.flush();
                                    // 下载完成
//                                    File finalFile = file;
                                    mThreadUtils.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            listener.onFinish(downloadFile);
                                        }
                                    });
                                } catch (Exception e) {
                                    mThreadUtils.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            listener.onError("下载失败, 失败信息: " + e.getMessage());
                                        }
                                    });
                                } finally {
                                    try {
                                        if (is != null)
                                            is.close();
                                    } catch (IOException e) {
                                    }
                                    try {
                                        if (fos != null)
                                            fos.close();
                                    } catch (IOException e) {
                                    }
                                }
                            }
                        });
            }
        }).start();
    }


    /**
     * 回调监听器
     * <p>
     * 所有回调函数都运行在主线程
     */
    public interface OnDownloadListener {
        // 开始下载
        void onStart();

        // 正在下载
        void onProgress(int fileTotalSize, int progress);

        // 下载完成
        void onFinish(File file);

        // 下载失败
        void onError(String errMsg);
    }

    /**
     * 线程切换工具
     */
    private static class ThreadUtils {

        private Handler mHandler = new Handler(Looper.getMainLooper());

        // 在主线程中运行
        public void runOnUiThread(Runnable r) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                //主线程
                r.run();
            } else {
                //子线程
                mHandler.post(r);
            }
        }

        // 在子线程中运行
        public void runOnSubThread(Runnable r) {
            new Thread(r).start();
        }

        // 是否是主线程
        public boolean isMainThread() {
            return Looper.getMainLooper() == Looper.myLooper();
        }
    }

}
