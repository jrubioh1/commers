package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Address;

import com.springboot.commers.repositories.IAddressRepository;
import com.springboot.commers.services.interfaces.IAddressService;

public class AddressServiceImpl implements IAddressService {

    private final IAddressRepository repository;

    public AddressServiceImpl(IAddressRepository repository) {
        this.repository = repository;
    }

    @Override 
    @Transactional(readOnly = true)
    public List<Address> findAll() {
        return (List<Address>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Address> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional()
    public Address save(Address address) {
        return repository.save(address);
    }

    @Override
    @Transactional()
    public Optional<Address> update(Long id, Address address) {
        Optional<Address> addressOptional = repository.findById(id);
        if (addressOptional.isPresent()) {
            Address addressDb = addressOptional.get();
            addressDb.setStreet(address.getStreet());
            addressDb.setCity(address.getCity());
            addressDb.setClient(address.getClient());
            addressDb.setCountry(address.getCountry());
            addressDb.setPostalCode(address.getPostalCode());
            addressDb.setState(address.getState());
            return Optional.of(repository.save(addressDb));
     

        }

        return addressOptional; 
    }

    @Override
    @Transactional()
    public Optional<Address> delete(Long id) {
        Optional<Address> addressOptional= repository.findById(id);
        addressOptional.ifPresent((addressDB)->{
            repository.delete(addressDB);
        });

        return addressOptional; 
    }


}
