package com.program.taobaounion.model.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchRecommend implements Serializable {

    /**
     * success : true
     * code : 10000
     * message : 获取成功
     * data : [{"id":"1250359232734244864","keyword":"iPhone","createTime":"2020-04-15 17:44"}]
     */

    @SerializedName("success")
    private Boolean success;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<DataBean> data;

    @Override
    public String toString() {
        return "SearchRecommend{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 1250359232734244864
         * keyword : iPhone
         * createTime : 2020-04-15 17:44
         */

        @SerializedName("id")
        private String id;
        @SerializedName("keyword")
        private String keyword;
        @SerializedName("createTime")
        private String createTime;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", keyword='" + keyword + '\'' +
                    ", createTime='" + createTime + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
