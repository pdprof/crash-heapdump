package pdpro.hang;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyDeadlockServlet
 */
public class MyDeadlockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyDeadlockServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doTask(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doTask(request,response);
	}

	public void doTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("> MyDeadlockServlet.doTask()");
		System.out.println("D MyDeadlockServlet.doTask() request.getParamter(\"lock sequence\")");
		String lock = request.getParameter("lock");
		System.out.println("D MyDeadlockServlet.doTask() lock " + lock);
		
		/*
		 * 
		 */
		doLockWork(lock);

		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<BODY>");
		out.println("Completed ... <BR />");
		out.println("MyDeadlockServlet.doTask() lock" + lock);
		out.println("</BODY>");
		out.println("</HTML>");
		System.out.println("< MyDeadlockServlet.doTask()");
	}

	public void doLockWork(String lock) {
		System.out.println("> MyDeadlockServlet.doLockWork()");
		if (lock.equalsIgnoreCase("AB")) {
			MyLockA a = new MyLockA();
			a.upCountAB();
		} else if (lock.equalsIgnoreCase("BA")) {
			MyLockB b = new MyLockB();
			b.upCountBA();
		}
		System.out.println("< MyDeadlockServlet.doLockWork()");
	}
}
