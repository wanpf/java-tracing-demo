package server.builder;

import java.util.UUID;

import org.springframework.util.StringUtils;
import server.representation.Address;
import server.util.Util;

public class AddressBuilder {

    /**
     * Server Specification for Address Object
     •	ID (exactly once)
     •	Name (exactly once)
     •	Email (exactly once)
     •	Address line 1 (exactly once)
     •	Address line 2 (zero or one occurrences)
     •	Town/City (exactly once)
     •	Postal Code (zero or one occurrences)
     •	Country (exactly once)
     •	Telephone number (zero to three occurrences)
     * @param personName
     * @return
     */
    public static Address buildAddressWithServerSpecs(String personName)
    {
        final Address address = new Address();
        address.setId(Util.getNextNumber());
        address.setName(StringUtils.isEmpty(personName) ? UUID.randomUUID().toString() : personName);
        address.setEmail(personName + "@gmail.com");
        address.setAddressLine(UUID.randomUUID().toString());
        address.setCity("Melbourne");
        address.setCountry("AU");
        address.setPostalCode(12345);
        return address;
    }
}

