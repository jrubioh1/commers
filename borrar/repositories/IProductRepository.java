package com.springboot.commers.repositories;
import org.springframework.data.repository.CrudRepository;


import com.springboot.commers.entities.Product;


public interface IProductRepository extends CrudRepository<Product, Long>{


}
