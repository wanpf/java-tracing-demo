package client.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import client.util.ErrorHelper;
import client.util.JsonHelper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ErrorControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void getAddressById() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/error"))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(mvcResult.getResponse().getContentAsString(),
                JsonHelper.asJsonStringOrBlank(ErrorHelper.getErrorWithMessage(ErrorController.SYSTEM_ERROR_TEXT)));
    }


}

