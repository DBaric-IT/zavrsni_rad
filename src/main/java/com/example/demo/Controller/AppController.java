package com.example.demo.Controller;

import com.example.demo.CustomModel.CustomMeasurement;
import com.example.demo.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;

//creating Controller
@Controller
public class AppController
{
    //autowired the UserService class
    @Autowired
    UserService userService;

    @GetMapping("")
    private String home(Model model) {
        User user = userService.findByEmail(userService.getCurrentUser());

        model.addAttribute("currentUser", user);

        model.addAttribute("mapFeatures",   userService.generateMapFeature(userService.isAdmin(user)));

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

    @GetMapping("/measurements")
    private String measurements(Model model, int id) {

        model.addAttribute("hideNav", true);
        model.addAttribute("content", "measurements");

        RiverRegion riverRegion = userService.getRiverRegion(id);
        model.addAttribute("measurements", CustomMeasurement.getMeasurementList(riverRegion));

        model.addAttribute("pageTitle", "Rezultati mjerenja za " + riverRegion.getRiver().getName() + " - " + riverRegion.getRegion().getName());

        return "index";
    }

    @GetMapping("/admin/measurements")
    private String adminMeasurements(Model model, int id) {
        User user = userService.findByEmail(userService.getCurrentUser());

        if(!userService.isAdmin(user)){
            return measurements(model, id);
        }

        RiverRegion riverRegion = userService.getRiverRegion(id);
        model.addAttribute("measurements", CustomMeasurement.getMeasurementList(riverRegion));

        model.addAttribute("currentUser", user);
        model.addAttribute("content", "measurements");
        model.addAttribute("pageTitle", "Najava poplava - Uredi mjerenja za " + riverRegion.getRiver().getName() + " - " + riverRegion.getRegion().getName());

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