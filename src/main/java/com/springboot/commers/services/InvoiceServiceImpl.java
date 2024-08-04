package com.springboot.commers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Invoice;

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
    //public InvoiceServiceImpl(IInvoiceRepository repository, 
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
    @Transactional()
    // como se crean las facturas en vacio una vez se hayan creado las lineas de
    // factura correspondiente de manda un objecto factura con las lines para que se
    // complete la factura-------
    // si la factura ya esta completa se borran las lineas y se update
    public Optional<Invoice> update(Long id, Invoice invoice, Employees employee) {

        Optional<Invoice> optionalInvoice = findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoiceDb = optionalInvoice.orElseThrow();

            invoiceDb.setClient(serviceClient.getClientDb(invoice.getClient()));
            // el employe se tiene que pasar ya de tipo employye db si no hacerlo antes aqui
            invoiceDb.setEmployee(employee);

            if (!invoiceDb.getLinesInvoice().isEmpty()) {
                serviceLine.removeLineInvoicesDb(invoiceDb.getLinesInvoice());
            }

            invoiceDb.setLinesInvoice(serviceLine.getLineInvoicesDb(invoice.getLinesInvoice()));
            invoiceDb.setDateTime(LocalDateTime.now());
            Double whole = invoice.getLinesInvoice().stream()
                    .mapToDouble(line -> line.getAmount())
                    .sum();
            invoiceDb.setWhole(whole);

            return Optional.of(repository.save(invoiceDb));
        }

        return optionalInvoice;
    }

    @Override
    @Transactional()
    public Optional<Invoice> delete(Long id) {
        Optional<Invoice> optionalInvoice = repository.findById(id);
        optionalInvoice.ifPresent(invoiceDb -> {
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
