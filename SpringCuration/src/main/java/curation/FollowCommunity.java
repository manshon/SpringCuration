package curation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import dao.CommunityDao;

/**
 * Servlet implementation class FollowCommunity
 */
@WebServlet("/FollowCommunity")
public class FollowCommunity extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowCommunity() {
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
			int communityId = Integer.parseInt(request.getParameter("communityId"));
			UserBeans user = (UserBeans) session.getAttribute("user");

			if(!CommunityDao.checkFollowCommunityByUser(user, communityId)) {
				CommunityDao.followCommunity(user, communityId);
			}else {
				CommunityDao.unFollowCommunity(user, communityId);
			}

			session.setAttribute("communityId", communityId);
			response.sendRedirect("CommunityDetail");

		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}

	}

}
