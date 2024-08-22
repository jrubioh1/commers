package com.springboot.commers.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Orders;

public interface IOrdersService {

    List<Orders> findAll();

    Optional<Orders> findById(Long id);

    Orders save(Orders order);

    Optional<Orders> update(Long id, Orders order);

    Optional<Orders> delete(Long id);

    Orders getClientDb(Orders order);

}
