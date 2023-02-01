package client.testUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import client.database.Address;

import java.io.IOException;

public class ControllerTestUtils {
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Address convertJsonToAddressOrNull(String jsonString) {
        Address converted = null;
        try {
            converted = new ObjectMapper().readValue(jsonString, Address.class);
        }
        catch (IOException exception) {
            return null;
        }
        return converted;
    }
}
