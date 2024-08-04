package com.springboot.commers.helpers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.springboot.commers.entities.Invoice;
import com.springboot.commers.entities.Product;
import com.springboot.commers.services.IInvoiceService;
import com.springboot.commers.services.IProductsService;

@Component
public class InvoiceHelpers {


    private final IProductsService serviceProduct;

    private final IInvoiceService serviceInvoice; 
 

    public InvoiceHelpers(IProductsService serviceProduct, IInvoiceService serviceInvoice) {
        this.serviceProduct = serviceProduct;
        this.serviceInvoice = serviceInvoice;
    }


    public List<Product> listOfProductDb(List<Product> products){

        return products.stream()
    .map(product -> serviceProduct.findById(product.getId()).get())
    .collect(Collectors.toList());
    }


    public Product getProductDb(Product product){
        return serviceProduct.findById(product.getId()).get();
    }
    public Invoice getInvoiceDb(Invoice invoice){
        return serviceInvoice.findById(invoice.getId()).get();
    }

    public Integer fixedStockProduct( Long id, Integer quantity){
        Product productDb= serviceProduct.findById(id).get();
         Integer stock=productDb.getStock()+quantity;
        productDb.setStock(stock);
        serviceProduct.saveStock(productDb);

        return stock;
    }
    
    
}
