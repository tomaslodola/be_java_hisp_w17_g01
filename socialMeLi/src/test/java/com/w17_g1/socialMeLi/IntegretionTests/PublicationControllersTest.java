package com.w17_g1.socialMeLi.IntegretionTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import com.w17_g1.socialMeLi.factory.PublicationFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class PublicationControllersTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void createPublicationTest() throws Exception {
        ObjectWriter writer= new ObjectMapper().registerModule(new JavaTimeModule()).writer();

        PublicationDTO publicationDTO = PublicationFactory.createPublicationDTO();
        writer.writeValueAsString(publicationDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(publicationDTO);
        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isOk();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products/post/");

        mockMvc.perform(requestBuilder.content(json).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print()).andExpect(expectedStatus);
    }
}
