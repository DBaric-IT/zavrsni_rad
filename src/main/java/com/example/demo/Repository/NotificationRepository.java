package com.example.demo.Repository;

import com.example.demo.Model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface NotificationRepository extends CrudRepository<Notification, Integer>
{
    @Query("SELECT u FROM User u WHERE u.RegionId = ?1 AND u.Newsletter = true")
    public List<User> findUserByRegionAndNotification(int regionId);
}
