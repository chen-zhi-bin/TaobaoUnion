package com.program.taobaounion.utils;

public class UrlUtils {
    public static String createHomePagerUrl(int materialId,int page){
        return "discovery/"+materialId+"/"+page;
    }

    public static String getCoverPath(String pictUrl,int size) {
        return "https:"+pictUrl+"_"+size+"x"+size+".jpg";
    }

    public static String getCoverPath(String pictUrl) {
        return "https:"+pictUrl;
    }

    public static String getTicketUrl(String url) {
        if (url.startsWith("http")||url.startsWith("https")){
            return url;
        }else {
            return "https:"+url;
        }
    }
}
