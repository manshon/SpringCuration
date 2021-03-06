package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Article;
import com.example.demo.model.ArticleTags;
import com.example.demo.model.Comment;
import com.example.demo.model.Community;
import com.example.demo.model.QuoteUrl;
import com.example.demo.model.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ArticleTagsRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.QuoteUrlRepository;
import com.example.demo.repository.UserRepository;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommunityRepository communityRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ArticleTagsRepository articleTagsRepository;

	@Autowired
	private QuoteUrlRepository urlRepository;


	@Transactional
	public List<Article> findByUserId(Long id) {
		List<Article> articleList = articleRepository.findByContributorId(id);
		return articleList;
	}

	@Transactional
	public void registerArticle(String title, String tags, String urls, Long conditions, String content, Long userId,
			Long communityId) {
		User user = userRepository.findById(userId);
		Community community = communityRepository.findById(communityId);

		Article article = new Article(title, content, conditions);
		article.setContributorId(userId);
		article.setCommunityId(communityId);
		article.setLikes(0L);
		Set<ArticleTags> articleTags = new HashSet<ArticleTags>();
		Set<QuoteUrl> quoteUrls = new HashSet<QuoteUrl>();

		// tagsが存在するとき追加
		if (tags != null && !tags.equals("")) {
			String[] tagArray = tags.split("[,、/／　\t\\s]+");
			for (String tag : tagArray) {
				articleTags.add(new ArticleTags(tag));
			}
			article.setTags(articleTags);
		}

		// urlsが存在するとき追加
		if (urls != null && !urls.equals("")) {
			String[] urlArray = urls.split("[\n]+");
			for (String url : urlArray) {
				quoteUrls.add(new QuoteUrl(url));
			}
			article.setQuoteUrl(quoteUrls);
		}

		article.setBelongCommunity(community);
		articleRepository.save(article);
		community.addArticle(article);
		communityRepository.save(community);
		user.addAdminArticle(article);
		userRepository.save(user);
	}

	@Transactional
	public void updateArticle(Long articleId, String title, String tags, String urls, Long conditions, String content) {
		Article article = articleRepository.findOne(articleId);
		Community community = article.getBelongCommunity();
		boolean communityRemoveArticle = community.getArticles().remove(article);
		User user = article.getAdminUser();
		boolean userRemoveArticle = user.getAdminArticles().remove(article);

		Set<ArticleTags> articleTags = new HashSet<ArticleTags>();
		Set<QuoteUrl> quoteUrls = new HashSet<QuoteUrl>();

		// tagsが存在するとき追加
		if (tags != null && !tags.equals("")) {
			String[] tagArray = tags.split("[,、/／　\t\\s]+");
			for (String tag : tagArray) {
				articleTags.add(new ArticleTags(tag));
			}
			article.setTags(articleTags);
		}

		// urlsが存在するとき追加
		if (urls != null && !urls.equals("")) {
			String[] urlArray = urls.split("[\n]+");
			for (String url : urlArray) {
				quoteUrls.add(new QuoteUrl(url));
			}
			article.setQuoteUrl(quoteUrls);
		}

		article.setTitle(title);
		article.setConditions(conditions);
		article.setContent(content);
		article.setUpdatedDate(new java.util.Date());

		articleRepository.save(article);
		community.addArticle(article);
		communityRepository.save(community);
		user.addAdminArticle(article);
		userRepository.save(user);

	}

	@Modifying
	@Transactional
	public void deleteArticle(Long articleId) {
		Article article = articleRepository.findOne(articleId);
		Community community = article.getBelongCommunity();
		List<Comment> comments = commentRepository.findByArticleIdOrderByCreatedDateDesc(articleId);
		for(Comment comment: comments) {

		}
		boolean communityRemoveArticle = community.getArticles().remove(article);
		User adminUser = article.getAdminUser();
		boolean userRemoveArticle = adminUser.getAdminArticles().remove(article);
		Set<User> likeUsers = article.getLikeUsers();
		for(User tempUser: likeUsers) {
			tempUser.getLikeArticles().remove(article);
		}
//		List<Comment> comment = new ArrayList<Comment>(article.getComment());
//		boolean commentRemoveArticle = comment.remove(article);

//		communityRepository.save(community);
		articleRepository.delete(article);
//		commentRepository.delete(comment);
//		userRepository.save(user);

	}

	@Transactional
	public void likeAndUnlikeArticle(Long articleId, Long userId) {
		Article article = articleRepository.findOne(articleId);
		User user = userRepository.findOne(userId);
		boolean isLike = isLikeArticle(articleId, userId);

		// いいねしているかどうかで条件分岐
		if(!isLike) {
			article.addLikeUser(user);
			article.setLikes(article.getLikes() + 1);
			user.addLikeArticles(article);
		}else {
			article.getLikeUsers().remove(user);
			article.setLikes(article.getLikes() - 1);
			user.getLikeArticles().remove(article);
		}
		articleRepository.save(article);
		userRepository.save(user);
	}

	@Transactional
	public boolean isLikeArticle(Long articleId, Long userId) {
		Article article = articleRepository.findOne(articleId);
		boolean isLike = false;

		// userがarticleをいいねしているかチェック
		for(User tempUser : article.getLikeUsers()) {
			if(tempUser.getId() == userId) {
				isLike = true;
				break;
			}
		}
		return isLike;
	}

	@Transactional
	public void addComment(Long userId, Long articleId, String content) {
		Article article = articleRepository.findOne(articleId);
		User user = userRepository.findOne(userId);
		Comment comment = new Comment();
		comment.setArticleId(articleId);
		comment.setUserId(userId);
		comment.setContent(content);
		article.addComment(comment);
//		user.addComment(comment);

		articleRepository.save(article);

	}

}
