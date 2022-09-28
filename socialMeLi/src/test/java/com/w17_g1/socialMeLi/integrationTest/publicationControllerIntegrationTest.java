package com.w17_g1.socialMeLi.integrationTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.Publication.PublicationIdDTO;
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

import static com.w17_g1.socialMeLi.factory.PublicationFactory.createPublicationDto;

@SpringBootTest
@AutoConfigureMockMvc
public class publicationControllerIntegrationTest {

    MockMvc mockMvc;

    ObjectWriter writer;

    @Autowired
    public  publicationControllerIntegrationTest(MockMvc mockMvc){
        this.mockMvc=mockMvc;
        this.writer = new ObjectMapper().registerModule(new JavaTimeModule()).writer();

    }

    private static final PublicationIdDTO publicationIdDTOExpected = new PublicationIdDTO(12);

    @Test
    @DisplayName("Verifica la creacion correcta de una publication")
    public void test1() throws Exception {
        // Variables
        PublicationDTO publicationDTO = createPublicationDto();

        String responseJsonBody =  writer.writeValueAsString(publicationIdDTOExpected);

        String requestJsonBody =  writer.writeValueAsString(publicationDTO);
        // RequestHandles

        ResultMatcher jsonExpected = MockMvcResultMatchers.content().string(responseJsonBody);
        ResultMatcher statusExpected = MockMvcResultMatchers.status().isOk();
        ResultMatcher contentTypeExpected =  MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);
        // Request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/products/post/").contentType(MediaType.APPLICATION_JSON).content(requestJsonBody);
        // MockMVC
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonExpected)
                .andExpect(statusExpected)
                .andExpect(contentTypeExpected);
    }


}
