package com.springboot.commers.services.interfaces;

import java.util.List;
import java.util.Optional;


import com.springboot.commers.entities.StatusOrder;

public interface IStatusOrderService {


    List<StatusOrder> findAll();

    Optional<StatusOrder> findById(Long id);

    StatusOrder save(StatusOrder status);

    Optional<StatusOrder> update(Long id, StatusOrder status);

    Optional<StatusOrder> delete(Long id);

    StatusOrder getClientDb(StatusOrder status);

}
