import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class AddOrderServlet extends HttpServlet
{
	private String categoryString = "AllProducts";
	Order order;

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

    
	String localCategoryString = request.getParameter("category");
	if(localCategoryString != null)
	{
		categoryString = localCategoryString;
	}
		
	HttpSession session = request.getSession();
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
		    	case "AddToCart" : cart.addItem(productId);
		    	break;
		    	case "DeleteFromCart" : cart.deleteItem(productId); 
		    	break;
	    	}
	    }
	}
	
	displayProductDetails(out, categoryString);
  }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException
    {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    String userIdForOrder = request.getParameter("userId");
	    String htmlContent = "";
	    
	    if(userIdForOrder != null)
	    {
	    	HttpSession session = request.getSession();
	    	session.setAttribute("userIdForOrder", userIdForOrder);
	    	htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\orderview.html");
	    	out.println(htmlContent);
	    }
    }

	public void displayProductDetails(PrintWriter out, String category)
	{
	  // Add header and navigation bar content
	  printHeader(out);
	
	  printBodyBasedOnCategory(out, category);
	
	  // Add side bar and footer html content
	  printSideBar(out);
	  printFooter(out);
	}
	
	public void printBodyBasedOnCategory(PrintWriter out, String categoryString)
	  {
		if(categoryString == null)
		{
		  categoryString = "AllProducts";
		}  
		DataStore.Category category = DataStore.Category.valueOf(categoryString);
		switch(category)
	    {
	      case SmartWatch: printBody(out, DataStore.getSmartWatches());
	      break;
	      case Speaker: printBody(out, DataStore.getSpeakers());
	      break;
	      case HeadPhone: printBody(out, DataStore.getHeadPhones());
	      break;
	      case Phone: printBody(out, DataStore.getPhones());
	      break;
	      case Laptop: printBody(out, DataStore.getLaptops());
	      break;
	      case ExternalStorage: printBody(out, DataStore.getExternalStorages());
	      break;
	      default : printBody(out, DataStore.getAllProducts());
	      break;
	    }
	  }

	  public void printBody(PrintWriter out, HashMap<Integer, Product> products)
	  {
	    int count = 0;
	    out.println("	  <div id=\"body\" class=\"width\">                                                 ");
	    out.println("  		<section id=\"content\">                                                        ");
	    out.println("        <article>                                                                      ");
	    out.println("          <ul>                                                                         ");
	    out.println("            <li>                                                                       ");
	    out.println("              <form method=\"post\" action=\"OrderViewServlet\">                        ");
	    out.println("                <table style=\"color:white\">                                          ");
	    out.println(" 					<tr>																");
	    out.println(" 						<td>Adding Order for User Id</td>												");
	    out.println(" 					    <td><input style=\"color:white\" type=\"text\" name=\"userId\" required/></td>	");
	    out.println(" 					    <td><input style=\"color:white; float:right\" type=\"submit\" value=\"Place Order\"/></td> ");
	    out.println(" 					</tr>																");
	    out.println("				 </table>																");
	    out.println("			  </form>																	");
	    out.println("            </li>                                                                      ");
	    out.println("            <li>                                                                       ");
	    //out.println("              <form method=\"get\" action=\"AddOrderServlet\">                     ");
	    out.println("                <table style=\"color:white\">                                          ");
	    
	    
	    for(Map.Entry<Integer, Product> entry : products.entrySet())
	    {
	      // To ensure only two items are displayed in a row
	      if(count%2==0)
	      {
	        if(count > 0)
	        {
	          out.println("</tr>");
	        }
	        out.println("<tr>");
	      }
	      count++;
	      Product product = entry.getValue();
	      Integer productId = product.getProductId();
	      out.println("<td>                                                                                     ");
	      out.println(" <form method=\"get\" action=\"AddOrderServlet\">                     ");
	      out.println("  <table  style=\"box-shadow: 0px 0px 30px red\">                                        ");
	      out.println("    <tr><td><img width=\"150px\" height=\"150px\" src=\"images/"+product.getProductImage()+"\" /></td></tr>");
	      out.println("    <tr><td>Product Name</td><td>"+product.getProductName()+"</td></tr>                  ");
	      out.println("    <tr><td>Manufacturer</td><td>"+product.getProductManufacturer()+"</td></tr>          ");
	      out.println("    <tr><td>Price</td><td>"+product.getProductPrice()+"</td></tr>                        ");
	      out.println("    <tr><td><input type=\"hidden\" name=\"productId\" value=\"" +productId+ "\"/></td></tr>");
	      out.println("    <tr><td><input style=\"color:white\" type=\"submit\" name=\"submitId\" value=\"AddToCart\"/></td> ");
	      out.println("        <td><input style=\"color:white\" type=\"submit\" name=\"submitId\" value=\"DeleteFromCart\"/></td></tr> ");
	      out.println("  </table>                                                                               ");
	      out.println("</form>                                                                                  ");
	      out.println("</td>                                                                                    ");
	    }

	    out.println("                  </tr>                                                                                      ");
	    out.println("                </table>                                                                                     ");
	    //out.println("              </form>                                                                                        ");
	    out.println("            </li>                                                                                            ");
	    out.println("          </ul>                                                                                              ");
	    out.println("        </article>                                                                                           ");
	    out.println("      </section>                                                                                             ");
	  }

	  public void printHeader(PrintWriter out)
	  {
	    out.println("<!doctype html>                                                                                    ");
	    out.println("<html>                                                                                             ");
	    out.println("<head>                                                                                             ");
	    out.println(" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                         ");
	    out.println("    <title>SmartPortables</title>                                                                  ");
	    out.println("    <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                              ");
	    out.println("    <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />");
	    out.println("  </head>                                                                                          ");
	    out.println("  <body>                                                                    						            ");
	    out.println("  <div id=\"container\">                                                    						            ");
	    out.println("    <header>                                                                						            ");
	    out.println("    	<div class=\"width\">                                                						              ");
	    out.println("    		<h1><a href=\"/\">Smart<span>Portables</span></a></h1>           						                ");
	    out.println("        <h3>The best ever deal you can get</h3>                             						            ");
	    out.println("      </div>                                                                						            ");
	    out.println("      <a href=\"AddOrderServlet?id=1\" style='font-weight: bold;float:right'>Home</a>						    ");
	    out.println("    </header>                                                               						            ");
	    out.println("    <h2>Add Order</h2>                                                               						            ");
	  }

	  public void printSideBar(PrintWriter out)
	  {
	    out.println("  <aside class=\"sidebar\">                                                                             ");
	    out.println("    <ul>                                                                                              ");
	    out.println("      <li>                                                                                            ");
	    out.println("        <h4>Categories</h4>                                                                           ");
	    out.println("        <ul>                                                                                          ");
	    out.println("           <li class=\"\"><a href=\"AddOrderServlet?category=SmartWatch\">Smart Watch</a></li>           ");
	    out.println("           <li class=\"\"><a href=\"AddOrderServlet?category=Speaker\">Speaker</a></li>                  ");
	    out.println("           <li class=\"\"><a href=\"AddOrderServlet?category=HeadPhone\">Head Phone</a></li>             ");
	    out.println("           <li class=\"\"><a href=\"AddOrderServlet?category=Phone\">Phone</a></li>                      ");
	    out.println("           <li class=\"\"><a href=\"AddOrderServlet?category=Laptop\">Laptop</a></li>                    ");
	    out.println("           <li class=\"\"><a href=\"AddOrderServlet?category=ExternalStorage\">External Storage</a></li> ");
	    out.println("        </ul>                                                                                         ");
	    out.println("        <h4>Offer Zone</h4> 																																					");
		out.println("        <ul>																																													");
		out.println("        <li class=\"\"><a href=\"AddOrderServlet?id=1\">Retailer Warranty</a></li> 					");
		out.println("        </ul> 																																												");
		out.println("      </li>                                                                                           ");
	    out.println("    </ul>                                                                                             ");
	    out.println("  </aside>                                                                                            ");
	    out.println("  <div class=\"clear\"></div>                                                                         ");
	    out.println("  </div>																							                                                 ");
	  }

	  public void printFooter(PrintWriter out)
	  {
	    out.println("      <footer>                                           ");
	    out.println("        <div class=\"footer-content width\">             ");
	    out.println("          <ul>                                           ");
	    out.println("            <li><h2>Helpful Links</h2></li>              ");
	    out.println("            <li><a href=\"Home?id=2\">About Us</a></li>  ");
	    out.println("            <li><a href=\"Home?id=3\">Contact Us</a></li>");
	    out.println("          </ul>                                          ");
	    out.println("          <div class=\"clear\"></div>                    ");
	    out.println("        </div>                                           ");
	    out.println("        <div class=\"footer-bottom\">                    ");
	    out.println("          <p>&copy;2017 Developed by Vinay Jagannath</p> ");
	    out.println("        </div>                                           ");
	    out.println("      </footer>                                          ");
	    out.println("    </div>                                               ");
	    out.println("  </body>                                                ");
	    out.println("</html>                                                  ");
	  }
}