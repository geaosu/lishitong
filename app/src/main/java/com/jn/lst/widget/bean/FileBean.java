package com.jn.lst.widget.bean;

import java.io.Serializable;

public class FileBean implements Serializable {
    private String id;// id
    private String name;// 名称
    private String type;// 类型
    private String url;// 网络地址
    private String sdCardPath;// 本地地址
    private String size;// 大小

    public FileBean() {

    }

    public FileBean(String id, String name, String type, String url, String sdCardPath, String size) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
        this.sdCardPath = sdCardPath;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSdCardPath() {
        return sdCardPath;
    }

    public void setSdCardPath(String sdCardPath) {
        this.sdCardPath = sdCardPath;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
