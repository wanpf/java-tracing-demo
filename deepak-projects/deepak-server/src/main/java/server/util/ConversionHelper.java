package server.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class ConversionHelper {
    public static String asJsonStringOrBlank(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            return new JSONObject().toString();
        }
    }

}
