package curation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
 * Servlet implementation class Summary
 */
@WebServlet("/Summary")
public class Summary extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Summary() {
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
			CommunityBeans community = CommunityDao.findCommunityByCommunityId(communityId);

			request.setAttribute("community", community);
			request.setAttribute("user", user);
			RequestDispatcher dispatcer = request.getRequestDispatcher("/WEB-INF/jsp/summary.jsp");
			dispatcer.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {
			UserBeans user = (UserBeans) session.getAttribute("user");
			int communityId = Integer.parseInt(request.getParameter("communityId"));
			String title = request.getParameter("title");
			String tags = request.getParameter("tags");
			String quotes = request.getParameter("quotes");
			String condition = request.getParameter("condition");
			String content = request.getParameter("content");

			ArticleDao.createArticle(user, communityId, title, condition, content);
			ArticleBeans article = ArticleDao.findLatestArticle()	;

//			配列に変換
			if(tags != null && !tags.equals("")) {
				String[] tagArray = tags.split("[,、。／/　\\s\n]+");
				ArrayList<String> tagList = new ArrayList<String>(Arrays.asList(tagArray));
				ArticleDao.addArticleTags(article.getId(), tagList);
			}

			if(quotes != null && !quotes.equals("")) {
				String[] quoteArray = quotes.split("[\\n]+");
				ArrayList<String> quoteList = new ArrayList<String>(Arrays.asList(quoteArray));
				ArticleDao.addQuoteUrl(article.getId(), quoteList);
			}

			response.sendRedirect("MyArticle");
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}

	}

}
