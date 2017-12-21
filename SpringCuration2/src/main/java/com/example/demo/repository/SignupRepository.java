package com.example.demo.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface SignupRepository extends CrudRepository<User, Long> {

    List<User> findByOrderByIdDesc(Pageable pageable);

}

