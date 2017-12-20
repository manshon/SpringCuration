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
 * Servlet implementation class MyArticle
 */
@WebServlet("/MyArticle")
public class MyArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyArticle() {
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

//			記事をもっているかチェックする
			if(CurationHelper.checkHasArticle(user)) {
				ArrayList<ArticleBeans> articleList = ArticleDao.findArticleRelatedUser(user);
				request.setAttribute("user", user);
				request.setAttribute("articleList", articleList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/myArticle.jsp");
				dispatcher.forward(request, response);
			}else {
				String msg = "記事は投稿されていません";
				request.setAttribute("msg", msg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user.jsp");
				dispatcher.forward(request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}


	}


}
