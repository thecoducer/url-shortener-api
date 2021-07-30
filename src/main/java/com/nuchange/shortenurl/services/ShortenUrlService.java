package com.thecoducer.shortenurl.services;

import com.thecoducer.shortenurl.entities.LatestUsageCount;
import com.thecoducer.shortenurl.entities.Response;
import com.thecoducer.shortenurl.models.UrlInfo;
import com.thecoducer.shortenurl.repositories.ShortenUrlRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShortenUrlService {

    Response response = new Response();

    @Autowired
    private ShortenUrlRepository shortenUrlRepository;

    public ResponseEntity<Response> generateAndSaveShortKey(String url) {
        String shortKey = shortenUrlRepository.findShortKeyByURL(url);

        // check if url already exists or not
        if(shortKey != null) {
            response.setMessage("Short key is already present for the given URL.");
        }else{
            // generate unique short key
            try {
                shortKey = DigestUtils.md5Hex(url).toUpperCase();

                UrlInfo urlInfo = new UrlInfo(url, shortKey);
                shortenUrlRepository.save(urlInfo);

                response.setMessage("Short key has been generated and saved successfully.");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.setUrl(url);
        response.setShortKey(shortKey);

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Response> getShortKeyAndIncrementCount(String url) {
        String shortKey = shortenUrlRepository.findShortKeyByURL(url);

        if(shortKey == null) {
            response.setMessage("There is no short key generated for the given URL.");
        }else{
            try{
                int currentCount = shortenUrlRepository.findUsageCountByURL(url);
                shortenUrlRepository.incrementCountByOne(shortKey, currentCount + 1);
            }catch (Exception e) {
                e.printStackTrace();
            }
            response.setMessage("Usage count has been incremented by 1.");
        }

        response.setShortKey(shortKey);
        response.setUrl(url);

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    public ResponseEntity<LatestUsageCount> getLatestUsageCount(String url) {
        LatestUsageCount latestUsageCount = new LatestUsageCount();
        latestUsageCount.setUsageCount(shortenUrlRepository.findUsageCountByURL(url));

        return new ResponseEntity<LatestUsageCount>(latestUsageCount, HttpStatus.OK);
    }

    public List<UrlInfo> getUrlInfoData() {
        return shortenUrlRepository.findAll();
    }
}
