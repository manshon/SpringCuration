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

import beans.CommunityBeans;
import beans.UserBeans;
import dao.CommunityDao;

/**
 * Servlet implementation class EditCommunityDetail
 */
@WebServlet("/EditCommunityDetail")
public class EditCommunityDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCommunityDetail() {
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
			int communityId = Integer.parseInt(request.getParameter("communityId"));
			UserBeans user = (UserBeans) session.getAttribute("user");
			String stringTags = "";

			CommunityBeans community = CommunityDao.findCommunityByCommunityId(communityId);
			ArrayList<String> tagList = CommunityDao.findTagsRelatedCommunityId(communityId);
			if(!tagList.isEmpty()) {
				stringTags = String.join(",", tagList);
//				System.out.println("yes");
			}

			request.setAttribute("stringTags", stringTags);
			request.setAttribute("community", community);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editCommunityDetail.jsp");
			dispatcher.forward(request, response);
		}catch(Exception e){
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
			UserBeans user = (UserBeans) session.getAttribute("user");
			int communityId = Integer.parseInt(request.getParameter("communityId"));
			String tags = request.getParameter("tags");
			String content = request.getParameter("content");


//			内容が入力されているかチェック
			if(content == null || content.equals("")) {
				String msg = "入力が正しくありません";
				request.setAttribute("user",user);
				request.setAttribute("msg",msg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editCommunity.jsp");
				try {
					dispatcher.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}

			if(tags != null && !tags.equals("")) {
				String[] tagList = tags.split("[,、/／　\\t\\\\s]+");
				CommunityDao.editTags(tagList, content, communityId);

			}

			CommunityDao.editCommunity(content, communityId);
//			if(!tags.equals("") ) {
//				String[] tagList = tags.split("[,、/／　\t\\s]+");
//				CommunityBeans latestCommunity = CommunityDao.findLatestCommunity();
//				CommunityDao.addTags(latestCommunity, tagList);
//			}

			response.sendRedirect("MyCommunity");
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", e.toString());
			response.sendRedirect("Error");
		}


	}

}
