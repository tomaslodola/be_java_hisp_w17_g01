package com.w17_g1.socialMeLi.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.dto.output.MessageResponseDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserCountFollowersDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserFollowedOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserFollowersOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserOutputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    ObjectWriter writer;

    @BeforeEach
    void setUp(){
        writer = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writer();
    }

    @Test
    @DisplayName("Test Integrador followUser: resultOK")
    public void followUserTest1() throws Exception {
        int userId = 0;
        int followUserId= 2;

        // Expected
        String response = "Se ha seguido al usuario "+ followUserId + " con exito.";
        MessageResponseDTO expected = new MessageResponseDTO(response);
        String stringExpected = writer.writeValueAsString(expected);

        //Result Matcher
        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isCreated();
        ResultMatcher expectedJson = MockMvcResultMatchers.content().string(stringExpected);
        ResultMatcher expectedContentType = MockMvcResultMatchers.content().contentType("APPLICATION/JSON");

        // Request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/follow/{followUserId}",userId,followUserId);

        // Mock mvc
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(expectedStatus)
                .andExpect(expectedJson)
                .andExpect(expectedContentType);
    }

    @Test
    @DisplayName("Test Integrador followUser result: No se puede seguir a si mismo")
    public void followUserTest2() throws Exception {
        int userId = 0;

        // Expected
        String expected = "{\"message\":\"El usuario no puede seguirse a si mismo\"}";

        //Result Matcher
        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isConflict();
        ResultMatcher expectedJson = MockMvcResultMatchers.content().string(expected);
        ResultMatcher expectedContentType = MockMvcResultMatchers.content().contentType("APPLICATION/JSON");

        // Request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/follow/{userId}",userId,userId);

        // Mock mvc
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(expectedStatus)
                .andExpect(expectedJson)
                .andExpect(expectedContentType);
    }

    @Test
    @DisplayName("Test unfollowUser: resultOk")
    public void unfollowUserTest1() throws Exception {
        Integer userId = 0;
        Integer unfollowUserId = 5;

        //Expected
        String expected = "Se ha dejado de seguir al usuario " + unfollowUserId + " con exito.";
        MessageResponseDTO messageResponseDTO = MessageResponseDTO.builder().message(expected).build();
        String expectedJsonResponse = writer.writeValueAsString(messageResponseDTO);

        //Result Matcher
        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedJson = MockMvcResultMatchers.content().json(expectedJsonResponse);
        ResultMatcher expectedContentType = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        //Request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/users/{userId}/unfollow/{userIdToUnfollow}", userId, unfollowUserId)
                .contentType(MediaType.APPLICATION_JSON);

        //MockMvc
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(expectedStatus)
                .andExpect(expectedJson)
                .andExpect(expectedContentType);
    }

    @Test
    @DisplayName("Test Integrador de countFollowers: resultOK")
    public void countFollowersTest1() throws Exception {
        // Expected
        int userId = 0;
        Integer numberOfFollowers = 2;
        UserCountFollowersDTO expected = new UserCountFollowersDTO(numberOfFollowers);
        String stringExpected = writer.writeValueAsString(expected);

        //Result Matcher
        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedJson = MockMvcResultMatchers.content().string(stringExpected);
        ResultMatcher expectedContentType = MockMvcResultMatchers.content().contentType("APPLICATION/JSON");

        // Request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/followers/count",userId);

        // Mock mvc
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(expectedStatus)
                .andExpect(expectedJson)
                .andExpect(expectedContentType);
    }

    @Test
    @DisplayName("Test Integrador de countFollowers result: No se encontro el usuario")
    public void countFollowersTest2() throws Exception{
        // Expected
        int userId = 9999;
        String expected = "{\"message\":\"No se encontro el usuario con  id: " + userId + "\"}";

        //Result Matcher
        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isNotFound();
        ResultMatcher expectedJson = MockMvcResultMatchers.content().string(expected);
        ResultMatcher expectedContentType = MockMvcResultMatchers.content().contentType("APPLICATION/JSON");

        // Request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/followers/count",userId);

        // Mock mvc
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(expectedStatus)
                .andExpect(expectedJson)
                .andExpect(expectedContentType);
    }

    @Test
    @DisplayName("Test Integrador de getFollowersList: resultOk")
    public void getFollowersListTest1() throws Exception {

        // Expected;
        int userId = 0;
        List<UserOutputDTO> followers = List.of(new UserOutputDTO(1,"Barr Owen"),new UserOutputDTO(3,"Brady Donovan"));
        UserFollowersOutputListDTO expected = new UserFollowersOutputListDTO(userId,"Glenn Brewer", followers);
        String stringExpected = writer.writeValueAsString(expected);

        //Result Matcher
        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedJson = MockMvcResultMatchers.content().string(stringExpected);
        ResultMatcher expectedContentType = MockMvcResultMatchers.content().contentType("APPLICATION/JSON");

        // Request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/followers/list", userId)
                .param("order","name_asc");

        // Mock mvc
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(expectedStatus)
                .andExpect(expectedJson)
                .andExpect(expectedContentType);
    }

    @Test
    @DisplayName("Test Integrador de getFollowersList result: error orden invalido")
    public void getFollowersListTest2() throws Exception{
        // Expected
        int userId = 0;
        String expected = "{\"message\":\"Parametro no correspondiente\"}";

        //Result Matcher
        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isNotFound();
        ResultMatcher expectedJson = MockMvcResultMatchers.content().string(expected);
        ResultMatcher expectedContentType = MockMvcResultMatchers.content().contentType("APPLICATION/JSON");

        // Request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/followers/list", userId )
                .param("order","test");

        // Mock mvc
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(expectedStatus)
                .andExpect(expectedJson)
                .andExpect(expectedContentType);
    }

    @Test
    @DisplayName("Test Integrador de getFollowedList: resultOk")
    public void getFollowedListTest1() throws Exception {
        // Expected;
        int userId = 0;
        List<UserOutputDTO> followedList = List.of(new UserOutputDTO(6,"Taylor Tillman"),new UserOutputDTO(5,"Whitney Mcdowell"));
        UserFollowedOutputListDTO expected = new UserFollowedOutputListDTO(userId,"Glenn Brewer", followedList);
        String stringExpected = writer.writeValueAsString(expected);

        //Result Matcher
        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedJson = MockMvcResultMatchers.content().string(stringExpected);
        ResultMatcher expectedContentType = MockMvcResultMatchers.content().contentType("APPLICATION/JSON");

        // Request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/followed/list", userId)
                .param("order","name_asc");

        // Mock mvc
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(expectedStatus)
                .andExpect(expectedJson)
                .andExpect(expectedContentType);
    }

}
