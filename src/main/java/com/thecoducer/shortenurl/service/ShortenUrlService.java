package com.thecoducer.shortenurl.service;

import com.thecoducer.shortenurl.dto.UrlInfoDTO;
import com.thecoducer.shortenurl.model.UrlInfo;
import com.thecoducer.shortenurl.repository.ShortenUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ShortenUrlService {

    @Autowired
    private ShortenUrlRepository shortenUrlRepository;

    @Autowired
    private UniqueShortKeyGeneratorService uniqueShortKeyGeneratorService;

    public ResponseEntity<Map<String, String>> generateAndSaveShortKey(String url) {
        Map<String, String> response = new TreeMap<String, String>();

        // remove leading and trailing spaces, if any
        url = url.trim();

        String shortKey = shortenUrlRepository.findShortKeyByURL(url);

        // check if url already exists or not
        if(shortKey != null) {
            response.put("message", "Short key is already present for the given URL.");
        }else{
            try {
                shortKey = uniqueShortKeyGeneratorService.generateUniqueShortKey(url);

                UrlInfo urlInfo = new UrlInfo(url, shortKey, System.currentTimeMillis());
                shortenUrlRepository.save(urlInfo);

                response.put("message", "Short key has been generated and saved successfully.");
                response.put("url", url);
                response.put("shortKey", shortKey);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> getShortKeyAndIncrementCount(String url) {
        Map<String, String> response = new TreeMap<String, String>();

        String shortKey = shortenUrlRepository.findShortKeyByURL(url);

        if(shortKey == null) {
            response.put("message", "There is no short key generated for the given URL.");
        }else{
            try{
                int currentCount = shortenUrlRepository.findUsageCountByURL(url);
                shortenUrlRepository.incrementCountByOne(shortKey, currentCount + 1);
            }catch (Exception e) {
                e.printStackTrace();
            }
            response.put("message", "Usage count has been incremented by 1.");
            response.put("url", url);
            response.put("shortKey", shortKey);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Integer>> getLatestUsageCount(String url) {
        Map<String, Integer> response = new TreeMap<String, Integer>();
        response.put("latestUsageCount", shortenUrlRepository.findUsageCountByURL(url));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public List<UrlInfoDTO> getUrlInfoData(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {
        List<UrlInfo> urlInfoList;

        if(page.isEmpty() && size.isEmpty() && sortBy.isEmpty()) {
            urlInfoList = shortenUrlRepository.findAll();
        }else{
            urlInfoList = shortenUrlRepository.findAll(
                    PageRequest.of(page.orElse(0), size.orElse(10), Sort.Direction.ASC, sortBy.orElse("id"))
            ).getContent();
        }

        List<UrlInfoDTO> urlInfoDTOList = new ArrayList<>();
        for(UrlInfo obj : urlInfoList) {
            UrlInfoDTO urlInfoDTO = new UrlInfoDTO();
            urlInfoDTO.setURL(obj.getUrl());
            urlInfoDTO.setUsageCount(obj.getUsageCount());
            urlInfoDTO.setShortKey(obj.getShortKey());
            urlInfoDTO.setCreatedAt(obj.getCreatedAt());
            urlInfoDTOList.add(urlInfoDTO);
        }

        return urlInfoDTOList;
    }
}
