package curation;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import form.SignupForm;


@RequestMapping("/signup")
@Controller
public class Signup {

//	@Autowired
//	private MessageService service;

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("form", new SignupForm());

		List<User> users = User.getRecentUsers(100);
		model.addAttribute("users", users);
		return "users";
	}
}

/**
 * Servlet implementation class Signup
 */
@WebServlet("/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp");
		dispatcher.forward(request,response);
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		UserBeans user = new UserBeans();

		if(UserDao.checkExistName(userName)) {
			String msg = "そのユーザー名は使用されています";
			this.failedSignup(msg, request, response);
		}

		if (userName == null || userName.equals("")){
			String msg = "名前を入力してください";
			this.failedSignup(msg, request, response);
			return;
		}
		if (password == null || password.equals("")){
			String msg = "パスワードを入力してください";
			this.failedSignup(msg, request, response);
			return;
		}
		if(!password.equals(confirmPassword)){
			String msg = "パスワードが異なります";
			this.failedSignup(msg, request, response);
			return;
		}

		password = CurationHelper.encryption(password);
		user.setUserName(userName);
		user.setPassword(password);
		if(UserDao.addUser(user)) {
			response.sendRedirect("Signin");
		}else {
			String msg = "";
			this.failedSignup(msg, request, response);
		}


	}

	private void failedSignup (String msg, HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		UserBeans user = new UserBeans();
		user.setUserName(userName);
		user.setPassword(password);
		request.setAttribute("user",user);
		request.setAttribute("msg",msg);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
