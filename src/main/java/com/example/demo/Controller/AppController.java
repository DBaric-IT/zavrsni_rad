package com.example.demo.Controller;

import com.example.demo.Model.Region;
import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//creating Controller
@Controller
public class AppController
{
    //autowired the UserService class
    @Autowired
    UserService userService;

    @GetMapping("")
    private String home(Model model) {
        model.addAttribute("currentUser", userService.findByEmail(userService.getCurrentUser()));

        model.addAttribute("mapFeatures",   userService.generateMapFeature());

        model.addAttribute("content", "home");

        model.addAttribute("pageTitle", "Najava poplava - Početna");

        return "index";
    }

    @GetMapping("/profile")
    private String profile(Model model) {
        model.addAttribute("currentUser", userService.findByEmail(userService.getCurrentUser()));

        model.addAttribute("content", "profile");

        model.addAttribute("pageTitle", "Najava poplava - Uredi profil");

        model.addAttribute("regions", userService.getAllRegions());

        return "index";
    }

    @PostMapping("/updateUser")
    public String registerNewUser(Model model, User user) {

        User currentUser = userService.findByEmail(userService.getCurrentUser());

        user.setId(currentUser.getId());
        user.setEmail(currentUser.getEmail());
        user.setRoleId(currentUser.getRoleId());

        user.setPassword(userService.hashPassword(user.getPassword()));

        userService.saveUser(user);

        return "redirect:/profile?success";
    }
}