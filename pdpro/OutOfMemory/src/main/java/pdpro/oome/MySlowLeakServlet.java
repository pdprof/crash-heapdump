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
 public class MySlowLeakServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public MySlowLeakServlet() {
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

		System.out.println("> MySlowLeakServlet.doTask()");
		System.out.println("D MySlowLeakServlet.doTask() request.getParamter(\"size and loop and time\")");
		int size = 1000000;
		int loop = 1000;
		int time = 100;
		try { size = Integer.parseInt(request.getParameter("size")); } catch (Exception e) {}
		try { loop = Integer.parseInt(request.getParameter("loop")); } catch (Exception e) {}
		try { time = Integer.parseInt(request.getParameter("time")); } catch (Exception e) {}
		MyVector v = new MyVector();
		
		HttpSession session = request.getSession();
		session.setAttribute("v", v);
		int count = 0;
		
		
		out.println("<HTML><TITLE>MySlowLeakServlet</TITLE><BODY>");
		try {
			long now = System.currentTimeMillis();
			while(true) {
				long tmp = System.currentTimeMillis();
				if (tmp - now >= (2000 + time)) {
					throw new Exception("Took 2 seconds");
				} else {
					now = tmp;
					for(int i=0; i<loop; i++) {
						byte[] buf1 = new byte[size];
					}
					byte[] buf = new byte[size];
					v.add(buf);
					Thread.sleep(time);
					count = count + 1;
				}
			}
			
		} catch (OutOfMemoryError t) {
			v.clear();
			System.out.println("D MySlowLeakServlet.doTask() OutOfMemoryError");
			System.out.println("D MySlowLeakServlet.doTask() - " + count + " x sleep over time is required to get OutOfMemoryError");
			out.println("<H2>OutOfMemoryError</H2>");
			out.println("<PRE>");
			t.printStackTrace(out);
			out.println("</PRE>");
			out.println("buffer size = " + size + " bytes<BR>");
			out.println("loop count  = " + loop + " times<BR>");
			out.println("sleep time  = " + time + " milliseconds<BR>");
			out.println("</BODY></HTML>");
			System.out.println("< MySlowLeakServlet.doTask()");
			throw t;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			v.clear();
			System.out.println("D MMySlowLeakServlet.doTask() Performance down");
			out.println("<H2>Performance down</H2>");
			out.println("<PRE>");
			e.printStackTrace(out);
			out.println("</PRE>");
			out.println("buffer size = " + size + " bytes<BR>");
			out.println("loop count  = " + loop + " times<BR>");
			out.println("sleep time  = " + time + " milliseconds<BR>");
			out.println("</BODY></HTML>");
			System.out.println("< MySlowLeakServlet.doTask()");
		}

	} 
	
}