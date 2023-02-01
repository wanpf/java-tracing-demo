package client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import client.database.Address;
import org.json.JSONObject;

import java.io.IOException;

public class JsonHelper {
    public static String asJsonStringOrBlank(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
           return new JSONObject().toString();
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

    public static String createSuccessJson(final String id) {
        return "{\"success\":"+ id +"\"\"}";
    }
}
