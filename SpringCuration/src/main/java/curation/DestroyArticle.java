package curation;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import dao.ArticleDao;

/**
 * Servlet implementation class DestoryArticle
 */
@WebServlet("/DestroyArticle")
public class DestroyArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DestroyArticle() {
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

			request.setAttribute("articleId", articleId);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/destroyArticle.jsp");
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

			ArticleDao.deleteArticle(articleId);
			if(ArticleDao.checkHasTagsByArticleId(articleId)) {
				ArticleDao.deleteArticle(articleId);
			}
			if(ArticleDao.checkHasQuoteByArticleId(articleId)) {
				ArticleDao.deleteArticleTags(articleId);
			}
			if(ArticleDao.checkHasCommentByArticleId(articleId)) {
				ArticleDao.deleteCommentByArticleId(articleId);

			response.sendRedirect("MyArticle");
		}

		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}
	}

}
