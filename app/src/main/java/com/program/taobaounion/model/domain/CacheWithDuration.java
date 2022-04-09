package com.program.taobaounion.model.domain;

public class CacheWithDuration {
    private long duratiion;
    private String cache;

    public CacheWithDuration(long duratiion, String cache) {
        this.duratiion = duratiion;
        this.cache = cache;
    }

    public long getDuratiion() {
        return duratiion;
    }

    public void setDuratiion(long duratiion) {
        this.duratiion = duratiion;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }
}
