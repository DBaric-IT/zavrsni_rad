package com.example.demo.Repository;

import com.example.demo.Model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Integer>
{
    @Query("SELECT u FROM Role u WHERE LOWER(u.Name) = LOWER(:name)")
    public List<Role> findRoleByName(@Param("name") String name);

    @Query("SELECT u FROM Region u")
    public List<Region> findAllRegions();

    @Query("SELECT u FROM User u WHERE LOWER(u.Email) = LOWER(?1)")
    public User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE lower(u.Email) = LOWER(?1) and u.Password = ?2")
    public User checkUserLogin(String email, String password);

    @Query("SELECT u FROM RiverRegion u")
    public List<RiverRegion> findAllRiverRegions();

    @Query("SELECT r FROM RiverRegion r WHERE r.Id = ?1")
    public RiverRegion getRiverRegion(int riverRegionId);

    @Query("SELECT LOWER(r.Name) FROM Role r WHERE r.Id = ?1")
    public String getRoleName(int id);
}
