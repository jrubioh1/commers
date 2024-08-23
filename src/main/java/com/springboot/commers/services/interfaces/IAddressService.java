package com.springboot.commers.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Address;


public interface IAddressService {

    List<Address> findAll();

    Optional<Address> findById(Long id);

    Address save(Address address);

    Optional<Address> update(Long id, Address  address);

    Optional<Address> delete(Long id);

   

}
