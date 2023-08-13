package com.adminpanel.controller;

import com.adminpanel.dto.UserRegistrationDto;
import com.adminpanel.model.User;
import com.adminpanel.repository.UserRepository;
import com.adminpanel.service.RoleService;
import com.adminpanel.service.RoleServiceImpl;
import com.adminpanel.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String saveEmployee(@ModelAttribute("user") UserRegistrationDto user) {
        // save employee to database
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
    public String showUsers(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("listUsers",users);
        return "addAdmin";
    }

    @GetMapping("/makeAdmin/{id}")
    public String makeUserAdmin(@PathVariable("id") Long userId) {
        roleService.updateRoleNameById(userId,"ROLE_ADMIN");

        return "redirect:/admin/addAdmin";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUserForm(@PathVariable("id") Long userId, Model model) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        model.addAttribute("user", user);
        model.addAttribute("userId", userId); // Add the userId to be used in the form action
        return "updateUser";
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Long userId, @ModelAttribute("user") UserRegistrationDto updatedUser) {
        userService.updateUser(userId, updatedUser);

        return "redirect:/admin/listUsers"; // Redirect with success parameter
    }

}
