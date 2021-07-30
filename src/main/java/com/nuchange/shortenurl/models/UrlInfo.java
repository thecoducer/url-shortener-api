package com.thecoducer.shortenurl.models;

import javax.persistence.*;

@Entity
public class UrlInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "url")
    private String URL;

    @Column(name = "short_key", nullable = false, unique = true)
    private String ShortKey;

    @Column(name = "usage_count")
    private int UsageCount;

    public UrlInfo() {

    }

    public UrlInfo(String URL, String shortKey) {
        this.URL = URL;
        ShortKey = shortKey;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getShortKey() {
        return ShortKey;
    }

    public void setShortKey(String shortKey) {
        ShortKey = shortKey;
    }

    public int getUsageCount() {
        return UsageCount;
    }

    public void setUsageCount(int usageCount) {
        UsageCount = usageCount;
    }
}
