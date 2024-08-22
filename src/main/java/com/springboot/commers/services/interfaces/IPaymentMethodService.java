package com.springboot.commers.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.PaymentMethod;


public interface IPaymentMethodService {
     List<PaymentMethod> findAll();

    Optional<PaymentMethod> findById(Long id);

    PaymentMethod save(PaymentMethod status);

    Optional<PaymentMethod> update(Long id, PaymentMethod method);

    Optional<PaymentMethod> delete(Long id);

    Optional<PaymentMethod> getClientDb(PaymentMethod method);

}
