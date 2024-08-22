package com.springboot.commers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springboot.commers.entities.Orders;

public interface IOrdersRepository extends CrudRepository<Orders, Long>{

}
