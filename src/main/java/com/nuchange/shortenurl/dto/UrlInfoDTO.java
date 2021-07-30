package com.thecoducer.shortenurl.dto;

public class UrlInfoDTO {
    private String URL;
    private int UsageCount;

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
}
