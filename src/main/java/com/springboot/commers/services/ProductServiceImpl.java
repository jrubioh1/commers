package com.springboot.commers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Product;
import com.springboot.commers.repositories.IProductRepository;

@Service
public class ProductServiceImpl implements IProductsService {


    private final IProductRepository repository;


    //@Autowired
        public ProductServiceImpl(IProductRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional()
    public Optional<Product> delete(Long id) {
       Optional<Product> optionalProduct= repository.findById(id);
       optionalProduct.ifPresent(productDb->{
            repository.delete(productDb);
       });

       return optionalProduct; 
    }


    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>)repository.findAll();
    }

    @Override
    @Transactional(readOnly =  true)
    public Optional<Product> findById(Long id) {
       return  repository.findById(id);
    }

    @Override
    @Transactional()
    public Product save(Product product, Employees employee) {
        product.setCreateAt(LocalDateTime.now());
        product.setCreateBy(employee);
        return repository.save(product);
    }

    @Override
    @Transactional()
    public Optional<Product> update(Long id, Product product, Employees employee) {
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
