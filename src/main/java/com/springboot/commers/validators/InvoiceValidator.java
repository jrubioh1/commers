package com.springboot.commers.validators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.springboot.commers.entities.Invoice;


@Component
public class InvoiceValidator implements Validator {

    private final LineInvoiceValidator lineInvoiceValidator;

    //@Autowired
    public InvoiceValidator(LineInvoiceValidator lineInvoiceValidator) {
        this.lineInvoiceValidator = lineInvoiceValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Invoice.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Invoice invoice = (Invoice) target;

      

        // Validar que el total de la factura no sea nulo o negativo
        if (invoice.getWhole() == null) {
            errors.rejectValue("whole", "field.required", "El total de la factura es obligatorio.");
        } else if (invoice.getWhole() < 0) {
            errors.rejectValue("whole", "field.invalid", "El total de la factura no puede ser negativo.");
        }

       

        // Validar que el cliente no sea nulo
        if (invoice.getClient() == null) {
            errors.rejectValue("client", "field.required", "La factura debe tener un cliente asignado.");
        }
    }
}
