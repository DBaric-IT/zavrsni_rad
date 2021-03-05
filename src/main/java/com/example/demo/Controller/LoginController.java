package com.example.demo.Controller;

import java.util.List;

import com.example.demo.Model.User;
import com.example.demo.Model.Region;
import com.example.demo.Model.Role;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.Service.UserService;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping("/login")
    private String login(Model model) {

        if(!userService.getCurrentUser().equals("anonymousUser")){
            return "redirect:/";
        }

        model.addAttribute("user", new User());

        return "login";
    }

    @GetMapping("/register")
    private String register(Model model, User user) {

        if(!userService.getCurrentUser().equals("anonymousUser")){
            return "redirect:/";
        }

        List<Region> regions = userService.getAllRegions();

        model.addAttribute("regions", regions);

        if(user.getName() == null) {
            user.setRegionId(regions.get(0).getId());

            model.addAttribute("error", false);
        }
        else {
            model.addAttribute("error", true);
        }

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
    public String registerNewUser(Model model, RedirectAttributes redirectAttributes, User user) {

        User checkForEmail = userService.findByEmail(user.getEmail());

        if(checkForEmail != null) {
            user.setPassword("");
            user.setEmail("");

            return register(model, user);
        }
        else {
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
}