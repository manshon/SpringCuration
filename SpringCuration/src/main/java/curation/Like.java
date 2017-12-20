package curation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import dao.ArticleDao;

/**
 * Servlet implementation class Like
 */
@WebServlet("/Like")
public class Like extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Like() {
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
			String servletPath = request.getParameter("servletPath");
			int articleId = Integer.parseInt(request.getParameter("articleId"));

			if(servletPath.equals("ArticleDetail")) {
				session.setAttribute("articleId", articleId);
			}
			if(servletPath.equals("Community")) {
				int communityId = Integer.parseInt(request.getParameter("communityId"));
				session.setAttribute("communityId", communityId);
			}
			if(servletPath.equals("SearchArticle")) {
				int communityId = Integer.parseInt(request.getParameter("communityId"));
				session.setAttribute("communityId", communityId);
				String keywords = request.getParameter("keywords");
				session.setAttribute("keywords", keywords);
			}


			if(ArticleDao.checkLikeArticle(user.getId(), articleId)) {
				ArticleDao.unlikeArticle(user.getId(), articleId);
			}else {
				ArticleDao.likeArticle(user.getId(), articleId);
			}

			response.sendRedirect(servletPath);

		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}
	}


}
