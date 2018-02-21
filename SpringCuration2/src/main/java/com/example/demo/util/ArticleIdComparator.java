package com.example.demo.util;

import java.util.Comparator;

import com.example.demo.model.Article;

@SuppressWarnings("rawtypes")
public class ArticleIdComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	public int compare(Article art1, Article art2) {
	    if(art1.getCreatedDate().compareTo(art2.getCreatedDate()) < 0){
	        return -1;
	      }else if(art2.getCreatedDate().compareTo(art1.getCreatedDate()) < 0){
	        return 1;
	      }else{
	        return 0;
	      }
	    }
	}

