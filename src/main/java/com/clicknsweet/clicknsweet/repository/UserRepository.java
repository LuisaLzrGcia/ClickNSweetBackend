package com.clicknsweet.clicknsweet.repository;

import com.clicknsweet.clicknsweet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUserName(String userName);

    User findByPhone(String phone);

    List<User> findByFirstName(String firstName);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    List<User> findByRole_Id(Integer roleId);

    List<User> findByLastLoginAfter(LocalDateTime date);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    long countByRole_Id(Integer roleId);

}