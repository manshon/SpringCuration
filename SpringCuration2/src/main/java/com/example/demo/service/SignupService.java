//package com.example.demo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.demo.model.User;
//import com.example.demo.repository.SignupRepositoryImpl;
//
//@Service
//public class SignupService {
//
//    @Autowired
//    private SignupRepositoryImpl repository;
//
//
//    @Transactional
//    public void save(User user) {
//        repository.save(user);
//    }
//
//}