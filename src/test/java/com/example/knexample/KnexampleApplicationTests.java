package com.example.knexample;

import com.example.knexample.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class KnexampleApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MockMvc mvc;

    @Test
    public void postUser() throws Exception {
        User user = new User("Test", "Test", "Test", "Test");
        mvc.perform(MockMvcRequestBuilders.post("/api/users/").contentType(APPLICATION_JSON)
                .content(asJsonString(user)).accept(APPLICATION_JSON)).andExpect(status().isCreated());
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
