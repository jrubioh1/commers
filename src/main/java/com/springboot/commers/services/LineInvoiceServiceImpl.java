package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Invoice;
import com.springboot.commers.entities.LineInvoice;
import com.springboot.commers.entities.Product;
import com.springboot.commers.helpers.InvoiceHelpers;
import com.springboot.commers.repositories.ILinesRepository;

public class LineInvoiceServiceImpl implements ILineInvoiceService {

    private final ILinesRepository repository;

    private final InvoiceHelpers invoiceHelpers;

    // @Autowired
    public LineInvoiceServiceImpl(ILinesRepository repository, InvoiceHelpers invoiceHelpers) {
        this.repository = repository;
        this.invoiceHelpers = invoiceHelpers;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineInvoice> findAll() {
        return (List<LineInvoice>) repository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LineInvoice> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public LineInvoice save(LineInvoice line) {
        Product productDb = invoiceHelpers.getProductDb(line.getProduct());
        Invoice invoiceDb = invoiceHelpers.getInvoiceDb(line.getInvoice());
        invoiceHelpers.fixedStockProduct(line.getProduct().getId(), -line.getQuantity());
        line.setProduct(productDb);
        line.setInvoice(invoiceDb);

        return repository.save(line);
    }

    @Override
    @Transactional
    public Optional<LineInvoice> update(Long id, LineInvoice line) {
        Optional<LineInvoice> lineOptional = findById(id);
        if (lineOptional.isPresent()) {
            LineInvoice lineInvoiceDb = lineOptional.get();
            lineInvoiceDb.setProduct(invoiceHelpers.getProductDb(line.getProduct()));
            lineInvoiceDb.setQuantity(line.getQuantity());
            lineInvoiceDb.setAmount(lineInvoiceDb.getProduct().getPrice()*lineInvoiceDb.getQuantity());
            Integer difStock=line.getQuantity()-lineInvoiceDb.getQuantity();
            invoiceHelpers.fixedStockProduct(lineInvoiceDb.getProduct().getId(), difStock);
            return Optional.of(repository.save(lineInvoiceDb));
        }

        return lineOptional;
    }

    @Override
    @Transactional
    public Optional<LineInvoice> delete(Long id) {

        Optional<LineInvoice> lineOptional = repository.findById(id);
        lineOptional.ifPresent(lineDb -> {
            invoiceHelpers.fixedStockProduct(lineDb.getProduct().getId(), lineDb.getQuantity());
            repository.delete(lineDb);
        });

        return lineOptional;
    }

}
