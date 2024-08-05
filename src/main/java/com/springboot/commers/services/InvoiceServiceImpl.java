package com.springboot.commers.services;

import java.time.LocalDateTime;
import java.util.List;


import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Invoice;
import com.springboot.commers.entities.LineInvoice;
import com.springboot.commers.repositories.IInvoiceRepository;

//PARA CORREGIR EL ERROR DE DEPENDENCIA CICLICA, TENGO QUE ENCARGAME DE ASIGNAR LA FACTURA A LA LINEA DESDE AQUI
//MODIFICAR EL UPDATE PORQUE YA NO SE VA CREAR LA LINEAS ANTES, ASIQUE HAY QUE CREARLAS PARA LUEGO UPDATE

@Service
public class InvoiceServiceImpl implements IInvoiceService {

    private final IInvoiceRepository repository;

    private final IClientService serviceClient;
    private final ILineInvoiceService serviceLine;
    private final IEmployeeService serviceEmployee;

    // @Autowired
    public InvoiceServiceImpl(IInvoiceRepository repository,
            IClientService serviceClient, ILineInvoiceService serviceLine, IEmployeeService serviceEmployee) {
        this.repository = repository;

        this.serviceClient = serviceClient;
        this.serviceLine = serviceLine;
        this.serviceEmployee = serviceEmployee;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        return (List<Invoice>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional()
    public Invoice save(Invoice invoice, Employees employee) {

        invoice.setEmployee(serviceEmployee.getEmployeeDb(employee));
        invoice.setClient(serviceClient.getClientDb(invoice.getClient()));

        return repository.save(invoice);
    }
    @Override
    @Transactional
    public Optional<Invoice> update(Long id, Invoice invoice, Employees employee) {
    
        Optional<Invoice> optionalInvoice = findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoiceDb = optionalInvoice.orElseThrow();
    
            // Asignar el cliente desde la base de datos
            invoiceDb.setClient(serviceClient.getClientDb(invoice.getClient()));
    
            // Asignar el empleado pasado ya como Employee db
            invoiceDb.setEmployee(employee);
    
            // Actualizar las líneas de factura utilizando el comportamiento en cascada
            invoiceDb.getLinesInvoice().clear();
            invoiceDb.getLinesInvoice().addAll(invoice.getLinesInvoice());
    
            // Actualizar el timestamp
            invoiceDb.setDateTime(LocalDateTime.now());
    
            // Calcular el total de la factura
            Double whole = invoice.getLinesInvoice().stream()
                    .mapToDouble(LineInvoice::getAmount)
                    .sum();
            invoiceDb.setWhole(whole);
    
            // Guardar y devolver la factura actualizada
            return Optional.of(repository.save(invoiceDb));
        }
    
        return optionalInvoice;
    }
    

    @Override
    @Transactional
    public Optional<Invoice> delete(Long id) {
        Optional<Invoice> optionalInvoice = repository.findById(id);
        
        optionalInvoice.ifPresent(invoiceDb -> {
            // Eliminar manualmente las líneas de factura
            invoiceDb.getLinesInvoice().forEach(line -> {
                // Realizar lógica adicional antes de eliminar, si es necesario
                serviceLine.delete(line.getId()); // Eliminar la línea usando el servicio de líneas
            });
    
            // Limpiar la colección de líneas antes de eliminar la factura
            invoiceDb.getLinesInvoice().clear();
    
            // Finalmente, eliminar la factura
            repository.delete(invoiceDb);
        });
    
        return optionalInvoice;
    }
    

    @Override
    @Transactional(readOnly = true)
    public Invoice getInvoiceDb(Invoice invoice) {
        return repository.findById(invoice.getId()).orElseThrow();
    }



}
