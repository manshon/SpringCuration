package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.QuoteUrl;

@Repository
public interface QuoteUrlRepository extends CrudRepository<QuoteUrl, Long> {

}
