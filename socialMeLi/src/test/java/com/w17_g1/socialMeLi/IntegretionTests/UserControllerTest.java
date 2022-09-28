package com.w17_g1.socialMeLi.IntegretionTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.dto.output.User.UserCountFollowersDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void numberOfFollowersTest() throws Exception {
        ObjectWriter writer= new ObjectMapper().registerModule(new JavaTimeModule()).writer();
        UserCountFollowersDTO userCountFollowersDTO = new UserCountFollowersDTO();
        userCountFollowersDTO.setCountFollowers(2);
        ResponseEntity<UserCountFollowersDTO> response = new ResponseEntity<>(userCountFollowersDTO, HttpStatus.OK);


        String stringExpect = writer.writeValueAsString(response.getBody());

        System.out.println(stringExpect);

        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedJson = MockMvcResultMatchers.content().json(stringExpect);
        ResultMatcher expectedContentType = MockMvcResultMatchers.content().contentType("APPLICATION/JSON");


        //Request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/0/followers/count");
        //
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(expectedStatus,expectedJson,expectedContentType);

    }




}
