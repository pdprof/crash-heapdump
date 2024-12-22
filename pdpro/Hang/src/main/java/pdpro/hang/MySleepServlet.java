package pdpro.hang;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyLoopServlet
 */
public class MySleepServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MySleepServlet() {
        super();
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
		System.out.println("> MySleepServet.doTask()");
		System.out.println("D MySleepServet.doTask() request.getParamter(\"sleeptime\")");
		int sleeptime = Integer.parseInt(request.getParameter("sleeptime")) * 1000;
		System.out.println("D MySleepServet.doTask() looptime is "  + sleeptime + " milliseconds");
		MySleep sleep = new MySleep();
		sleep.doSleep(sleeptime);
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<BODY>");
		out.println("Completed ... <BR />");
		out.println("MySleepServet.doTask() looptime is " + sleeptime + " seconds");
		out.println("</BODY>");
		out.println("</HTML>");
		System.out.println("< MySleepServet.doTask()");
	}
}
