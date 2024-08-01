package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Invoice;
import com.springboot.commers.repositories.IInvoiceRepository;

@Service
public class InvoiceServiceImpl  implements IInvoiceService{

    @Autowired
    private IInvoiceRepository repository; 
    

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        return (List<Invoice>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findById(Long id) {
       return repository.findById(id);
    }

    @Override
    @Transactional()
    public Invoice save(Invoice invoice) {
        return repository.save(invoice);
    }

    @Override
    @Transactional()
    public Optional<Invoice> update(Long id, Invoice invoice) {
    
        Optional<Invoice> optionalInvoice= findById(id);
        if(optionalInvoice.isPresent()){
            Invoice invoiceDb= optionalInvoice.orElseThrow();
            invoiceDb.setClient(invoice.getClient());
            invoiceDb.setDateTime(invoice.getDateTime());
            invoiceDb.setEmployee(invoice.getEmployee());
            invoiceDb.setLinesInvoice(invoice.getLinesInvoice());
            invoiceDb.setWhole(invoice.getWhole());

            return  Optional.of(repository.save(invoiceDb));
        }

        return optionalInvoice; 
    }

    @Override
    @Transactional()
    public Optional<Invoice> delete(Long id) {
       Optional<Invoice> optionalInvoice= repository.findById(id);
       optionalInvoice.ifPresent(invoiceDb->{
        repository.delete(invoiceDb);
       });

       return optionalInvoice;
    }



}
