package client.database;

import client.builder.AddressBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressDatabaseTest {

   @Autowired
   private TestEntityManager entityManagerManager;

   @Autowired
   private AddressRepository addressRepository;

    @MockBean
    public RestTemplate restTemplate;


   private Address preSaved, toDelete = null;


    @Before
   public void setUp()   {
        preSaved = entityManagerManager.persistAndFlush(AddressBuilder.buildSampleAddress("preSaved"));
        toDelete = entityManagerManager.persistAndFlush(AddressBuilder.buildSampleAddress("toDelete"));
   }
   
    @Test
    public void getAddressById() throws Exception {
        Address found = addressRepository.findOne(preSaved.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(preSaved.getName(), found.getName());
    }

    @Test
    public void saveAddress() throws Exception {
        Address toSave = AddressBuilder.buildSampleAddress("toSave");
        //given object does not exist in db
        Assert.assertNull(toSave.getId());
        // when
        Address saved = addressRepository.save(toSave);
        // then
        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(toSave.getName(), saved.getName());
    }

    @Test
    public void deleteAddress() throws Exception {
        //given object exists
        Assert.assertNotNull(addressRepository.findOne(toDelete.getId()));
        // when
        addressRepository.delete(toDelete);
        //then
        Assert.assertNull(addressRepository.findOne(toDelete.getId()));
    }

    @Test
    public void updateAddress() throws Exception {
        //given object exists
        Assert.assertNotNull(addressRepository.findOne(preSaved.getId()));
        // and we make some change
        preSaved.setName("nameChanged");
        // when
        Address updated = addressRepository.save(preSaved);
        //then
        Assert.assertEquals("nameChanged",updated.getName());
        Assert.assertEquals(preSaved.getId().longValue(), updated.getId().longValue());
    }


}
