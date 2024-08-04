package com.springboot.commers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Product;
import com.springboot.commers.repositories.IProductRepository;

@Service
public class ProductServiceImpl implements IProductsService {

    private final IProductRepository repository;

    // @Autowired
    public ProductServiceImpl(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional()
    public Optional<Product> delete(Long id) {
        Optional<Product> optionalProduct = repository.findById(id);
        optionalProduct.ifPresent(productDb -> {
            repository.delete(productDb);
        });

        return optionalProduct;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional()
    public Product save(Product product, Employees employee) {
        product.setCreateAt(LocalDateTime.now());
        // el employee del parametro es un employeeDB
        product.setCreateBy(employee);
        return repository.save(product);
    }

    @Override
    @Transactional()
    public Optional<Product> update(Long id, Product product, Employees employee) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()) {
            Product productDb = optionalProduct.orElseThrow();
            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            productDb.setStock(product.getStock());
            productDb.setModifyAt(LocalDateTime.now());
            // el employee del parametro es un employeeDB
            productDb.setModifyBy(employee);

            return Optional.of(repository.save(productDb));

        }

        return optionalProduct;
    }

    @Override
    @Transactional
    public Product saveStock(Product product) {
        return repository.save(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Product getProductDb(Product product){
        return repository.findById(product.getId()).get();
    }

    @Transactional()
    @Override
    public Integer fixedStockProduct( Long id, Integer quantity){
        Product productDb= repository.findById(id).orElseThrow();
        Integer stock=productDb.getStock()+quantity;
        productDb.setStock(stock);
        saveStock(productDb);

        return stock;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> listOfProductDb(List<Product> products){

        return products.stream()
    .map(product -> repository.findById(product.getId()).get())
    .collect(Collectors.toList());
    }
}
