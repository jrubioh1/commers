package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.PaymentMethod;
import com.springboot.commers.repositories.IPaymentMethodRepository;
import com.springboot.commers.services.interfaces.IPaymentMethodService;
import org.springframework.transaction.annotation.Transactional;

public class PaymentMethodServiceImpl implements IPaymentMethodService{

    private final IPaymentMethodRepository repository;

    

    public PaymentMethodServiceImpl(IPaymentMethodRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethod> findAll() {
        return (List<PaymentMethod>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentMethod> findById(Long id) {
      return repository.findById(id);
    }

    @Override
    @Transactional
    public PaymentMethod save(PaymentMethod method) {
        return repository.save(method);
    }

    @Override
    @Transactional
    public Optional<PaymentMethod> update(Long id, PaymentMethod method) {
       Optional<PaymentMethod> methodOptional= repository.findById(id);
       if(methodOptional.isPresent()){
        PaymentMethod methodDb= methodOptional.get();
        methodDb.setDescription(method.getDescription());
        return Optional.of(repository.save(methodDb));
       }

       return methodOptional; 
    }

    @Override
    @Transactional
    public Optional<PaymentMethod> delete(Long id) {
      Optional<PaymentMethod> methodOptional= repository.findById(id);
      methodOptional.ifPresent((methodDb)->{
            repository.delete(methodDb);
      });

      return methodOptional;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentMethod> getClientDb(PaymentMethod method) {
        return repository.findById(method.getId()); 
        
    }

}
