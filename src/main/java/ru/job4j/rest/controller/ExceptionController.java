package ru.job4j.rest.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;

@ControllerAdvice
public class ExceptionController {

    private final ObjectMapper objectMapper;


    public ExceptionController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void handleIAException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "Password or login is empty!");
            put("details", e.getMessage());
        }}));

    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public void handleCVException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "Password or login is empty!");
        }}));

    }
}
