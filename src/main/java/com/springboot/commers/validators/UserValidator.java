package com.springboot.commers.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.springboot.commers.entities.User;

@Component
public class UserValidator implements Validator {

    // Expresión regular para validar contraseñas complejas
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!.]).{8,}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        // Validación de campos vacíos
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "El nombre es obligatorio.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "El email es obligatorio.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required", "La contraseña es obligatoria.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serialUser", "field.required", "El serial de usuario es obligatorio.");

        // Validación de formato de correo electrónico
        if (user.getEmail() != null && !user.getEmail().matches("^(.+)@(.+)$")) {
            errors.rejectValue("email", "field.invalid", "El email no tiene un formato válido.");
        }

        // Validación de contraseña
        if (user.getPassword() != null && !user.getPassword().matches(PASSWORD_PATTERN)) {
            errors.rejectValue("password", "field.invalid",
                    "La contraseña debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas, números y símbolos.");
        }

        // Validación de la lista de roles
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            errors.rejectValue("roles", "field.required", "Debe tener al menos un rol.");
        }
    }
}
