package com.thecoducer.shortenurl;

import com.thecoducer.shortenurl.repository.ShortenUrlRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShortenUrlTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ShortenUrlRepository shortenUrlRepository;

    @Test
    public void usageCountTest() throws Exception {
        String url = "https://en.wikipedia.org/wiki/Base64";
        int latestUsageCount = shortenUrlRepository.findUsageCountByURL(url);

        mockMvc.perform(get("/count?url=" + url)).andDo(print()).andExpect(status().isOk()).andExpect(content().json("{\n" +
                "  \"usageCount\": " + latestUsageCount + "\n" +
                "}"));
    }

    @Test
    @Transactional
    public void getEndPointTest() throws Exception {
        String url = "https://en.wikipedia.org/wiki/Base64";
        int previousUsageCount = shortenUrlRepository.findUsageCountByURL(url);

        mockMvc.perform(get("/get?url=https://en.wikipedia.org/wiki/Base64")).andDo(print()).andExpect(status().isOk()).andExpect(content().json("{\n" +
                "  \"url\": \"https://en.wikipedia.org/wiki/Base64\",\n" +
                "  \"message\": \"Usage count has been incremented by 1.\",\n" +
                "  \"shortKey\": \"EMwCjEMM\"\n" +
                "}"));

        int latestUsageCount = shortenUrlRepository.findUsageCountByURL(url);
        Assertions.assertEquals(1, (latestUsageCount - previousUsageCount));
    }
}
