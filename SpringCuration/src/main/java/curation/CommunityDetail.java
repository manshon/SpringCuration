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
 * Servlet implementation class CommunityDetail
 */
@WebServlet("/CommunityDetail")
public class CommunityDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunityDetail() {
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
			int communityId;
			if(session.getAttribute("communityId") != null) {
				communityId = (int) CurationHelper.cutSessionAttribute(session, "communityId");
			}else {
				communityId = Integer.parseInt(request.getParameter("communityId"));
			}

//			userがコミュニティをフォローしているかチェック
//			String btnType = CommunityDao.checkFollowCommunityByUser(user, communityId) ? "アンフォロー" : "フォロー";
			boolean isFollow = CommunityDao.checkFollowCommunityByUser(user, communityId);

//			画面下でコミュニティに紐付いた最新記事を5つ表示する記事
			CommunityBeans community = CommunityDao.findCommunityByCommunityId(communityId);
			ArrayList<ArticleBeans> articleList = ArticleDao.findArticleByCommunityId(communityId);
			ArrayList<ArticleBeans> latest5Article = new ArrayList<ArticleBeans>();
			for(int i=0;i<articleList.size();i++) {
				latest5Article.add(articleList.get(i));
				if(i >= 4) {
					break;
				}
			}

			request.setAttribute("isFollow", isFollow);
			request.setAttribute("articleList", latest5Article);
			request.setAttribute("community", community);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/communityDetail.jsp");
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}

	}


}
