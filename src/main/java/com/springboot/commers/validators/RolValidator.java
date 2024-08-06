package com.springboot.commers.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.springboot.commers.entities.Rol;

@Component
public class RolValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Rol.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Rol rol = (Rol) target;

        // Validación de campos vacíos
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "El nombre del rol es obligatorio.");

        // Validaciones adicionales podrían ir aquí, como la verificación de unicidad en la base de datos
        // Esta verificación debe realizarse dentro de los servicios o al persistir, no dentro del validador.
    }
}
