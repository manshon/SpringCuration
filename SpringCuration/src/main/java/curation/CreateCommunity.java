package curation;

import java.io.IOException;

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
 * Servlet implementation class CreateCommunity
 */
@WebServlet("/CreateCommunity")
public class CreateCommunity extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCommunity() {
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

			request.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/createCommunity.jsp");
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
		UserBeans user = (UserBeans) session.getAttribute("user");
		String communityName = request.getParameter("communityName");
		String content = request.getParameter("content");
		String tags = request.getParameter("tags");
//		System.out.println(tags);

//		コミュニティ名が入力されているかチェック
		if(communityName == null || communityName.equals("")) {
			String msg = "入力が正しくありません";
			this.failedCreate(user, msg, request, response);
		}
//		内容が入力されているかチェック
		if(content == null || content.equals("")) {
			String msg = "入力が正しくありません";
			this.failedCreate(user, msg, request, response);
		}

		CommunityDao.createCommunity(communityName,content,user);
		if(!tags.equals("") ) {
			String[] tagList = tags.split("[,、/／　\t\\s]+");
			CommunityBeans latestCommunity = CommunityDao.findLatestCommunity();
			CommunityDao.addTags(latestCommunity, tagList);
		}


		response.sendRedirect("MyCommunity");
	}


	private void failedCreate (UserBeans user,String msg, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("user",user);
		request.setAttribute("msg",msg);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/createCommunity.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
