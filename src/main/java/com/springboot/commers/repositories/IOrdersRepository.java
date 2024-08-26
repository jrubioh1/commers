package com.springboot.commers.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springboot.commers.entities.Clients;
import com.springboot.commers.entities.Orders;

public interface IOrdersRepository extends CrudRepository<Orders, Long>{

     List<Orders> findByClient(Clients client);


}
