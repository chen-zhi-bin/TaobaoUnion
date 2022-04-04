package com.program.taobaounion.utils;

public class UrlUtils {
    public static String createHomePagerUrl(int materialId,int page){
        return "discovery/"+materialId+"/"+page;
    }

    public static String getCoverPath(String pictUrl,int size) {
        return "https:"+pictUrl+"_"+size+"x"+size+".jpg";
    }

    public static String getCoverPath(String pictUrl) {
        if (pictUrl.startsWith("http")||pictUrl.startsWith("https")){
            return pictUrl;
        }else {
            return "https:"+pictUrl;
        }
    }

    public static String getTicketUrl(String url) {
        if (url.startsWith("http")||url.startsWith("https")){
            return url;
        }else {
            return "https:"+url;
        }
    }

    public static String getSelectedPageContentUrl(int categoryID) {
        return "recommend/"+categoryID;
    }
}
