import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class CheckOutServlet extends HttpServlet
{
	boolean addOrderSuccessful = false;

public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    
    // The below check is if user clicked on Home, and to check who did check out - user or salesman
    String id = request.getParameter("id");
    String userIdForOrder = (String)session.getAttribute("userIdForOrder");
    String userId = (String)session.getAttribute("userId");
    if(id != null && userId.equals(userIdForOrder))
    {
    	String htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\login.html");
        out.println(htmlContent);
    	return;
    }
    if(id != null && !userId.equals(userIdForOrder))
    {
    	String htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\salesman.html");
        out.println(htmlContent);
    	return;
    }
    String htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\checkout.html");
    out.println(htmlContent);
  }

public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    
    String userName = request.getParameter("userName");
    String address = request.getParameter("userAddress");
    String creditCard = request.getParameter("userCreditcard");
    
    HttpSession session = request.getSession();
	String userIdForOrder = (String)session.getAttribute("userIdForOrder");
	ShoppingCart cart = (ShoppingCart)session.getAttribute("shoppingCart");	
	
	
    Order order = new Order(cart.getItems());
    order.setAddress(address);
    
    if(DataStore.getUsers().containsKey(userIdForOrder))
    {    	
    	addOrderSuccessful = true;
    	
		// Add order for the user
		DataStore.AddOrderForUser(userIdForOrder, order);	    
	    
	    // Remove the cart from the session
		session.removeAttribute("shoppingCart");
    }
    
    displayOrderConfirmation(out, order);
  }

 public void displayOrderConfirmation(PrintWriter out, Order order)
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
	 if(!addOrderSuccessful)
	 {
		 out.println("      <h1 style=\"text-align:center\">Order Not Confirmed : User does not exist</h1>              ");			 
	 }
	 else
	 {		
		 addOrderSuccessful = false;
		 out.println("      <h1 style=\"text-align:center\">Order Confirmed</h1>                                         ");
		 out.println("      <h3 style=\"text-align:center\"> Order Id : " + order.getOrderId() + "</h3>            		 ");
		 out.println("      <h3 style=\"text-align:center\"> Order Delivery date : " + order.getDeliveryDate() + "</h3>  ");		  
	 }
	 out.println("      <a href=\"CheckOutServlet?id=1\" style='font-weight: bold;float:right'>Home</a>                 ");
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
