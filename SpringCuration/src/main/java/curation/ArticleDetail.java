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
 * Servlet implementation class ArticleDetail
 */
@WebServlet("/ArticleDetail")
public class ArticleDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticleDetail() {
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
			int articleId;
//			sessionにarticleIdがあればそれを代入し、なければrequestからとる条件式
			if(session.getAttribute("articleId") != null) {
				articleId = (int) CurationHelper.cutSessionAttribute(session, "articleId");
			}else {
				articleId = Integer.parseInt(request.getParameter("articleId"));
			}
//			記事の詳細を持ってくる
			ArticleBeans article = ArticleDao.findArticleByArticleId(articleId);
//			記事に紐付いたコメントリストを作成

			// Listを文字列に変換
			ArrayList<String> tagList = article.getTagList();
			String tagString = String.join(",", tagList);

			// いいねしているかチェック
			boolean likeType = ArticleDao.checkLikeArticle(user.getId(), articleId);

//			記事の投稿者がuser自身かチェック
			boolean isContributor = (user.getUserName().equals(article.getContributor())) ? true : false;
//			記事が編集されたかチェック
			boolean createdDateIsNotUpdateDate = (article.getCreateDate().compareTo(article.getUpdateDate())==0) ? false: true;

			request.setAttribute("likeType", likeType);
			request.setAttribute("tagString", tagString);
			request.setAttribute("createdDateIsNotUpdateDate", createdDateIsNotUpdateDate);
			request.setAttribute("isContributor", isContributor);
			request.setAttribute("article", article);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/articleDetail.jsp");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
