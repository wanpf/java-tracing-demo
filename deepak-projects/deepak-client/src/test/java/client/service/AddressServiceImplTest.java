package client.service;

import client.builder.AddressBuilder;
import client.database.Address;
import client.database.AddressRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AddressServiceImplTest {

    @TestConfiguration
    static class AddressServiceImplTestContextConfiguration {

        @Bean
        public AddressService addressService() {
            return new AddressServiceImpl();
        }
    }

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    private final Long preSavedID = 1L;
    private Address toSave;

    @Before
    public void setUp() {
        Address address = AddressBuilder.buildSampleAddress("dear");
        address.setId(preSavedID);
        Mockito.when(addressRepository.findOne(address.getId()))
                .thenReturn(address);

        toSave = AddressBuilder.buildSampleAddress("toSave");
        Mockito.when(addressRepository.save(toSave))
                .thenReturn(toSave);

    }


    @Test
    public void findAddressById() {
        Address found = addressService.findById(preSavedID);
        Assert.assertNotNull(found);
        Assert.assertEquals(preSavedID.longValue(), found.getId().longValue());
    }

    @Test
    public void saveAddress() {
        Address saved = addressService.saveAddress(toSave);
        Assert.assertNotNull(saved);
        Assert.assertEquals(toSave.getName(), saved.getName());
    }

    @Test
    public void deleteAddress() {
        Address saved = addressService.saveAddress(toSave);
        Assert.assertNotNull(saved);
        Assert.assertEquals(toSave.getName(), saved.getName());
    }

    @Test
    public void updateAddress() {
        Address saved = addressService.updateAddress(toSave);
        Assert.assertNotNull(saved);
        Assert.assertEquals(toSave.getName(), saved.getName());
    }
}
