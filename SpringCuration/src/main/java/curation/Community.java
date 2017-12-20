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
 * Servlet implementation class Community
 */
@WebServlet("/Community")
public class Community extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Community() {
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
			ArrayList<CommunityBeans> communityList = new ArrayList<CommunityBeans>();
			ArrayList<CommunityBeans> adminCommunityList = new ArrayList<CommunityBeans>();
//			userに紐付いたcommunityをリストにする
			communityList = CommunityDao.findAllFollowCommunityByUser(user);
			adminCommunityList = CommunityDao.findMyCommunityByUser(user);
//			ユーザーがコミュニティを持っていなければユーザー画面へ遷移する
			if(!CommunityDao.checkAllFollowCommunityByUser(user)) {
				response.sendRedirect("SearchCommunity");
				return;
			}
//			いいねボタンを押されて来たときに実行される。いいねボタンの文字
//			String like = (session.getAttribute("like") != null) ? (String) CurationHelper.cutSessionAttribute(session, "like"): "いいね" ;

//			sessionにcommunityIdがあるとき実行
			int communityId = (session.getAttribute("communityId") != null) ? (int) CurationHelper.cutSessionAttribute(session, "communityId") : 0;
			if(communityId == 0) {
				String communityIdString = request.getParameter("communityId");
				int firstCommunityId = communityList.get(0).getId();
				if(communityIdString != null && !communityIdString.equals("")) {
					communityId = Integer.parseInt(communityIdString);
				}else {
					communityId = firstCommunityId;
				}
			}

//			communityに紐付いたarticleをリストにする
			ArrayList<ArticleBeans> articleList = ArticleDao.findArticleByCommunityId(communityId);
//			いいねしているかチェック
			for(ArticleBeans article: articleList) {
				boolean likeType = ArticleDao.checkLikeArticle(user.getId(), article.getId());
				article.setLikeType(likeType);
			}

			request.setAttribute("selectedCommunityId", communityId);
			request.setAttribute("communityId", communityId);
			request.setAttribute("articleList", articleList);
			request.setAttribute("user", user);
			request.setAttribute("communityList", communityList);
			request.setAttribute("adminCommunityList", adminCommunityList);
			RequestDispatcher dispatcer = request.getRequestDispatcher("/WEB-INF/jsp/community.jsp");
			dispatcer.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}


	}


}
