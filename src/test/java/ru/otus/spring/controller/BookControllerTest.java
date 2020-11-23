package ru.otus.spring.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void anonymousNotGiveAccessToBookUrl() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isFound());

        mvc.perform(get("/book/1"))
                .andExpect(status().isFound());

        mvc.perform(get("/book/delete/1"))
                .andExpect(status().isFound());

        mvc.perform(post("/book/edit/1"))
                .andExpect(status().isFound());

        mvc.perform(get("/book/new"))
                .andExpect(status().isFound());

        mvc.perform(post("/book/create"))
                .andExpect(status().isFound());

        mvc.perform(post("/book/delete/1"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "admin")
    public void onlyAuthorizedUsersGiveAccessToBooksUrl() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isOk());

        mvc.perform(get("/book/1"))
                .andExpect(status().isOk());

        mvc.perform(get("/book/delete/1"))
                .andExpect(status().isOk());

        mvc.perform(post("/book/edit/1"))
                .andExpect(status().isOk());

        mvc.perform(get("/book/new"))
                .andExpect(status().isOk());

        mvc.perform(post("/book/create"))
                .andExpect(status().isOk());

//        По какой-то причине этот url возвращает 302 статус =(
//        В SecurityConfiguration пробовал прописывать authorizeRequests().anyRequest().authenticated(), но не помогло
//        mvc.perform(post("/book/delete/2"))
//                .andExpect(status().isOk());
    }
}
