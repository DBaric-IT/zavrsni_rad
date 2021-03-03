package com.example.demo.Service;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Model.Region;
import com.example.demo.Model.RiverRegion;
import com.example.demo.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.Model.User;
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

    public String generateMapFeature() {
        List<RiverRegion> allRiverRegions = userRepository.findAllRiverRegions();

        String mapFeature = "";

        for (RiverRegion riverRegion : allRiverRegions)
        {
            mapFeature += "{'type': 'Feature','properties': {'description':'<div style=\"color: black; min-width: 150px; min-height: 90px\"><b>" + riverRegion.getRiver().getName() + " - " + riverRegion.getRegion().getName() + "</b><br>Zadnje mjerenje:<br>03.03.2021. 08:00 h<br>-225 cm<br><span onclick=\"prikaziPodatkePostaje(5, false)\" style=\"cursor: pointer; text-decoration: underline\">klikni za više</span></div>','icon': 'pulsing-dot-green'},'geometry': {'type': 'Point','coordinates': [" + riverRegion.getLongitude() + ", " + riverRegion.getLatitude() + "]}},";
        }

        if(mapFeature.length() > 1) {
            mapFeature = mapFeature.substring(0, mapFeature.length() - 1);
        }

        return mapFeature;
    }
}




