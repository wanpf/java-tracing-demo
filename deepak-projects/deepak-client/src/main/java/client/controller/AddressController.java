package client.controller;

import client.database.Address;
import client.service.AddressService;
import client.util.JsonHelper;
import client.util.ErrorHelper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@EnableAutoConfiguration
public class AddressController {
    @Autowired
    AddressService addressService;

    @Autowired
    RestTemplate restTemplate;

    private final static String SERVER_GET_ADDRESS = "/getAddress";


    @RequestMapping("/getAddressById/{id}")
    public String getAddressById(@PathVariable Long id) {
        Address foundAddress= addressService.findById(id);
        if (foundAddress == null)
        {
            return new JSONObject().toString();
        }
        return JsonHelper.asJsonStringOrBlank(foundAddress);
    }


    @RequestMapping("/fetchAndStore/{serverPort}")
    public String fetchAndStorefromServer(@PathVariable Long serverPort) {
       Address address = null;
       try {
           // Connect and fetch data from server
           serverPort += 1;
           // address = restTemplate.getForObject("http://www-" + serverPort + ".java-tracing.svc.cluster.local:" + serverPort + SERVER_GET_ADDRESS, Address.class);
           String nextUrl = "http://www-" + serverPort + ".java-tracing.svc.cluster.local:" + serverPort + "/fetchAndStore/" + serverPort;
           System.out.println("next URL: " + nextUrl);
           address = restTemplate.getForObject(nextUrl, Address.class);
       }
       catch (RestClientException restException) {
           return JsonHelper.asJsonStringOrBlank(ErrorHelper.getError(restException));
       }
       catch (HttpMessageNotReadableException messageNotReadable) {
           return JsonHelper.asJsonStringOrBlank(ErrorHelper.getError(messageNotReadable));
       }
       catch (Exception exception) {
           return JsonHelper.asJsonStringOrBlank(ErrorHelper.getError(exception));
       }

       String id = this.saveAddressToDb(address);
       return JsonHelper.asJsonStringOrBlank(id == null ?
               JsonHelper.asJsonStringOrBlank(ErrorHelper.getGenericError()) : JsonHelper.createSuccessJson(id));
    }

    @RequestMapping(value = "/saveAddress", method = RequestMethod.POST)
    @ResponseBody
    public String saveAddress(@RequestBody Address address) {
        Address saved  = null;
        try {
            saved = addressService.saveAddress(address);
            if (saved == null || saved.getId() == null) {
                return JsonHelper.asJsonStringOrBlank(ErrorHelper.getErrorWithMessage("Could not save the object."));
            }
        }
        catch (Exception exception)        {
            return JsonHelper.asJsonStringOrBlank(ErrorHelper.getError(exception));
        }
        return JsonHelper.asJsonStringOrBlank(saved);
    }

    @RequestMapping(value = "/updateAddress", method = RequestMethod.PUT)
    public String updateAddress(@RequestBody Address address) {
        Address updatedAddress = null;
        try {
            if (address.getId() == null) {
                return JsonHelper.asJsonStringOrBlank(ErrorHelper.getErrorWithMessage("Record does not exist"));
            }
            Address addressInDb = addressService.findById(address.getId());
            if (addressInDb == null) {
                return JsonHelper.asJsonStringOrBlank(ErrorHelper.getErrorWithMessage("Could not find record with ID : " + address.getId()));
            }
            updatedAddress = addressService.updateAddress(address);
        }
        catch (Exception exception){
            return JsonHelper.asJsonStringOrBlank(ErrorHelper.getError(exception));
        }
        return JsonHelper.asJsonStringOrBlank(updatedAddress);
    }

    @RequestMapping(value = "/deleteAddress/{id}", method = RequestMethod.DELETE)
    public String deleteAddress(@PathVariable Long id) {
        try {
            addressService.deleteAddress(id);
        }
        catch (Exception exception){
            return JsonHelper.asJsonStringOrBlank(ErrorHelper.getGenericError());
        }
        return JsonHelper.createSuccessJson(id.toString());
    }

    private String saveAddressToDb(final Address address) {
       String savedAddressJson = this.saveAddress(address);
       Address converted = JsonHelper.convertJsonToAddressOrNull(savedAddressJson);
       if (converted != null && converted.getId() != null) {
           return converted.getId().toString();
       }
       return null;
    }
}
