package com.example.demo.Service;
import java.util.List;

import com.example.demo.Model.*;
import com.example.demo.Notification.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.NotificationRepository;

@Service
public class NotificationService
{
    @Autowired
    NotificationRepository notificationRepository;

    public void sendEmailToUsers(Measurement measurement) throws Exception {
        //Make Email message
        Notification notification = new Notification();

        notification.setSubject("Upozorenje od poplava za " + measurement.getRiverRegion().getRiver().getName()
                + " - " + measurement.getRiverRegion().getRegion().getName() + " područje");

        notification.setMessage("Ovim putem želimo Vas obavjestiti o potencijalnoj opasnosti" +
                " za područje " + measurement.getRiverRegion().getRiver().getName()
                + " - " + measurement.getRiverRegion().getRegion().getName() + ".");

        //Catch users which are signed to dangerous region and have notification tag on
        List<User> users = notificationRepository.findUserByRegionAndNotification(measurement.getRiverRegion().getRegion().getId());

        //Foreach user send email
        for (User user : users){
            notification.setUser(user);

            //save notification
            notificationRepository.save(notification);

            //send notification
            SendEmail.sendMail(notification);
        }
    }
}