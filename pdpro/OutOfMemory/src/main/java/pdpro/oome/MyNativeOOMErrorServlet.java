package pdpro.oome;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MyNativeOOMErrorServlet
 */
public class MyNativeOOMErrorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyNativeOOMErrorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doTask(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doTask(request, response);
	}   	
	
	protected void doTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		System.out.println("> MyOutOfMemoryErrorServlet.doTask()");
		System.out.println("D MyOutOfMemoryErrorServlet.doTask() request.getParamter(\"loop\")");
		int loop = 10000;
		try { loop = Integer.parseInt(request.getParameter("looptime")); } catch (Exception e) {}
		
		MyNativeOOME nat = new MyNativeOOME();
		nat.callMallocJNI(loop);
		
		PrintWriter out = response.getWriter();
		out.println("<HTML><TITLE>MyNativeOOMErrorServlet</TITLE><BODY>");
		out.println("loop count  = " + loop + " times<BR>");
		out.println("</BODY></HTML>");
		System.out.println("< MyNativeOOMErrorServlet.doTask()");
		
	}   

}
