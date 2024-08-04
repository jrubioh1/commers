package com.springboot.commers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Invoice;
import com.springboot.commers.repositories.IInvoiceRepository;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

    private final IInvoiceRepository repository;

    // @Autowired
    public InvoiceServiceImpl(IInvoiceRepository repository) {
        this.repository = repository;
    }

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
    public Invoice save(Invoice invoice, Employees employee) {

        invoice.setEmployee(employee);
        
        Double whole = invoice.getLinesInvoice().stream()
                .mapToDouble(line -> line.getAmount())
                .sum();
        invoice.setWhole(whole);

        return repository.save(invoice);
    }

    @Override
    @Transactional()
    public Optional<Invoice> update(Long id, Invoice invoice, Employees employee) {

        Optional<Invoice> optionalInvoice = findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoiceDb = optionalInvoice.orElseThrow();

            invoiceDb.setClient(invoice.getClient());
            invoiceDb.setEmployee(employee);
            invoiceDb.setLinesInvoice(invoice.getLinesInvoice());
            invoiceDb.setDateTime(LocalDateTime.now());
            Double whole = invoice.getLinesInvoice().stream()
                    .mapToDouble(line -> line.getAmount())
                    .sum();
            invoiceDb.setWhole(whole);

            return Optional.of(repository.save(invoiceDb));
        }

        return optionalInvoice;
    }

    @Override
    @Transactional()
    public Optional<Invoice> delete(Long id) {
        Optional<Invoice> optionalInvoice = repository.findById(id);
        optionalInvoice.ifPresent(invoiceDb -> {
            repository.delete(invoiceDb);
        });

        return optionalInvoice;
    }

}
