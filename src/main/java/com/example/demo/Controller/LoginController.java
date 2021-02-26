package com.example.demo.Controller;

import java.util.List;

import com.example.demo.Model.User;
import com.example.demo.Model.Region;
import com.example.demo.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.Service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//creating Controller
@Controller
public class LoginController
{
    //autowired the UserService class
    @Autowired
    UserService userService;

    @GetMapping("")
    private String home()
    {
        //provjera dal je korisnik ulogiran

        return "index";
    }

    @GetMapping("/login")
    private String login(Model model) {
        model.addAttribute("user", new User());

        return "login";
    }

    @GetMapping("/register")
    private String register(Model model) {

        List<Region> regions = userService.getAllRegions();

        model.addAttribute("regions", regions);

        User user = new User();

        user.setRegionId(regions.get(0).getId());

        model.addAttribute("user", user);

        return "register";
    }

    @GetMapping("/logout")
    public String logout (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }

    @PostMapping("/registerNewUser")
    public String registerNewUser(RedirectAttributes redirectAttributes, User user) {

        String redirection = "redirect:/login?";

        try{
            user.setPassword(userService.hashPassword(user.getPassword()));

            Role role = (userService.getRoleByName("Korisnik"));

            user.setRoleId(role.getId());

            userService.saveUser(user);

            redirection += "success";
        }
        catch (Exception ex){
            redirection += "failed";
        }

        return redirection;
    }
}