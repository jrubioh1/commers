package com.springboot.commers.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Invoice;

public interface IInvoiceService {
    List<Invoice> findAll();
    Optional<Invoice> findById(Long id);
    Invoice save(Invoice invoice, Employees employee);
    Optional<Invoice>  update(Long id, Invoice invoice, Employees employee);
    Optional<Invoice> delete(Long id);
    Invoice getInvoiceDb(Invoice invoice);

}
