package com.thecoducer.shortenurl.repository;

import com.thecoducer.shortenurl.model.UrlInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenUrlRepository extends JpaRepository<UrlInfo, Integer> {
    @Query(value = "select short_key from url_info where url = :url", nativeQuery = true)
    String findShortKeyByURL(String url);

    @Modifying(clearAutomatically = true)
    @Query(value = "update url_info set usage_count = :updatedCount where short_key = :shortKey", nativeQuery = true)
    void incrementCountByOne(String shortKey, int updatedCount);

    @Query(value = "select usage_count from url_info where url = :url", nativeQuery = true)
    int findUsageCountByURL(String url);

    @Query(value = "select url from url_info order by rand() limit 1;", nativeQuery = true)
    String getARandomUrl();

}
