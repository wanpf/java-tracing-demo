package client.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import client.builder.AddressBuilder;
import client.testUtils.ControllerTestUtils;
import client.database.Address;
import client.util.JsonHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {

    @Autowired
    private MockMvc mvc;
    private String preSavedAddressId, idToDelete = "-1";
    private Address preSavedAddress;
    @Before
    public void setUp() throws Exception
    {
        preSavedAddress= AddressBuilder.buildSampleAddress("preSaved");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/saveAddress")
                .content(ControllerTestUtils.asJsonString(preSavedAddress))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        preSavedAddressId = ControllerTestUtils.convertJsonToAddressOrNull(mvcResult.getResponse().getContentAsString()).getId().toString();
        preSavedAddress.setId(Long.parseLong(preSavedAddressId));
        Address addressToDelete = AddressBuilder.buildSampleAddress("toDelete");
        MvcResult mvcResultDelete = mvc.perform(MockMvcRequestBuilders.post("/saveAddress")
                .content(ControllerTestUtils.asJsonString(addressToDelete))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        idToDelete = ControllerTestUtils.convertJsonToAddressOrNull(mvcResultDelete.getResponse().getContentAsString()).getId().toString();


    }

    @Test
    public void saveAddress() throws Exception {
        Address address = AddressBuilder.buildSampleAddress("dear");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/saveAddress")
                .content(ControllerTestUtils.asJsonString(address))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        Assert.assertNotEquals("-1", result);
    }

    @Test
    public void getAddressById() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/getAddressById/" + preSavedAddressId))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("\"id\":" + preSavedAddressId +""));
    }

    @Test
    public void deleteAddress() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/deleteAddress/{id}" , Long.parseLong(idToDelete) ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(JsonHelper.createSuccessJson(idToDelete), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void updateAddress() throws Exception {
        // given
        Assert.assertNotNull(preSavedAddress.getId());
        Assert.assertEquals("preSaved", preSavedAddress.getName());
        // make some changes
        preSavedAddress.setName("updatedName");
        // when updated
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/updateAddress")
                .content(ControllerTestUtils.asJsonString(preSavedAddress))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String updatedJsonAddress = mvcResult.getResponse().getContentAsString();
        // then
        Assert.assertTrue(updatedJsonAddress.contains("\"name\":\"updatedName\""));
    }

}
