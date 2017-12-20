package curation;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import dao.UserDao;

/**
 * Servlet implementation class EditUser
 */
@WebServlet("/EditUser")
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUser() {
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editUser.jsp");
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
		try {
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");

			if(password == null | password.equals("")) {
				String msg = "入力が正しくありません";
				request.setAttribute("msg", msg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editUser.jsp");
				dispatcher.forward(request, response);
			}

			if(confirmPassword == null | confirmPassword.equals("")) {
				String msg = "入力が正しくありません";
				request.setAttribute("msg", msg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editUser.jsp");
				dispatcher.forward(request, response);
			}

			if(!password.equals(confirmPassword)) {
				String msg = "パスワードが異なります";
				request.setAttribute("msg", msg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editUser.jsp");
				dispatcher.forward(request, response);
			}

			password = CurationHelper.encryption(password);
			UserBeans user = new UserBeans();
			user.setUserName(userName);
			user.setPassword(password);

			if(UserDao.editUser(user)) {
				response.sendRedirect("User");
			}else {
				String msg = "変更されませんでした";
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
