package com.springboot.commers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Orders;
import com.springboot.commers.repositories.IOrdersRepository;
import com.springboot.commers.services.interfaces.IAddressService;
import com.springboot.commers.services.interfaces.IClientService;
import com.springboot.commers.services.interfaces.IInvoiceService;
import com.springboot.commers.services.interfaces.IOrdersService;
import com.springboot.commers.services.interfaces.IPaymentMethodService;
import com.springboot.commers.services.interfaces.IStatusOrderService;

public class OrdersServiceImpl implements IOrdersService {

    private final IOrdersRepository repository;
    private final IInvoiceService invoiceService;
    private final IClientService serviceClient;
    private final IPaymentMethodService paymentMethodService;
    private final IStatusOrderService statusOrderService;
    private final IAddressService addressService;

    public OrdersServiceImpl(IOrdersRepository repository, IClientService serviceClient, IInvoiceService invoiceService,
            IPaymentMethodService paymentMethodService, IStatusOrderService statusOrderService,
            IAddressService addressService) {
        this.repository = repository;
        this.invoiceService = invoiceService;
        this.serviceClient = serviceClient;
        this.paymentMethodService = paymentMethodService;
        this.statusOrderService = statusOrderService;
        this.addressService = addressService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Orders> findAll() {

        return (List<Orders>) repository.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Orders> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional()
    public Orders save(Orders order) {
        order.setClient(serviceClient.getClientDb(order.getClient()));
        order.setInvoice(invoiceService.findById(order.getInvoice().getId()).orElseThrow());
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod(paymentMethodService.findById(order.getPaymentMethod().getId()).orElseThrow());
        order.setStatusOrder(statusOrderService.findById(order.getStatusOrder().getId()).orElseThrow());
        order.setBillingAddress(addressService.findById(order.getBillingAddress().getId()).orElseThrow());
        order.setShippingAddress(addressService.findById(order.getShippingAddress().getId()).orElseThrow());
        return repository.save(order);

    }

    @Override
    @Transactional()
    public Optional<Orders> update(Long id, Orders order) {
        Optional<Orders> orderOptional= repository.findById(id);
        if(orderOptional.isPresent()){
            Orders orderDb= orderOptional.get();
            orderDb.setClient(serviceClient.getClientDb(order.getClient()));
            orderDb.setInvoice(invoiceService.findById(order.getInvoice().getId()).orElseThrow());
            orderDb.setOrderDate(LocalDateTime.now());
            orderDb.setPaymentMethod(paymentMethodService.findById(order.getPaymentMethod().getId()).orElseThrow());
            orderDb.setStatusOrder(statusOrderService.findById(order.getStatusOrder().getId()).orElseThrow());
            orderDb.setBillingAddress(addressService.findById(order.getBillingAddress().getId()).orElseThrow());
            orderDb.setShippingAddress(addressService.findById(order.getShippingAddress().getId()).orElseThrow());
            return Optional.of(repository.save(order));


        }
        return orderOptional;
    }

    @Override
    @Transactional()
    public Optional<Orders> delete(Long id) {
        Optional<Orders> ordersOptional = repository.findById(id);
        ordersOptional.ifPresent((orderDd) -> {
            repository.delete(orderDd);
        });
        return ordersOptional;
    }



}
