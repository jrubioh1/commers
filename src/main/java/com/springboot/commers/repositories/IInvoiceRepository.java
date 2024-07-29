package com.springboot.commers.repositories;
import org.springframework.data.repository.CrudRepository;


import com.springboot.commers.entities.Invoice;


public interface IInvoiceRepository extends CrudRepository<Invoice, Long>{

}
