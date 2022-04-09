package com.program.taobaounion.model.domain;

public interface IBaseInfo {
    /**
     * 获取商品的封面
     * @return
     */
    String getCover();

    /**
     * 商品的标题
     * @return
     */
    String getTitle();


    /**
     *商品的url
     * @return
     */
    String getUrl();
}
