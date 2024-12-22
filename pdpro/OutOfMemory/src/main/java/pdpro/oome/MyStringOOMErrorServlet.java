package pdpro.oome;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MyStringOOMErrorServlet
 */
public class MyStringOOMErrorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyStringOOMErrorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

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

		System.out.println("> MyStringOOMErrorServlet.doTask()");
		System.out.println("D MyStringOOMErrorServlet.doTask() request.getParamter(\"looptime\")");
		int looptime = 1000;
		try { looptime = Integer.parseInt(request.getParameter("looptime")); } catch (Exception e) {}
		MyVector v = new MyVector();
		
		HttpSession session = request.getSession();
		session.setAttribute("v", v);
		String alphabets = "abcdefghijklmnopqrstuvwxyz";
		
		out.println("<HTML><TITLE>MyStringOOMErrorServlet</TITLE><BODY>");
		try {
			long now = System.currentTimeMillis();
			v.add(alphabets);
			for(int i=0; i<looptime; i++) {
				long tmp = System.currentTimeMillis();
				if (tmp - now >= 2000) {
					throw new Exception("Took 2 seconds");
				} else {
					now = tmp;
					alphabets = alphabets + alphabets;
				}
			}
			System.out.println("D MyStringOOMErrorServlet.doTask() Normal End");
			out.println("<H2>Normal End</H2>");
			
		} catch (OutOfMemoryError t) {
			v.clear();
			System.out.println("D MyStringOOMErrorServlet.doTask() OutOfMemoryError");
			out.println("<H2>OutOfMemoryError</H2>");
			out.println("<PRE>");
			t.printStackTrace(out);
			out.println("</PRE>");
			out.println("looptime count  = " + looptime + " times<BR>");
			out.println("string size = " + alphabets.getBytes().length + " bytes<BR>");
			out.println("</BODY></HTML>");
			System.out.println("< MyStringOOMErrorServlet.doTask()");
			throw t;
		} catch (Exception e) {
			v.clear();
			System.out.println("D MyStringOOMErrorServlet.doTask() Performance down");
			out.println("<H2>Performance down</H2>");
			out.println("<PRE>");
			e.printStackTrace(out);
			out.println("</PRE>");
			out.println("looptime count  = " + looptime + " times<BR>");
			out.println("string size = " + alphabets.getBytes().length + " bytes<BR>");
			out.println("</BODY></HTML>");
			System.out.println("< MyStringOOMErrorServlet.doTask()");
		}
		
		out.println("looptime count  = " + looptime + " times<BR>");
		out.println("string size = " + alphabets.getBytes().length + " bytes<BR>");
		out.println("</BODY></HTML>");
		System.out.println("< MyStringOOMErrorServlet.doTask()");
		
	}   

}
