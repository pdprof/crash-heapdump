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
public class MyLoopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyLoopServlet() {
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
		System.out.println("> MyLoopServlet.doTask()");
		System.out.println("D MyLoopServlet.doTask() request.getParamter(\"looptime\")");
		int looptime = Integer.parseInt(request.getParameter("looptime"));
		System.out.println("D MyLoopServlet.doTask() looping " + looptime + " times");
		
		MyLoop loop = new MyLoop();
		int elapsed = loop.doLoop(looptime);
		
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<BODY>");
		out.println("Completed ... <BR />");
		out.println("MyLoopServlet.doTask() looping " + looptime + "  times / elapsed " + elapsed + " milliseconds");
		out.println("</BODY>");
		out.println("</HTML>");
		System.out.println("< MyLoopServlet.doTask()");
	}
}
