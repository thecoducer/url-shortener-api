package com.thecoducer.shortenurl.model;

import javax.persistence.*;

@Entity
public class UrlInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "short_key", nullable = false, unique = true)
    private String shortKey;

    @Column(name = "usage_count", nullable = false)
    private int usageCount;

    public UrlInfo() {

    }

    public UrlInfo(String url, String shortKey) {
        this.url = url;
        this.shortKey = shortKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
}
