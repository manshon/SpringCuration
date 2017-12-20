package curation;

import java.io.IOException;
import java.sql.SQLException;

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
 * Servlet implementation class Signin
 */
@WebServlet("/Signin")
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		if(session.getAttribute("user") != null) {
			response.sendRedirect("Community");
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/signin.jsp");
			dispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		password = CurationHelper.encryption(password);

		HttpSession session = request.getSession();

		try {
			UserBeans user = UserDao.findUserbyName(userName);
			if(user == null) {
				String msg = "入力が間違っています";
				request.setAttribute("msg",msg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/signin.jsp");
				dispatcher.forward(request, response);
			}else {
				if(!user.getPassword().equals(password)) {
					String msg = "パスワードが異なります";
					request.setAttribute("msg", msg);
					System.out.println(user.getPassword());
					System.out.println(password);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/signin.jsp");
					dispatcher.forward(request, response);
				}else {
					session.setAttribute("user", user);
					response.sendRedirect("Community");
				}

			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
