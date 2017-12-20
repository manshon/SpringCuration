package curation;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ArticleBeans;
import beans.CommunityBeans;
import beans.UserBeans;
import dao.ArticleDao;
import dao.CommunityDao;

/**
 * Servlet implementation class SearchArticle
 */
@WebServlet("/SearchArticle")
public class SearchArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {
			UserBeans user = (UserBeans) session.getAttribute("user");
			String keywords;
			int communityId;
			if(session.getAttribute("keywords") != null && !session.getAttribute("keywords").equals("")) {
				keywords = (String) CurationHelper.cutSessionAttribute(session, "keywords");
			}else {
				keywords = request.getParameter("keywords");
			}
			if(session.getAttribute("communityId") != null) {
				communityId = (int) CurationHelper.cutSessionAttribute(session, "communityId");
			}else {
				communityId = Integer.parseInt(request.getParameter("communityId"));
			}
			ArrayList<ArticleBeans> articleList = new ArrayList<ArticleBeans>();

			if(keywords != null && !keywords.equals("")) {
				String[] keywordList = keywords.split("[,、/／\t\\s]+");
				articleList = ArticleDao.searchArticleRelatedCommunityId(communityId, keywordList);
			}else {
				articleList = ArticleDao.findArticleByCommunityId(communityId);
			}
//			いいねしているかチェック
			for(ArticleBeans article: articleList) {
				boolean likeType = ArticleDao.checkLikeArticle(user.getId(), article.getId());
				article.setLikeType(likeType);
			}

			ArrayList<CommunityBeans> communityList = CommunityDao.findAllFollowCommunityByUser(user);
			ArrayList<CommunityBeans> adminCommunityList = CommunityDao.findMyCommunityByUser(user);

			request.setAttribute("keywords", keywords);
			request.setAttribute("communityId", communityId);
			request.setAttribute("selectedCommunityId", communityId);
			request.setAttribute("communityList", communityList);
			request.setAttribute("adminCommunityList", adminCommunityList);
			request.setAttribute("articleList", articleList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/searchArticle.jsp");
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}
	}


}
