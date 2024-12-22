package pdpro.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyExceptionServlet
 */
public class MyExceptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyExceptionServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		System.out.println("> MyExceptionServlet.doTask()");
		System.out.println("D MyExceptionServlet.doTask() request.getParamter(\"exception\")");
		String exception = request.getParameter("exception");
		System.out.println("D MyExceptionServlet.doTask() exception = " + exception);
		
		/*
		 * Change behavior depends on the exception
		 */
		Exception e = callException(exception);
		
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<BODY>");
		out.println("Completed ... <BR />");
		out.println("MyExceptionServlet.doTask() got exception");
		out.println("<pre>");
		e.printStackTrace(out);
		out.println("</pre>");
		out.println("</BODY>");
		out.println("</HTML>");
		System.out.println("< MyExceptionServlet.doTask()");
		
	}
	
	public Exception callException(String exception) {
		System.out.println("> MyExceptionServlet.callException()");
		
		try {
			if (exception.equalsIgnoreCase("ArrayIndexOutOfBoundsException")) {
				String string_array[] = {"ichi", "ni", "san", "shi", "go"};
				for (int i=0; i<6;i++) {
					// The access of string_array[6] causes ArrayIndexOutOfBoundsException
					System.out.println("D MyExceptionServlet.callException() - " + i + " : " + string_array[i]);
				}
			} else if(exception.equalsIgnoreCase("NullPointerException")) {
				String string_null = "This is null string.";
				string_null = null;
				string_null.indexOf("null"); // This operation causes NullPointerException
			} else if(exception.equalsIgnoreCase("NumberFormatException")) {
				Integer.parseInt(exception); // This operation causes NumberFormatException
			}
			
		} catch (Exception e) {
			System.out.println("< MyExceptionServlet.callException() - " + e.getMessage());
			e.printStackTrace(System.out);
			return e;
		}
		System.out.println("< MyExceptionServlet.callException() - exception did not occur");
		return new Exception("This is dummy Exception");
	}
	

}
