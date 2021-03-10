package com.example.demo.Controller;

import com.example.demo.CustomModel.CustomMeasurement;
import com.example.demo.Model.*;
import com.example.demo.Repository.MeasurementRepository;
import com.example.demo.Service.MeasurementService;
import com.example.demo.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

//creating Controller
@Controller
public class AppController
{
    //autowired the UserService class
    @Autowired
    UserService userService;
    //autowired the Measurement class
    @Autowired
    MeasurementService measurementService;
    //autowired the Notification class
    @Autowired
    NotificationService notificationService;

    @GetMapping("")
    private String home(Model model) throws Exception {

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

    @GetMapping("/admin/newMeasurement")
    public String newMeasurement(Model model, int id){

        User user = userService.findByEmail(userService.getCurrentUser());

        if(!userService.isAdmin(user)){
            return measurements(model, id);
        }

        model.addAttribute("hideNav", true);
        model.addAttribute("content", "modalView");

        RiverRegion riverRegion = userService.getRiverRegion(id);

        String titles = "Dodaj mjerenje za " + riverRegion.getRiver().getName() + " - " + riverRegion.getRegion().getName();

        CustomMeasurement customMeasurement = new CustomMeasurement();
        customMeasurement.setRiverRegionId(riverRegion.getId());
        model.addAttribute("measurement", customMeasurement);

        model.addAttribute("sectionTitle",titles);

        model.addAttribute("pageTitle", "Najava poplava - " + titles);

        return "index";
    }

    @GetMapping("/admin/editMeasurement")
    public String editMeasurement(Model model, int id) throws Exception{

        User user = userService.findByEmail(userService.getCurrentUser());

        if(!userService.isAdmin(user)){
            return home(model);
        }

        model.addAttribute("hideNav", true);
        model.addAttribute("content", "modalView");

        Measurement measurement = measurementService.getMeasurement(id);

        CustomMeasurement customMeasurement = new CustomMeasurement(measurement, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        customMeasurement.setValue(customMeasurement.getValue().replace(" cm", ""));
        model.addAttribute("measurement", customMeasurement);

        RiverRegion riverRegion = measurement.getRiverRegion();

        String titles = "Uredi mjerenje za " + riverRegion.getRiver().getName() + " - " + riverRegion.getRegion().getName();

        model.addAttribute("sectionTitle",titles);

        model.addAttribute("pageTitle", "Najava poplava - " + titles);

        return "index";
    }

    @PostMapping("/updateMeasurement")
    public String updateMeasurement(Model model, CustomMeasurement customMeasurement) throws Exception {

        User user = userService.findByEmail(userService.getCurrentUser());

        if(!userService.isAdmin(user)){
            return home(model);
        }

        Measurement measurement = new Measurement(customMeasurement);

        RiverRegion riverRegion = userService.getRiverRegion(customMeasurement.getRiverRegionId());

        measurement.setRiverRegion(riverRegion);

        //update measurement table in DB
        measurementService.saveMeasurement(measurement);

        //send email to users who have "newsletter tag" and users who is signed to that region if measurement is "dangerous"
        if(riverRegion.getUpperLevel().compareTo(measurement.getValue()) != 1 && isSameDay(new java.util.Date(measurement.getDate().getTime()), new Date())){
            notificationService.sendEmailToUsers(measurement);
        }

        return "redirect:/?close";
    }

    @PostMapping("/admin/deleteMeasurement")
    public ResponseEntity deleteMeasurement(int id){
        User user = userService.findByEmail(userService.getCurrentUser());

        if(!userService.isAdmin(user)){
            return ResponseEntity.notFound().build();
        }

        measurementService.deleteMeasurement(measurementService.getMeasurement(id));

        return ResponseEntity.ok().build();
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }
}