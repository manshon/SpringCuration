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
 * Servlet implementation class EditArticle
 */
@WebServlet("/EditArticle")
public class EditArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditArticle() {
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
			int articleId = Integer.parseInt(request.getParameter("articleId"));
			ArticleBeans article = ArticleDao.findArticleByArticleId(articleId);
			ArrayList<String> tagList = new ArrayList<String>();
			ArrayList<String> quoteList = new ArrayList<String>();


			CommunityBeans community = CommunityDao.findCommunityByCommunityId(article.getCommunityId());
			String communityName = community.getName();

			if(ArticleDao.checkHasTagsByArticleId(articleId)) {
				tagList = ArticleDao.findTagsByArticleId(articleId);
				String tags = String.join(" ", tagList);
				request.setAttribute("tags", tags);
			}

			if(ArticleDao.checkHasQuoteByArticleId(articleId)) {
				quoteList = ArticleDao.findQuoteByArticleId(articleId);
				String quotes = String.join("\n", quoteList);
				request.setAttribute("quotes", quotes);
			}

			request.setAttribute("communityName", communityName);
			request.setAttribute("article", article);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editArticle.jsp");
			dispatcher.forward(request, response);
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
			int articleId = Integer.parseInt(request.getParameter("articleId"));
			String title = request.getParameter("title");
			String tags = request.getParameter("tags");
			String quotes = request.getParameter("quotes");
			String conditions = request.getParameter("conditions");
			String content = request.getParameter("content");

			ArticleDao.editArticle(articleId, title, conditions, content);
			if(tags != null && !tags.equals("")) {
				String[] tagArray = tags.split("[,、。／/　\\s\n]+");
				ArrayList<String> tagList = new ArrayList<String>(Arrays.asList(tagArray));
				ArticleDao.editArticleTags(articleId, tagList);
			}
			if(quotes != null && !quotes.equals("")) {
				String[] quoteArray = quotes.split("[\n]+");
				ArrayList<String> quoteList = new ArrayList<String>(Arrays.asList(quoteArray));
				ArticleDao.editQuote(articleId, quoteList);
			}

			response.sendRedirect("MyArticle");
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}


	}

}
