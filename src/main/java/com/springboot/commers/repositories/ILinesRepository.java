package com.springboot.commers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springboot.commers.entities.LineInvoice;

public interface ILinesRepository extends CrudRepository<LineInvoice, Long> {

}
