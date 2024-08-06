package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Product;

public interface IProductsService {

    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product, Employees employee);
    Optional<Product> update(Long id, Product product, Employees employee);
    Optional<Product> delete(Long id);
    Product saveStock(Product product);
    Product getProductDb(Product product);
    Integer fixedStockProduct( Long id, Integer quantity);
    List<Product> listOfProductDb(List<Product> products);
    boolean existsBySerial(String serial);


}
