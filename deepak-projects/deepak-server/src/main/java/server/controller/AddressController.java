package server.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import server.representation.Address;
import server.builder.AddressBuilder;
import server.util.ConversionHelper;

import java.util.UUID;

@RestController
@EnableAutoConfiguration
public class AddressController {

    @RequestMapping("/**")
    public String getAddress() {
        Address addressToSend =  AddressBuilder.buildAddressWithServerSpecs("Rob " + UUID.randomUUID().toString().substring(0,5));
        return ConversionHelper.asJsonStringOrBlank(addressToSend);
    }

/*
    @RequestMapping("/getAddress")
    public String getAddress() {
        Address addressToSend =  AddressBuilder.buildAddressWithServerSpecs("Rob " + UUID.randomUUID().toString().substring(0,5));
        return ConversionHelper.asJsonStringOrBlank(addressToSend);
    }
*/

}





