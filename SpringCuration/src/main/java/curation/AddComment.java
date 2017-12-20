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
 * Servlet implementation class AddComment
 */
@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComment() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {
			UserBeans user = (UserBeans) session.getAttribute("user");
			int articleId = Integer.parseInt(request.getParameter("articleId"));
			String comment =  request.getParameter("comment");
//			ArticleBeans article = ArticleDao.findArticleByArticleId(articleId);
//			ArrayList<CommentBeans> commentList = new ArrayList<CommentBeans>();

			ArticleDao.addComment(articleId, user.getId(), comment);

			// 記事にコメントがあるかチェック
//			if(ArticleDao.checkHasCommentByArticleId(articleId)) {
//				commentList = ArticleDao.findCommentListByArticleId(articleId);
//				for(CommentBeans comments: commentList) {
//					UserBeans contributor = UserDao.findUserById(user.getId());
//					comments.setContributor(contributor.getUserName());
//				}
//				request.setAttribute("commentList", commentList);
//			}
			session.setAttribute("articleId", articleId);
			response.sendRedirect("ArticleDetail");

//			request.setAttribute("commentList", commentList);
//			request.setAttribute("article", article);
//			request.setAttribute("user", user);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/articleDetail.jsp");
//			dispatcher.forward(request, response);

		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}
	}

}
