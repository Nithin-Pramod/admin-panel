package com.adminpanel.controller;

import com.adminpanel.dto.UserRegistrationDto;
import com.adminpanel.exception.CustomException;
import com.adminpanel.model.User;
import com.adminpanel.repository.UserRepository;
import com.adminpanel.service.RoleService;
import com.adminpanel.service.RoleServiceImpl;
import com.adminpanel.service.UserServiceImpl;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleService roleService;


    @GetMapping("/listUsers")
    public String showAllUsers(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("listUsers",users);
        return "listUser";
    }

    @GetMapping("/addUsers")
    public String AddUser(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "addUser";

    }

    @PostMapping("/saveUser")
    public String saveEmployee(@ModelAttribute("user") UserRegistrationDto user) throws CustomException{

        System.out.println("Inside Save user mapping");
        // save employee to database
        String email = user.getEmail();
        System.out.println("mail id is : " + email);
        if(!isValidEmail(email)){
            System.out.println("Inside if statement");
            throw new CustomException("Mail id not valid");
        }

        userService.save(user);
        return "redirect:/admin/listUsers";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {

        // call delete employee method
        this.userService.deleteUserById(id);
        return "redirect:/admin/listUsers";
    }

    @GetMapping("/addAdmin")
    public String showUsers(Model model, Authentication authentication){

        String username = authentication.getName();
        User user = userService.findByUsername(username);

        List<User> users = userRepository.findAll();
        model.addAttribute("listUsers",users);
        if (user.isBlocked()) {
            return "blocked";
        }
        return "addAdmin";
    }

    @GetMapping("/makeAdmin/{id}")
    public String makeUserAdmin(@PathVariable("id") Long userId) {
        roleService.updateRoleNameById(userId,"ROLE_ADMIN");

        return "redirect:/admin/addAdmin";
    }

    @GetMapping("/makeUser/{id}")
    public String makeUserByAdmin(@PathVariable("id") Long userId) {
        roleService.updateRoleNameById(userId,"ROLE_USER");
        return "redirect:/admin/addAdmin";
    }


    @GetMapping("/updateUser/{id}")
    public String updateUserForm(@PathVariable("id") Long userId, Model model, Authentication authentication) {

        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        model.addAttribute("user", user);
        model.addAttribute("userId", userId); // Add the userId to be used in the form action
        if (currentUser.isBlocked()){
            return "blocked";
        }
        return "updateUser";
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Long userId, @ModelAttribute("user") UserRegistrationDto updatedUser) {
        userService.updateUser(userId, updatedUser);

        return "redirect:/admin/listUsers"; // Redirect with success parameter
    }


    @GetMapping("/search")
    public String searchUsers(@RequestParam("queryType") String queryType,
                              @RequestParam("queryWord") String query,
                              Model model) {

        List<User> users = new ArrayList<>();

        if("firstName".equals(queryType)){
            users = userRepository.findByFirstNameContaining(query);
        } else if ("lastName".equals(queryType)) {
            users = userRepository.findByLastNameContaining(query);
        } else if ("email".equals(queryType)) {
            users = userRepository.findByEmailContaining(query);
        }

        for (User user : users) {
            System.out.println("User Found: " + user.getFirstName() + " " + user.getLastName() + " - " + user.getEmail());
        }
        if(users.isEmpty()){
            return "no_user_found";
        }
        model.addAttribute("listSearchUsers",users);
        return "user_search_results";
    }


    public  boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @GetMapping("/notValidated")
    public String showNotValdatedForm(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "notValidated";
    }

    @GetMapping("/somethingGoesWrong")
    public String showSGWPage(){
        return "somethingGoesWrong";
    }

    @GetMapping("/toggleBlockUser/{id}")
    public String blockUser(@PathVariable("id") Long userId, @AuthenticationPrincipal UserDetails currentUser) {

        System.out.println("in toggle block user");

        User user = userRepository.findById(userId)
                .orElse(null);
        if (!user.getEmail().equals(currentUser.getUsername())) {
            user.setBlocked(!user.isBlocked());
            System.out.println("user block status : " +  user.isBlocked());
            userRepository.save(user);
        }
        return "redirect:/admin/addAdmin";
    }

}
