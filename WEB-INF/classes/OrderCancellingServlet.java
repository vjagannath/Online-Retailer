import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class OrderCancellingServlet extends HttpServlet
{

public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    
    String htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\cancelorder.html");
    out.println(htmlContent);
  }

public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	int orderId = Integer.parseInt(request.getParameter("orderId"));
	HttpSession session = request.getSession();
	String userIdForOrder = (String)session.getAttribute("userIdForOrder");
	User user = DataStore.getUsers().get(userIdForOrder);
	Order order = user.getOrders().get(orderId);
	if(order != null)
	{
		Date currentDate = new Date();
		Date deliveryDate = null;
		
		try 
		{
			deliveryDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(order.getDeliveryDate());
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		long difference = currentDate.getTime() - deliveryDate.getTime();
	    int days =  (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
	    
	    if(days < 5)
	    {
	    	// Delete order
	    	DataStore.DeleteOrderForUser(userIdForOrder, user.getOrders().get(orderId));
	    	
			displayCancellingConfirmation(out, true);
	    }
	    else
	    {
	    	displayCancellingConfirmation(out, false);
	    }
	}
	else
	{	
		String htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\cancelorder.html");
		out.println(htmlContent);
	}
  }

 public void displayCancellingConfirmation(PrintWriter out, boolean canCancel)
 {
	 out.println("<!doctype html>                                                                                    ");
	 out.println("<html>                                                                                             ");
	 out.println("<head>                                                                                             ");
	 out.println("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                        ");
	 out.println("  <title>Smart Portables</title>                                                                   ");
	 out.println("  <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                                ");
	 out.println("  <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />  ");
	 out.println("</head>                                                                                            ");
	 out.println("<body>                                                                                             ");
	 out.println("  <div id=\"container\">                                                                           ");
	 out.println("    <header>                                                                                       ");
	 out.println("    	<div class=\"width\">                                                                        ");
	 out.println("    		<h1><a href=\"/\">Smart<span>Portables</span></a></h1>                                   ");
	 out.println("        <h3>The best ever deal you can get</h3>                                                    ");
	 out.println("      </div>                                                                                       ");
	 
	 if(canCancel)
	 {
		 out.println("      <h1 style=\"text-align:center\">Order Cancelled</h1>                                     ");
	 }
	 else
	 {
		 out.println("      <h1 style=\"text-align:center\">Order Cannot be Cancelled - Already shipped</h1>         ");
	 }
	 out.println("      <a href=\"LoginServlet?id=1\" style='font-weight: bold;float:right'>Home</a>                 ");
	 out.println("    </header>                                                                                      ");
	 out.println("    <footer>                                                                                       ");
	 out.println("      <div class=\"footer-content width\">                                                         ");
	 out.println("        <ul>                                                                                       ");
	 out.println("          <li><h2>Helpful Links</h2></li>                                                          ");
	 out.println("          <li><a href=\"Home?id=2\">About Us</a></li>                                              ");
	 out.println("          <li><a href=\"Home?id=3\">Contact Us</a></li>                                            ");
	 out.println("        </ul>                                                                                      ");
	 out.println("        <div class=\"clear\"></div>                                                                ");
	 out.println("      </div>                                                                                       ");
	 out.println("      <div class=\"footer-bottom\">                                                                ");
	 out.println("        <p>&copy;2017 Developed by Vinay Jagannath</p>                                             ");
	 out.println("      </div>                                                                                       ");
	 out.println("    </footer>                                                                                      ");
	 out.println("  </div>                                                                                           ");
	 out.println("</body>                                                                                            ");
	 out.println("</html>																							 ");
 }
}
