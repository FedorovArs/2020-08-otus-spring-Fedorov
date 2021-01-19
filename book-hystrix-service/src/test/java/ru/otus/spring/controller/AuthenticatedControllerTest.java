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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticatedControllerTest {

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
    public void mustNotMissAnUnauthorizedUser() throws Exception {
        mvc.perform(get("/authenticated"))
                .andExpect(status().isFound());
    }

    @Test
    public void onlyAnonymousGiveAccessToPublicUrl() throws Exception {
        mvc.perform(get("/public"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin")
    @Test
    public void mustNotGiveAccessToAnAuthorizedUserPublicUrl() throws Exception {
        mvc.perform(get("/public"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "admin")
    @Test
    public void testAuthenticatedOnAdmin() throws Exception {
        mvc.perform(get("/authenticated"))
                .andExpect(status().isOk());
    }


}
