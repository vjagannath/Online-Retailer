import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class OrderHandlerServlet extends HttpServlet
{

public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
	PrintWriter out = response.getWriter();
	String htmlContent = "";
	
	HttpSession session = request.getSession();
	String loggedInBy = (String)session.getAttribute("userId");
	String loggedInFor = (String)session.getAttribute("userIdForOrder");
	
	ShoppingCart cart;
	synchronized(session)
	{
		cart = (ShoppingCart)session.getAttribute("shoppingCart");
		// New visitors get a fresh shopping cart
		// Previous visitors keep using their existing cart
		if(cart == null)
		{
			cart = new ShoppingCart();
			session.setAttribute("shoppingCart", cart);
		}
		
		Integer productId = Integer.parseInt(request.getParameter("productId"));
		String submitId = request.getParameter("submitId");
		
	    if (productId != null) 
		{
	    	switch(submitId)
	    	{
		    	case "AddToCart" : htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\addorder.html");
		    		cart.addItem(productId); 
		    		break;
		    	case "DeleteFromCart" : htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\deleteorder.html");
		    		cart.deleteItem(productId); 
		    	break;
		    	case "Add" : htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\login.html");
		    		cart.addItem(productId);
		    	break;
		    	case "Delete" : htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\login.html");
		    		cart.deleteItem(productId); 
		    	break;
	    	}
	    }
	}	
	out.println(htmlContent);
  }
}
