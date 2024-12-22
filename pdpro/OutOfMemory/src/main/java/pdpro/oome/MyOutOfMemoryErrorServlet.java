package pdpro.oome;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class for Servlet: OutOfMemoryErrorServlet
 *
 */
 public class MyOutOfMemoryErrorServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public MyOutOfMemoryErrorServlet() {
		super();
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
		PrintWriter out = response.getWriter();

		System.out.println("> MyOutOfMemoryErrorServlet.doTask()");
		System.out.println("D MyOutOfMemoryErrorServlet.doTask() request.getParamter(\"size and loop\")");
		int size = 1000000;
		int loop = 1000;
		try { size = Integer.parseInt(request.getParameter("size")); } catch (Exception e) {}
		try { loop = Integer.parseInt(request.getParameter("loop")); } catch (Exception e) {}
		MyVector v = new MyVector();
		
		HttpSession session = request.getSession();
		session.setAttribute("v", v);
		
		
		out.println("<HTML><TITLE>MyOutOfMemoryErrorServlet</TITLE><BODY>");
		try {
			long now = System.currentTimeMillis();
			for(int i=0; i<loop; i++) {
				long tmp = System.currentTimeMillis();
				if (tmp - now >= 2000) {
					throw new Exception("Took 2 seconds");
				} else {
					now = tmp;
					byte[] buf = new byte[size];
					v.add(buf);
				}
			}
			System.out.println("D MyOutOfMemoryErrorServlet.doTask() Normal End");
			out.println("<H2>Normal End</H2>");
			
		} catch (OutOfMemoryError t) {
			v.clear();
			System.out.println("D MyOutOfMemoryErrorServlet.doTask() OutOfMemoryError");
			out.println("<H2>OutOfMemoryError</H2>");
			out.println("<PRE>");
			t.printStackTrace(out);
			out.println("</PRE>");
			out.println("buffer size = " + size + " bytes<BR>");
			out.println("loop count  = " + loop + " times<BR>");
			out.println("total size  = " + size * loop + " bytes<BR>");
			out.println("</BODY></HTML>");
			System.out.println("< MyOutOfMemoryErrorServlet.doTask()");
			throw t;
		} catch (Exception e) {
			v.clear();
			System.out.println("D MyOutOfMemoryErrorServlet.doTask() Performance down");
			out.println("<H2>Performance down</H2>");
			out.println("<PRE>");
			e.printStackTrace(out);
			out.println("</PRE>");
			out.println("buffer size = " + size + " bytes<BR>");
			out.println("loop count  = " + loop + " times<BR>");
			out.println("total size  = " + size * loop + " bytes<BR>");
			out.println("</BODY></HTML>");
			System.out.println("< MyOutOfMemoryErrorServlet.doTask()");
		}
		out.println("buffer size = " + size + " bytes<BR>");
		out.println("loop count  = " + loop + " times<BR>");
		out.println("total size  = " + size * loop + " bytes<BR>");
		out.println("</BODY></HTML>");
		System.out.println("< MyOutOfMemoryErrorServlet.doTask()");
		
	}   
	
}