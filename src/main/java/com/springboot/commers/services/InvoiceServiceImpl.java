package com.springboot.commers.services;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

        List<LineInvoice> lines = invoice.getLinesInvoice().stream().map(line -> serviceLine.save(line))
                .collect(Collectors.toList());
        invoice.setLinesInvoice(lines);
        return repository.save(invoice);
    }

    @Override
    @Transactional()
    public Optional<Invoice> update(Long id, Invoice invoice, Employees employee) {

        Optional<Invoice> optionalInvoice = findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoiceDb = optionalInvoice.orElseThrow();

            invoiceDb.setClient(serviceClient.getClientDb(invoice.getClient()));
            // el employe se tiene que pasar ya de tipo employye db si no hacerlo antes aqui
            invoiceDb.setEmployee(employee);

            List<LineInvoice> lines = updateLinesInvoice(invoiceDb.getLinesInvoice(), invoice.getLinesInvoice());

            invoiceDb.setLinesInvoice(lines);
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
            invoiceDb.getLinesInvoice().forEach(line -> serviceLine.delete(line.getId()));
            repository.delete(invoiceDb);
        });

        return optionalInvoice;
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice getInvoiceDb(Invoice invoice) {
        return repository.findById(invoice.getId()).orElseThrow();
    }

    @Override
    public List<LineInvoice> updateLinesInvoice(List<LineInvoice> linesDb, List<LineInvoice> newLines) {
        Set<Long> newLineIds = newLines.stream()
                .map(LineInvoice::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Eliminar líneas que ya no están en newLines
        linesDb.stream()
                .filter(lineDb -> !newLineIds.contains(lineDb.getId()))
                .forEach(lineDb -> {
                    serviceLine.delete(lineDb.getId());
                    System.out.println("Deleted line with ID: " + lineDb.getId());
                });

        // Actualizar o guardar líneas nuevas
        List<LineInvoice> finalLineInvoices = newLines.stream()
                .map(line -> {
                    if (line.getId() != null) {
                        // Actualizar línea existente
                        return serviceLine.update(line.getId(), line)
                                .orElseGet(() -> {
                                    // Log de error si la actualización falla
                                    System.err.println("Failed to update line with ID: " + line.getId());
                                    return line; // Devolver la línea sin cambios en caso de error
                                });
                    } else {
                        // Guardar nueva línea
                        LineInvoice savedLine = serviceLine.save(line);
                        return savedLine;
                    }
                })
                .collect(Collectors.toList());

        return finalLineInvoices;
    }

}
