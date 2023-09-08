package com.adminpanel.controller;

import com.adminpanel.dto.UserRegistrationDto;
import com.adminpanel.exception.CustomException;
import com.adminpanel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/admin/addUserByAdmin")
public class AdminPrivilageController {

    private UserService userService;

    public AdminPrivilageController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) throws CustomException {
        System.out.println("Inside Save user mapping");
        // save employee to database
        String email = registrationDto.getEmail();
        System.out.println("mail id is : " + email);
        if(!isValidEmail(email)){
            System.out.println("Inside if statement");
            throw new CustomException("Mail id not valid");
        }
        userService.save(registrationDto);


        return "redirect:/admin/listUsers";
    }

    public  boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
