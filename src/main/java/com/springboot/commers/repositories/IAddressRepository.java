package com.springboot.commers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springboot.commers.entities.Address;

public interface IAddressRepository extends CrudRepository<Address, Long> {

}
