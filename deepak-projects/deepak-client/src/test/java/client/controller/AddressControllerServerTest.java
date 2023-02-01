package client.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerServerTest {

    @Autowired
    private AddressController addressController;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockRestServiceServer;
    private static final String VALID_RESPONSE_FROM_SERVER = "\"id\":null,\"nameSpecial\":\"Rob ef64c\",\"email\":\"Rob ef64c@gmail.com\",\"addressLine\":" +
            "\"1b4507f6-a687-44a2-8913-eacdbc03de91\",\"addressLineOptional\":null,\"city\":\"Melbourne\",\"postalCode\":12345,\"country\":\"AU\",\"createdOn\"" +
            ":null,\"phoneNumbers\":null}";
    private Long serverPort = 9091l;
    private String getAddressServerUrl = "http://localhost:"+ serverPort +"/getAddress";

    @Before
    public void setUp() throws Exception
    {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldReturnErrorWhenServerReturnsInvalidResponse() throws Exception{
        mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(getAddressServerUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("invalid_object", MediaType.APPLICATION_JSON));

        String result = addressController.fetchAndStorefromServer(serverPort);

        mockRestServiceServer.verify();

        Assert.assertTrue(result.contains("\"error\":\"Error\",\"code\":4"));

    }

    @Test
    public void shouldReturnErrorWhenServerError() throws Exception{
        mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(getAddressServerUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withServerError());

        String result = addressController.fetchAndStorefromServer(serverPort);
        mockRestServiceServer.verify();
        Assert.assertTrue(result.contains("\"error\":\"Error\",\"code\":1"));

    }

    @Test
    public void shouldReturnErrorWhenServerNotReachable() throws Exception{
        mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(getAddressServerUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.SERVICE_UNAVAILABLE));

        String result = addressController.fetchAndStorefromServer(serverPort);
        mockRestServiceServer.verify();
        Assert.assertTrue(result.contains("\"error\":\"Error\",\"code\":1"));

    }

    @Test
    public void shouldFetchValidAddressFromServer() {

        mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(getAddressServerUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(VALID_RESPONSE_FROM_SERVER, MediaType.APPLICATION_JSON));

        String result = addressController.fetchAndStorefromServer(serverPort);
        mockRestServiceServer.verify();
    }

}
