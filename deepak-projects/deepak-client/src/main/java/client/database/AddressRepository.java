package client.database;

import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long>{
    // TODO the return should be a list
    Address findByName(String name);
}
