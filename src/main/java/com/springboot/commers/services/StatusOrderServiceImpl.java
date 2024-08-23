package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.StatusOrder;
import com.springboot.commers.repositories.IStatusOrderRepository;
import com.springboot.commers.services.interfaces.IStatusOrderService;

public class StatusOrderServiceImpl implements IStatusOrderService {

    private final IStatusOrderRepository repository;

    

    public StatusOrderServiceImpl(IStatusOrderRepository repository) {
        this.repository = repository;
    }

    @Override   
    @Transactional(readOnly = true)

    public List<StatusOrder> findAll() {
        return (List<StatusOrder>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatusOrder> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional()
    public StatusOrder save(StatusOrder status) {
       return repository.save(status);
    }

    @Override
    @Transactional()
    public Optional<StatusOrder> update(Long id, StatusOrder status) {
        Optional<StatusOrder> statusOptional= repository.findById(id);
        if (statusOptional.isPresent()) {
            StatusOrder statusDb= statusOptional.get();
            statusDb.setDescription(status.getDescription());
            return Optional.of(repository.save(statusDb));
            
        }

        return statusOptional;
    }

    @Override
    @Transactional()
    public Optional<StatusOrder> delete(Long id) {
        Optional<StatusOrder> statusOptional= repository.findById(id);
        statusOptional.ifPresent((statusDb)->{
            repository.delete(statusDb);
        });

        return statusOptional;
    }

 

}
