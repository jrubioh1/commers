package com.springboot.commers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springboot.commers.entities.PaymentMethod;

public interface IPaymentMethodRepository extends CrudRepository<PaymentMethod, Long> {

}
