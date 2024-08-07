package com.springboot.commers.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.springboot.commers.entities.Product;

@Component
public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        // Validación de campos vacíos
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "El nombre del producto es obligatorio.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serial", "field.required", "El serial del producto es obligatorio.");

        // Validación del precio
        if (product.getPrice() == null) {
            errors.rejectValue("price", "field.required", "El precio del producto es obligatorio.");
        } else if (product.getPrice() <= 0) {
            errors.rejectValue("price", "field.invalid", "El precio del producto debe ser mayor a cero.");
        }

        // Validación del stock
        if (product.getStock() == null) {
            errors.rejectValue("stock", "field.required", "El stock del producto es obligatorio.");
        } else if (product.getStock() < 0) {
            errors.rejectValue("stock", "field.invalid", "El stock del producto no puede ser negativo.");
        }

      

    }
}
