package pdpro.crash;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MySigSegvServlet
 */
public class MySigSegvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MySigSegvServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doTask(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doTask(request, response);
	}
	
	public void doTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("> MySigSegvServlet.doTask()");
		MySigSegv sig = new MySigSegv();
		sig.callSigSegvJNI();
		System.out.println("< MySigSegvServlet.doTask()");
	}

}
