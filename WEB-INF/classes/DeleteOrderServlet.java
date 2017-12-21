import java.io.*;
import java.util.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class DeleteOrderServlet extends HttpServlet
{
	boolean deleteOrderSuccessful = false;

public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    
    String id = request.getParameter("id");
    if(id != null && id.equals("1"))
    {
    	String htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\salesman.html");
    	out.println(htmlContent);
    	return;
    }
  }
	
public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    String userId = request.getParameter("userId");
    int orderId = Integer.parseInt(request.getParameter("orderId"));

    // Delete the order if the order with the given order id is present in the user order list
    if(DataStore.getUsers().containsKey(userId))
    {
      User user = DataStore.getUsers().get(userId);
      if(user.getOrders().containsKey(orderId))
      {      
    	  deleteOrderSuccessful = true;
    	  
    	  // Delete order from Hash Table and DataBase
    	  DataStore.DeleteOrderForUser(userId, user.getOrders().get(orderId));
      }
    }

    PrintWriter out = response.getWriter();
    //String htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\deleteorder.html");
    //out.println(htmlContent);
    displayDeleteOrderPage(out);
  }

public void displayDeleteOrderPage(PrintWriter out)
{
  out.println("	<!doctype html>                                                                                    ");
  out.println("	<html>                                                                                             ");
  out.println("	<head>                                                                                             ");
  out.println("	  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                        ");
  out.println("	  <title>Smart Portables</title>                                                                   ");
  out.println("	  <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                                ");
  out.println("	  <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />  ");
  out.println("	</head>                                                                                            ");
  out.println("	                                                                                                   ");
  out.println("	<body>                                                                                             ");
  out.println("	  <div id=\"container\">                                                                           ");
  out.println("	    <header>                                                                                       ");
  out.println("	      <div class=\"width\">                                                                        ");
  out.println("	    		<h1><a href=\"/\">Smart<span>Portables</span></a></h1>                                 ");
  out.println("	        <h3>The best ever deal you can get</h3>                                                    ");
  out.println("	      </div>                                                                                       ");
  out.println("	      <a href=\"DeleteOrderServlet?id=1\" style='font-weight: bold;float:right'>Home</a>           ");
  out.println("	    </header>                                                                                      ");
  if(!deleteOrderSuccessful)
  {
    out.println("	    <h2>Delete Order</h2>                                                                          ");
  }
  else
  {
    deleteOrderSuccessful = false;
    out.println("	    <h2>Order Deleted Successfully</h2>                                                                          ");
  }
  out.println("	    <form method=\"post\" action=\"DeleteOrderServlet\">                                           ");
  out.println("	      <table>                                                                                      ");
  out.println("	        <tr>                                                                                       ");
  out.println("	          <td>User Id </td>                                                                        ");
  out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"userId\" required/></td>           ");
  out.println("	        </tr>                                                                                      ");
  out.println("	        <tr>                                                                                       ");
  out.println("	          <td>Order Id to be deleted</td>                                                          ");
  out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"orderId\" required/></td>          ");
  out.println("	        </tr>                                                                                      ");
  out.println("	        <tr>                                                                                       ");
  out.println("	          <td><input style=\"color:white\" type=\"submit\" value=\"Delete\"/></td>                 ");
  out.println("	        </tr>                                                                                      ");
  out.println("	      </table>                                                                                     ");
  out.println("	    </form>                                                                                        ");
  out.println("	    <footer>                                                                                       ");
  out.println("	      <div class=\"footer-content width\">                                                         ");
  out.println("	        <ul>                                                                                       ");
  out.println("	          <li><h2>Helpful Links</h2></li>                                                          ");
  out.println("	          <li><a href=\"Home?id=2\">About Us</a></li>                                              ");
  out.println("	          <li><a href=\"Home?id=3\">Contact Us</a></li>                                            ");
  out.println("	        </ul>                                                                                      ");
  out.println("	        <div class=\"clear\"></div>                                                                ");
  out.println("	      </div>                                                                                       ");
  out.println("	      <div class=\"footer-bottom\">                                                                ");
  out.println("	        <p>&copy;2017 Developed by Vinay Jagannath</p>                                             ");
  out.println("	      </div>                                                                                       ");
  out.println("	    </footer>                                                                                      ");
  out.println("	  </div>                                                                                           ");
  out.println("	</body>                                                                                            ");
  out.println("	</html>                                                                                            ");
}
}
