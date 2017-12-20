package curation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionManagement
 */
@WebServlet("/SessionManagement")
public class SessionManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionManagement() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (this.notExistsSession(req)) {
//            this.createSession(req);
        		try {
					resp.sendRedirect("Signin");
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
        }
//
//        if ("/delete".equals(req.getPathInfo())) {
//            this.deleteSession(req);
//        } else {
//            this.countUp(req);
//        }
    }

    private boolean notExistsSession(HttpServletRequest request) {
        return request.getSession(false) == null;
    }

    private void createSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("count", 0);

        System.out.printf("session is created. id=%s%n", session.getId());
    }

    private void deleteSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();

        System.out.printf("session is deleted. id=%s%n", session.getId());
    }

    private void countUp(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int count = (int)session.getAttribute("count");
        session.setAttribute("count", ++count);

        System.out.printf("count up. id=%s, count=%d%n", session.getId(), count);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
