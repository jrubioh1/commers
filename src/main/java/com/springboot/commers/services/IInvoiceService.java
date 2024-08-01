package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Invoice;

public interface IInvoiceService {
    List<Invoice> findAll();
    Optional<Invoice> findById(Long id);
    Invoice save(Invoice invoice);
    Optional<Invoice>  update(Long id, Invoice invoice);
    Optional<Invoice> delete(Long id);

}
