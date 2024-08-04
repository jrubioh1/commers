package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Invoice;
import com.springboot.commers.entities.LineInvoice;

public interface IInvoiceService {
    List<Invoice> findAll();
    Optional<Invoice> findById(Long id);
    Invoice save(Invoice invoice, Employees employee);
    Optional<Invoice>  update(Long id, Invoice invoice, Employees employee);
    Optional<Invoice> delete(Long id);
    Invoice getInvoiceDb(Invoice invoice);
    List<LineInvoice> updateLinesInvoice(List<LineInvoice> linesDb, List<LineInvoice> newLines) ;
}
