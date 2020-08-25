package com.example.test.practice.repository;

import com.example.test.practice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByPhoneNumberOrderByIdDesc(String phoneNumber);

    /* QueryMethod
    //select * from user where account = ? (String account)
    Optional<User> findByAccount(String account); // findBy 까지만 확인하고 where절로 인식

    Optional<User> findByEmail(String email);

    //select * from user where account = ? and email = ?
    Optional<User> findByAccountAndEmail(String account, String emile);

 */



}

