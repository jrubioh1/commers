package com.springboot.commers.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Address;
import com.springboot.commers.entities.Clients;

public interface IAddressService {

    List<Address> findAll();

    Optional<Address> findById(Long id);

    Address save(Address address);

    Optional<Address> update(Long id, Clients client);

    Optional<Address> delete(Long id);

    Address getClientDb(Address address);

}
