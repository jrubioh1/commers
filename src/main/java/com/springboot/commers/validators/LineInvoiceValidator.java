package com.springboot.commers.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.springboot.commers.entities.LineInvoice;

@Component
public class LineInvoiceValidator implements Validator {

    private final ProductValidator productValidator;

    //@Autowired
    public LineInvoiceValidator(ProductValidator productValidator) {
        this.productValidator = productValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LineInvoice.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LineInvoice lineInvoice = (LineInvoice) target;

        // Validar que la línea de factura tenga una factura asociada
        if (lineInvoice.getInvoice() == null) {
            errors.rejectValue("invoice", "field.required", "La línea debe estar asociada a una factura.");
        }

        // Validar que la línea de factura tenga un producto asociado
        if (lineInvoice.getProduct() == null) {
            errors.rejectValue("product", "field.required", "La línea debe tener un producto asociado.");
        } else {
            // Invocar al validador de productos
            errors.pushNestedPath("product");
            ValidationUtils.invokeValidator(productValidator, lineInvoice.getProduct(), errors);
            errors.popNestedPath();
        }

        // Validar que la cantidad sea al menos 1
        if (lineInvoice.getQuantity() == null || lineInvoice.getQuantity() < 1) {
            errors.rejectValue("quantity", "field.min", "La cantidad debe ser al menos 1.");
        }

        // Validar que el monto no sea nulo ni negativo
        if (lineInvoice.getAmount() == null) {
            errors.rejectValue("amount", "field.required", "El monto es obligatorio.");
        } else if (lineInvoice.getAmount() < 0) {
            errors.rejectValue("amount", "field.invalid", "El monto no puede ser negativo.");
        }
    }
}
