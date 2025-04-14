package edu.comillas.icai.gitt.pat.spring.p5.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * TODO#7 Hecho
 * Añade 2 tests unitarios adicionales que validen diferentes casos
 * (no variaciones del mismo caso) de registro con datos inválidos
 */

class RegisterRequestUnitTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidRequest() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.USER, "aaaaaaA1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(violations.isEmpty());
    }
    @Test
    public void testInvalidName() {
        // Given: Datos de registro con un nombre vacío
        RegisterRequest registro = new RegisterRequest(
                "",  // Nombre vacío
                "nombre@email.com",
                Role.USER, "aaaaaaA1");

        // When: Se valida el registro
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registro);

        // Then: Debe haber violaciones porque el nombre está vacío
        assertFalse(violations.isEmpty(), "El nombre no coincide con el formato esperado");  // Esperamos que haya violaciones
        assertEquals(1, violations.size());  // Debe haber una violación
        assertEquals("name", violations.iterator().next().getPropertyPath().toString()); // La violación debe ser en el campo "name"
    }
    @Test
    public void testInvalidEmail() {

        RegisterRequest registro = new RegisterRequest(
                "Hola","inigo-email.com",
                Role.ADMIN,"aaaaaaA1");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registro);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());  // Debe haber una violación
        assertEquals("email", violations.iterator().next().getPropertyPath().toString());
    }




}