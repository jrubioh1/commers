package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.springboot.commers.entities.LineInvoice;
import com.springboot.commers.entities.Product;

import com.springboot.commers.repositories.ILinesRepository;

@Service
public class LineInvoiceServiceImpl implements ILineInvoiceService {

    private final ILinesRepository repository;
    private final IProductsService serviceProduct;




    // @Autowired
    @Override
    @Transactional(readOnly = true)
    public List<LineInvoice> findAll() {
        return (List<LineInvoice>) repository.findAll();
    }


    public LineInvoiceServiceImpl(ILinesRepository repository, IProductsService serviceProduct) {
        this.repository = repository;
        this.serviceProduct = serviceProduct;
 
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LineInvoice> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public LineInvoice save(LineInvoice line) {
        Product productDb = serviceProduct.getProductDb(line.getProduct());
        serviceProduct.fixedStockProduct(line.getProduct().getId(), -line.getQuantity());
        line.setProduct(productDb);
        return repository.save(line);
    }

    @Override
    @Transactional
    public Optional<LineInvoice> update(Long id, LineInvoice line) {
        Optional<LineInvoice> lineOptional = findById(id);
        if (lineOptional.isPresent()) {
            LineInvoice lineInvoiceDb = lineOptional.get();
            lineInvoiceDb.setProduct(serviceProduct.getProductDb(line.getProduct()));
            lineInvoiceDb.setQuantity(line.getQuantity());
            lineInvoiceDb.setAmount(lineInvoiceDb.getProduct().getPrice()*lineInvoiceDb.getQuantity());
            Integer difStock=line.getQuantity()-lineInvoiceDb.getQuantity();
            serviceProduct.fixedStockProduct(lineInvoiceDb.getProduct().getId(), difStock);
            return Optional.of(repository.save(lineInvoiceDb));
        }

        return lineOptional;
    }

    @Override
    @Transactional
    public Optional<LineInvoice> delete(Long id) {

        Optional<LineInvoice> lineOptional = repository.findById(id);
        lineOptional.ifPresent(lineDb -> {
            serviceProduct.fixedStockProduct(lineDb.getProduct().getId(), lineDb.getQuantity());
            repository.delete(lineDb);
        });

        return lineOptional;
    }

    
    

}
