package com.example.demo.Service;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.demo.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.Repository.UserRepository;
@Service
public class UserService
{
    @Autowired
    UserRepository userRepository;
    //getting all users records
    public Role getRoleByName(String name)
    {
        return userRepository.findRoleByName(name).get(0);
    }

    //save or update user
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<Region> getAllRegions(){
        return userRepository.findAllRegions();
    }

    public String hashPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public String getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = "";

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }

    public String generateMapFeature(Boolean isAdmin) {
        List<RiverRegion> allRiverRegions = userRepository.findAllRiverRegions();

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        String mapFeature = "";

        for (RiverRegion riverRegion : allRiverRegions)
        {
            Measurement lastMeasurment = riverRegion.getMeasurements().get(riverRegion.getMeasurements().size() - 1);

            String adminOption = isAdmin ? "<br><span onclick=\"adminMeasurement(" + riverRegion.getId() + ")\" style=\"cursor: pointer; text-decoration: underline; color: red;\">Upravljaj mjerenjima</span>" : "";

            mapFeature += "{'type': 'Feature','properties': {'description':'<div style=\"color: black; min-width: 150px; min-height: 90px\"><b>" + riverRegion.getRiver().getName() + " - " + riverRegion.getRegion().getName() + "</b><br>Zadnje mjerenje:<br>" + lastMeasurment.getDate().toLocalDate().format(myFormatObj) + "<br>" + lastMeasurment.getValue() + " cm<br><span onclick=\"showLastMeasurements(" + riverRegion.getId() + ")\" style=\"cursor: pointer; text-decoration: underline\">Klikni za više</span>" + adminOption + "</div>','icon': '" + (riverRegion.getUpperLevel().compareTo(lastMeasurment.getValue()) == 1  ? "pulsing-dot-green" : "pulsing-dot-red" ) + "'},'geometry': {'type': 'Point','coordinates': [" + riverRegion.getLongitude() + ", " + riverRegion.getLatitude() + "]}},";
        }

        if(mapFeature.length() > 1) {
            mapFeature = mapFeature.substring(0, mapFeature.length() - 1);
        }

        return mapFeature;
    }

    public RiverRegion getRiverRegion(int riverRegionId){
        return userRepository.getRiverRegion(riverRegionId);
    }

    public Boolean isAdmin(User user){
        return userRepository.getRoleName(user.getRoleId()).equals("administrator");
    }
}




