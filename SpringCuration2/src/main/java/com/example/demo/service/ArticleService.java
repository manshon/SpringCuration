package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Article;
import com.example.demo.repository.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Transactional
	public List<Article> findByUserId(Long id){
		List<Article> articleList = articleRepository.findByContributorId(id);
		return articleList;
	}
}
