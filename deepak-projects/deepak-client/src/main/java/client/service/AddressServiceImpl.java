package client.service;

import client.database.Address;
import client.database.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address saveAddress(Address address) {
        address.setCreatedOn(new Date());
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.delete(id);
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findOne(id);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    public static List<Address> convertToList(Iterable<Address> addressIterable) {
        List<Address> list = new ArrayList();
        if (addressIterable == null )        {
            return list;
        }
        for (Address item : addressIterable) {
            list.add(item);
        }
        return list;
    }
}
