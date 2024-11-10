//package com.apress.prospring6.seventeen.boot.problem;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import java.nio.file.AccessDeniedException;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ModelAndView handle(NotFoundException ex) {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("problem",ex.getMessage());
//        mav.setViewName("error");
//        return mav;
//    }
//
//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ModelAndView notFound(HttpServletRequest req){
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("problem","Not Supported " + req.getRequestURI());
//        mav.setViewName("error");
//        return mav;
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ModelAndView forbidden(HttpServletRequest req){
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("problem","Method not allowed" + req.getRequestURI());
//        mav.setViewName("error");
//        return mav;
//    }
//}
