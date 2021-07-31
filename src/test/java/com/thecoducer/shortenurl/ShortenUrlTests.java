package com.thecoducer.shortenurl;

import com.thecoducer.shortenurl.repository.ShortenUrlRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
        String url = shortenUrlRepository.getARandomUrl();
        int latestUsageCount = shortenUrlRepository.findUsageCountByURL(url);

        mockMvc.perform(get("/count?url=" + url)).andDo(print()).andExpect(status().isOk()).andExpect(content().json("{\n" +
                "  \"usageCount\": " + latestUsageCount + "\n" +
                "}"));
    }

    @Test
    @Transactional
    public void getEndPointTest() throws Exception {
        String url = shortenUrlRepository.getARandomUrl();
        String shortKey = shortenUrlRepository.findShortKeyByURL(url);
        int previousUsageCount = shortenUrlRepository.findUsageCountByURL(url);

        mockMvc.perform(get("/get?url=" + url)).andDo(print()).andExpect(status().isOk()).andExpect(content().json("{\n" +
                "  \"url\": \"" + url + "\",\n" +
                "  \"message\": \"Usage count has been incremented by 1.\",\n" +
                "  \"shortKey\": \"" + shortKey + "\"\n" +
                "}"));

        int latestUsageCount = shortenUrlRepository.findUsageCountByURL(url);
        Assertions.assertEquals(1, (latestUsageCount - previousUsageCount));
    }

    @Test
    @Transactional
    public void testStoreUrl() throws Exception {
        String url = "https://www.youtube.com/";

        ResultActions resultActions = mockMvc.perform(get("/storeurl?url=" + url));

        String shortKey = shortenUrlRepository.findShortKeyByURL(url);

        resultActions.andDo(print()).andExpect(status().isOk()).andExpect(content().json("{\n" +
                "  \"url\": \"" + url + "\",\n" +
                "  \"message\": \"Short key has been generated and saved successfully.\",\n" +
                "  \"shortKey\": \"" + shortKey + "\"\n" +
                "}"));
    }

    @Test
    @Transactional
    public void testStoreUrlForAlreadyExisting() throws Exception {
        String url = shortenUrlRepository.getARandomUrl();

        ResultActions resultActions = mockMvc.perform(get("/storeurl?url=" + url));

        String shortKey = shortenUrlRepository.findShortKeyByURL(url);

        resultActions.andDo(print()).andExpect(status().isOk()).andExpect(content().json("{\n" +
                "  \"url\": \"" + url + "\",\n" +
                "  \"message\": \"Short key is already present for the given URL.\",\n" +
                "  \"shortKey\": \"" + shortKey + "\"\n" +
                "}"));
    }

    @Test
    public void testListEndpoint() throws Exception {
        mockMvc.perform(get("/list")).andDo(print()).andExpect(status().isOk());
    }
}
