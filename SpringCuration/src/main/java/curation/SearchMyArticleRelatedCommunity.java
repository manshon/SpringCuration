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
 * Servlet implementation class SearchMyArticleRelatedCommunity
 */
@WebServlet("/SearchMyArticleRelatedCommunity")
public class SearchMyArticleRelatedCommunity extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMyArticleRelatedCommunity() {
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
			int communityId = Integer.parseInt(request.getParameter("communityId"));
			ArrayList<ArticleBeans> articleList = new ArrayList<ArticleBeans>();

			ArrayList<CommunityBeans> communityList = CommunityDao.findAllFollowCommunityByUser(user);
			ArrayList<CommunityBeans> adminCommunityList = CommunityDao.findMyCommunityByUser(user);

			articleList= ArticleDao.searchArticleByCommunityIdAndUserId(communityId, user.getId());

			request.setAttribute("communityId", communityId);
			request.setAttribute("selectedCommunityId", communityId);
			request.setAttribute("communityList", communityList);
			request.setAttribute("adminCommunityList", adminCommunityList);
			request.setAttribute("articleList", articleList);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/community.jsp");
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}
	}



}
