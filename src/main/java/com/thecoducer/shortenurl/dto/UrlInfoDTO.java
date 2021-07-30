package com.thecoducer.shortenurl.dto;

public class UrlInfoDTO {
    private String URL;
    private int UsageCount;
    private String shortKey;
    private long createdAt;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getUsageCount() {
        return UsageCount;
    }

    public void setUsageCount(int usageCount) {
        UsageCount = usageCount;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }
}
