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
import beans.UserBeans;
import dao.ArticleDao;

/**
 * Servlet implementation class SearchMyArticle
 */
@WebServlet("/SearchMyArticle")
public class SearchMyArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMyArticle() {
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
			String keywords = request.getParameter("keywords");
			ArrayList<ArticleBeans> articleList = new ArrayList<ArticleBeans>();

			if(keywords != null && !keywords.equals("")) {
				String[] keywordList = keywords.split("[,、/／\t\\s]+");
				articleList = ArticleDao.searchMyArticle(user.getUserName(), keywordList);
			}else {
				articleList = ArticleDao.findArticleRelatedUser(user);
			}

			request.setAttribute("articleList", articleList);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/myArticle.jsp");
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}
	}


}
