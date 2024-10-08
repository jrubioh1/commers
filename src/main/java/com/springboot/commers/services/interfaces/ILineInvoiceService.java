package com.springboot.commers.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.LineInvoice;

public interface ILineInvoiceService {


    List<LineInvoice> findAll();
    Optional<LineInvoice> findById(Long id);
    LineInvoice save(LineInvoice line);
    Optional<LineInvoice> update(Long id, LineInvoice line);
    Optional<LineInvoice> delete(Long id); 

}
