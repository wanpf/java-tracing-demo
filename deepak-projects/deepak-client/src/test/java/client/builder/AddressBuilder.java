package client.builder;

import client.database.Address;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class AddressBuilder {
    public static Address buildSampleAddress(String personName)
    {
        final Address address = new Address();
        address.setName(StringUtils.isEmpty(personName) ? UUID.randomUUID().toString() : personName);
        address.setEmail(personName + "@gmail.com");
        address.setAddressLine(UUID.randomUUID().toString());
        address.setCity("Melbourne");
        address.setCountry("AU");
        address.setPostalCode(12345);
        return address;
    }
}
