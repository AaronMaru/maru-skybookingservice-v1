package com.skybooking.stakeholderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting.SendDownloadLinkRQ;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SettingController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void sendDownloadLinkEn() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/wv1.0.0/utils/send-download-link")
                .content(asJsonString(new SendDownloadLinkRQ("saivichet@skybooking.info", "+855")))
                .accept(MediaType.APPLICATION_JSON)
                .header("X-localization", "en")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message", is("Successfully sending")))
                .andExpect(jsonPath("data", is(""))).andReturn();
    }

    @Test
    public void sendDownloadLinkZh() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/wv1.0.0/utils/send-download-link")
                .content(asJsonString(new SendDownloadLinkRQ("saivichet@skybooking.info", "+855")))
                .accept(MediaType.APPLICATION_JSON)
                .header("X-localization", "zh")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message", is("成功发送")))
                .andExpect(jsonPath("data", is(""))).andReturn();
    }

    @Test
    public void sendDownloadLinkEnNotMail() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/wv1.0.0/utils/send-download-link")
                .content(asJsonString(new SendDownloadLinkRQ("abc", "+855")))
                .accept(MediaType.APPLICATION_JSON)
                .header("X-localization", "en")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", is("Invalid email")))
                .andExpect(jsonPath("data", is(""))).andReturn();
    }

    @Test
    public void test() {

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
