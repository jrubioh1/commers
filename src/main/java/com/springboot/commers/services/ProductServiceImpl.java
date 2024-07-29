package com.springboot.commers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.commers.entities.Employee;
import com.springboot.commers.entities.Product;
import com.springboot.commers.repositories.IProductRepository;

@Service
public class ProductServiceImpl implements IProductsService {

    @Autowired
    private IProductRepository repository;

    @Override
    public Optional<Product> delete(Long id) {
       Optional<Product> optionalProduct= repository.findById(id);
       optionalProduct.ifPresent(productDb->{
            repository.delete(productDb);
       });

       return optionalProduct; 
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>)repository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
       return  repository.findById(id);
    }

    @Override
    public Product save(Product product, Employee employee) {
        product.setCreateAt(LocalDateTime.now());
        product.setCreateBy(employee);
        return repository.save(product);
    }

    @Override
    public Optional<Product> update(Long id, Product product, Employee employee) {
        Optional<Product> optionalProduct= repository.findById(id);
        if(optionalProduct.isPresent()){
            Product productDb=optionalProduct.orElseThrow();
            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            productDb.setStock(product.getStock()); 
            productDb.setModifyAt(LocalDateTime.now());
            productDb.setModifyBy(employee);
            
            return Optional.of(repository.save(productDb));

        }

        return optionalProduct; 
    } 



}
