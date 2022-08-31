package com.jn.lst.ui.base_main;

import java.util.List;

/**
 * @Description: 主页
 */
public class MainActivityBean {

    /**
     * msg : 操作成功
     * code : 200
     * data : [{"id":29,"name":"新闻中心","recomImage":"/userfiles/1/程序附件//cms/column/recomImage/2022/8/4b0d1949eecd7658e6980ee96b603c93.png","href":"","enableFlag":"1","homeFlag":"1","keywords":"新闻中心","description":"NEWS CENTER","hits":299},{"id":30,"name":"通知公告","href":"","enableFlag":"1","homeFlag":"1","keywords":"通知公告","description":"","hits":158}]
     * success : true
     */

    private String msg;
    private int code;
    private boolean success;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 29
         * name : 新闻中心
         * recomImage : /userfiles/1/程序附件//cms/column/recomImage/2022/8/4b0d1949eecd7658e6980ee96b603c93.png
         * href :
         * enableFlag : 1
         * homeFlag : 1
         * keywords : 新闻中心
         * description : NEWS CENTER
         * hits : 299
         */

        private int id;
        private String name;
        private String recomImage;
        private String href;
        private String enableFlag;
        private String homeFlag;
        private String keywords;
        private String description;
        private int hits;

        public DataBean() {
        }

        public DataBean(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRecomImage() {
            return recomImage;
        }

        public void setRecomImage(String recomImage) {
            this.recomImage = recomImage;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getEnableFlag() {
            return enableFlag;
        }

        public void setEnableFlag(String enableFlag) {
            this.enableFlag = enableFlag;
        }

        public String getHomeFlag() {
            return homeFlag;
        }

        public void setHomeFlag(String homeFlag) {
            this.homeFlag = homeFlag;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getHits() {
            return hits;
        }

        public void setHits(int hits) {
            this.hits = hits;
        }
    }
}