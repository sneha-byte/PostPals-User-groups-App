package com.userDatabase.userDatabase.controller;

import java.util.List;

import com.userDatabase.userDatabase.exception.UserNotFoundException;
import com.userDatabase.userDatabase.model.User;
import com.userDatabase.userDatabase.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.userDatabase.userDatabase.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class UserPageController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    // Show all members
    @GetMapping("/members")
    public String showMembersPage(HttpSession session, Model sample) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        try {
            List<User> users = userService.findAllUsers();
            sample.addAttribute("users", users);
            sample.addAttribute("user", user);
        } catch (Exception e) {
            sample.addAttribute("errorMessage", "Error finding users"); 
            e.printStackTrace();
        }  
        return "members";
    }
    
    // Filters user list in user repo using the input username 
    @GetMapping("/search-users")
    public String searchUsers(@RequestParam(required = false) String username, Model model) throws UserNotFoundException {
        List<User> users;
        if (username == null || username.trim().isEmpty()) {
            users = userService.findAllUsers(); 
        } else {
            users = userService.findByName(username); 
        }

        model.addAttribute("users", users);
        model.addAttribute("searchedUsername", username);
        return "members"; 
    }
}
	
