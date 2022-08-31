package com.jn.lst.widget.showpic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Des：显示大图
 * 功能介绍：数据类
 * <p>
 * <p>
 * <p>
 * 注意：这个是已经封装好的，别他妈手长
 * 注意：这个是已经封装好的，别他妈手长
 * 注意：这个是已经封装好的，别他妈手长
 */
public class ShowBigImageActivityBean implements Parcelable {
    private String imgPath;     // 本地图片路径
    private String imgUrl;      // 网络图片地址
    private String imgTitle;    // 图片名称或标题

    public ShowBigImageActivityBean() {

    }

    public ShowBigImageActivityBean(String imgPath, String imgUrl, String imgTitle) {
        this.imgPath = imgPath;
        this.imgUrl = imgUrl;
        this.imgTitle = imgTitle;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }

    public ShowBigImageActivityBean(Parcel in) {
        imgPath = in.readString();
        imgUrl = in.readString();
        imgTitle = in.readString();
    }

    public static final Creator<ShowBigImageActivityBean> CREATOR = new Creator<ShowBigImageActivityBean>() {
        @Override
        public ShowBigImageActivityBean createFromParcel(Parcel in) {
            return new ShowBigImageActivityBean(in);
        }

        @Override
        public ShowBigImageActivityBean[] newArray(int size) {
            return new ShowBigImageActivityBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgPath);
        dest.writeString(imgUrl);
        dest.writeString(imgTitle);
    }
}