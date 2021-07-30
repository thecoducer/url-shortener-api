package com.thecoducer.shortenurl.service;

import com.thecoducer.shortenurl.dto.CountDTO;
import com.thecoducer.shortenurl.dto.ResponseDTO;
import com.thecoducer.shortenurl.dto.UrlInfoDTO;
import com.thecoducer.shortenurl.model.UrlInfo;
import com.thecoducer.shortenurl.repository.ShortenUrlRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShortenUrlService {

    ResponseDTO responseDTO = new ResponseDTO();

    @Autowired
    private ShortenUrlRepository shortenUrlRepository;

    public ResponseEntity<ResponseDTO> generateAndSaveShortKey(String url) {
        String shortKey = shortenUrlRepository.findShortKeyByURL(url);

        // check if url already exists or not
        if(shortKey != null) {
            responseDTO.setMessage("Short key is already present for the given URL.");
        }else{
            // generate unique short key
            try {
                shortKey = DigestUtils.md5Hex(url).toUpperCase();

                UrlInfo urlInfo = new UrlInfo(url, shortKey);
                shortenUrlRepository.save(urlInfo);

                responseDTO.setMessage("Short key has been generated and saved successfully.");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        responseDTO.setUrl(url);
        responseDTO.setShortKey(shortKey);

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResponseDTO> getShortKeyAndIncrementCount(String url) {
        String shortKey = shortenUrlRepository.findShortKeyByURL(url);

        if(shortKey == null) {
            responseDTO.setMessage("There is no short key generated for the given URL.");
        }else{
            try{
                int currentCount = shortenUrlRepository.findUsageCountByURL(url);
                shortenUrlRepository.incrementCountByOne(shortKey, currentCount + 1);
            }catch (Exception e) {
                e.printStackTrace();
            }
            responseDTO.setMessage("Usage count has been incremented by 1.");
        }

        responseDTO.setShortKey(shortKey);
        responseDTO.setUrl(url);

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    public ResponseEntity<CountDTO> getLatestUsageCount(String url) {
        CountDTO countDTO = new CountDTO();
        countDTO.setUsageCount(shortenUrlRepository.findUsageCountByURL(url));

        return new ResponseEntity<CountDTO>(countDTO, HttpStatus.OK);
    }

    public List<UrlInfoDTO> getUrlInfoData() {
        List<UrlInfo> urlInfoList = shortenUrlRepository.findAll();
        List<UrlInfoDTO> urlInfoDTOList = new ArrayList<>();

        for(UrlInfo obj : urlInfoList) {
            UrlInfoDTO urlInfoDTO = new UrlInfoDTO();
            urlInfoDTO.setURL(obj.getURL());
            urlInfoDTO.setUsageCount(obj.getUsageCount());
            urlInfoDTOList.add(urlInfoDTO);
        }

        return urlInfoDTOList;
    }
}
