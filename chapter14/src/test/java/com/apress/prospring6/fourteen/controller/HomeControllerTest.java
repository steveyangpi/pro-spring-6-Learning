package com.apress.prospring6.fourteen.controller;

import com.apress.prospring6.fourteen.WebConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled("Needs MariaDb up on port 3306 even if not used. Keep the container used to run the app up to run this test too.")
@SpringJUnitConfig(classes = WebConfig.class)
public class HomeControllerTest {

    MockMvc mockMvc;

    public HomeControllerTest(WebApplicationContext wac){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testHome()throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Spring MVC ThymeleafExample!!")));
    }
}
