package client.service;

import client.database.Address;

public interface AddressService {
    Address findById(Long id);
    Address saveAddress(Address address);
    Address updateAddress(Address address);
    void deleteAddress(Long id);

}
