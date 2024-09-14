package com.apress.prospring6.fifteen.controllers;

import com.apress.prospring6.fifteen.problem.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestErrorHandler {

    /*@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpStatus> handleNotFound(NotFoundException ex){
          return ResponseEntity.notFound().build();
     }
    */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleBadRequest(DataIntegrityViolationException ex){
       return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
