package com.thecoducer.shortenurl.controller;

import com.thecoducer.shortenurl.dto.CountDTO;
import com.thecoducer.shortenurl.dto.ResponseDTO;
import com.thecoducer.shortenurl.dto.UrlInfoDTO;
import com.thecoducer.shortenurl.model.UrlInfo;
import com.thecoducer.shortenurl.service.ShortenUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(tags = "APIs")
@RestController
public class ShortenUrlController {

    @Autowired
    private ShortenUrlService shortenUrlService;

    @GetMapping("storeurl")
    @ApiOperation(value = "Generate a unique short key for the given URL and store their mapping.")
    public ResponseEntity<ResponseDTO> shortenAndStoreUrl(@RequestParam String url) {
        ResponseEntity<ResponseDTO> response = null;
        if(url != null && !url.isEmpty()) {
            response = shortenUrlService.generateAndSaveShortKey(url);
        }
        return response;
    }

    @GetMapping("get")
    @ApiOperation(value = "Get unique short key for a URL and also increment its usage count.")
    public ResponseEntity<ResponseDTO> retrieveShortKey(@RequestParam String url) {
        ResponseEntity<ResponseDTO> response = null;
        if(url != null && !url.isEmpty()) {
            response = shortenUrlService.getShortKeyAndIncrementCount(url);
        }
        return response;
    }

    @GetMapping("count")
    @ApiOperation(value = "Get latest usage count of a URL.")
    public ResponseEntity<CountDTO> retrieveLatestUsageCount(@RequestParam String url) {
        ResponseEntity<CountDTO> response = null;
        if(url != null && !url.isEmpty()) {
            response = shortenUrlService.getLatestUsageCount(url);
        }
        return response;
    }

    @GetMapping("list")
    @ApiOperation(value = "Display all URLs and their counts.")
    public List<UrlInfoDTO> retrieveUrlAndCounts(@RequestParam(required = false) Optional<Integer> page,
                                                 @RequestParam(required = false) Optional<Integer> size,
                                                 @RequestParam(required = false) Optional<String> sortBy) {
        return shortenUrlService.getUrlInfoData(page, size, sortBy);
    }
}
