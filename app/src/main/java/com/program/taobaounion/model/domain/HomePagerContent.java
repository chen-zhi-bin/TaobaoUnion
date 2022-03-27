package com.program.taobaounion.model.domain;
import java.util.List;
public class HomePagerContent {

    private Boolean success;
    private Integer code;
    private String message;
    private List<DataBean> data;

    public Boolean isSuccess() {
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

    @Override
    public String toString() {
        return "HomePagerContent{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {

        private Integer categoryId;
        private Object categoryName;
        private String clickUrl;
        private String commissionRate;
        private Integer couponAmount;
        private String couponClickUrl;
        private String couponEndTime;
        private Object couponInfo;
        private Integer couponRemainCount;
        private String couponShareUrl;
        private String couponStartFee;
        private String couponStartTime;
        private Integer couponTotalCount;
        private String itemDescription;
        private Long itemId;
        private Integer levelOneCategoryId;
        private String levelOneCategoryName;
        private String nick;
        private String pictUrl;
        private Integer sellerId;
        private String shopTitle;
        private SmallImagesBean smallImages;
        private String title;
        private Integer userType;
        private Integer volume;
        private String zkFinalPrice;


        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Object getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(Object categoryName) {
            this.categoryName = categoryName;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }

        public String getCommissionRate() {
            return commissionRate;
        }

        public void setCommissionRate(String commissionRate) {
            this.commissionRate = commissionRate;
        }

        public Integer getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(Integer couponAmount) {
            this.couponAmount = couponAmount;
        }

        public String getCouponClickUrl() {
            return couponClickUrl;
        }

        public void setCouponClickUrl(String couponClickUrl) {
            this.couponClickUrl = couponClickUrl;
        }

        public String getCouponEndTime() {
            return couponEndTime;
        }

        public void setCouponEndTime(String couponEndTime) {
            this.couponEndTime = couponEndTime;
        }

        public Object getCouponInfo() {
            return couponInfo;
        }

        public void setCouponInfo(Object couponInfo) {
            this.couponInfo = couponInfo;
        }

        public Integer getCouponRemainCount() {
            return couponRemainCount;
        }

        public void setCouponRemainCount(Integer couponRemainCount) {
            this.couponRemainCount = couponRemainCount;
        }

        public String getCouponShareUrl() {
            return couponShareUrl;
        }

        public void setCouponShareUrl(String couponShareUrl) {
            this.couponShareUrl = couponShareUrl;
        }

        public String getCouponStartFee() {
            return couponStartFee;
        }

        public void setCouponStartFee(String couponStartFee) {
            this.couponStartFee = couponStartFee;
        }

        public String getCouponStartTime() {
            return couponStartTime;
        }

        public void setCouponStartTime(String couponStartTime) {
            this.couponStartTime = couponStartTime;
        }

        public Integer getCouponTotalCount() {
            return couponTotalCount;
        }

        public void setCouponTotalCount(Integer couponTotalCount) {
            this.couponTotalCount = couponTotalCount;
        }

        public String getItemDescription() {
            return itemDescription;
        }

        public void setItemDescription(String itemDescription) {
            this.itemDescription = itemDescription;
        }

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public Integer getLevelOneCategoryId() {
            return levelOneCategoryId;
        }

        public void setLevelOneCategoryId(Integer levelOneCategoryId) {
            this.levelOneCategoryId = levelOneCategoryId;
        }

        public String getLevelOneCategoryName() {
            return levelOneCategoryName;
        }

        public void setLevelOneCategoryName(String levelOneCategoryName) {
            this.levelOneCategoryName = levelOneCategoryName;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getPictUrl() {
            return pictUrl;
        }

        public void setPictUrl(String pictUrl) {
            this.pictUrl = pictUrl;
        }

        public Integer getSellerId() {
            return sellerId;
        }

        public void setSellerId(Integer sellerId) {
            this.sellerId = sellerId;
        }

        public String getShopTitle() {
            return shopTitle;
        }

        public void setShopTitle(String shopTitle) {
            this.shopTitle = shopTitle;
        }

        public SmallImagesBean getSmallImages() {
            return smallImages;
        }

        public void setSmallImages(SmallImagesBean smallImages) {
            this.smallImages = smallImages;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getUserType() {
            return userType;
        }

        public void setUserType(Integer userType) {
            this.userType = userType;
        }

        public Integer getVolume() {
            return volume;
        }

        public void setVolume(Integer volume) {
            this.volume = volume;
        }

        public String getZkFinalPrice() {
            return zkFinalPrice;
        }

        public void setZkFinalPrice(String zkFinalPrice) {
            this.zkFinalPrice = zkFinalPrice;
        }

        public static class SmallImagesBean {
            private List<String> string;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "categoryId=" + categoryId +
                    ", categoryName=" + categoryName +
                    ", clickUrl='" + clickUrl + '\'' +
                    ", commissionRate='" + commissionRate + '\'' +
                    ", couponAmount=" + couponAmount +
                    ", couponClickUrl='" + couponClickUrl + '\'' +
                    ", couponEndTime='" + couponEndTime + '\'' +
                    ", couponInfo=" + couponInfo +
                    ", couponRemainCount=" + couponRemainCount +
                    ", couponShareUrl='" + couponShareUrl + '\'' +
                    ", couponStartFee='" + couponStartFee + '\'' +
                    ", couponStartTime='" + couponStartTime + '\'' +
                    ", couponTotalCount=" + couponTotalCount +
                    ", itemDescription='" + itemDescription + '\'' +
                    ", itemId=" + itemId +
                    ", levelOneCategoryId=" + levelOneCategoryId +
                    ", levelOneCategoryName='" + levelOneCategoryName + '\'' +
                    ", nick='" + nick + '\'' +
                    ", pictUrl='" + pictUrl + '\'' +
                    ", sellerId=" + sellerId +
                    ", shopTitle='" + shopTitle + '\'' +
                    ", smallImages=" + smallImages +
                    ", title='" + title + '\'' +
                    ", userType=" + userType +
                    ", volume=" + volume +
                    ", zkFinalPrice='" + zkFinalPrice + '\'' +
                    '}';
        }
    }
}
