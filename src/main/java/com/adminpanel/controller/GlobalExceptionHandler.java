package com.adminpanel.controller;

import com.adminpanel.exception.CustomException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex) {
        ModelAndView modelAndView = new ModelAndView("redirect:/access-denied");
        return modelAndView;
    }


    @ExceptionHandler(CustomException.class)
    public ModelAndView handleCustomException(CustomException ex) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/notValidated");
        return modelAndView;
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ModelAndView handleDuplicateEntry(SQLIntegrityConstraintViolationException ex) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/somethingGoesWrong");
        return modelAndView;
    }

}
