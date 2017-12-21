import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class OrderViewServlet extends HttpServlet
{
	
	Order order;
	HashMap<Integer, ProductAccessory> overallAccessories; 

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException
    {
	    response.setContentType("text/html");
		String id = request.getParameter("id");				
				
		HttpSession session = request.getSession();
		ShoppingCart cart;
		ShoppingCart updatedShoppingCart;
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
			
			String accessoryIdString = request.getParameter("accessoryId");
			Integer accessoryId = null;
			if(accessoryIdString != null)
			{
				accessoryId = Integer.parseInt(accessoryIdString);
			}
			String submitId = request.getParameter("submitId");
			
		    if (accessoryId != null) 
			{
		    	switch(submitId)
		    	{ 
			    	case "Add" : cart.addItem(accessoryId);
			    	break;
			    	case "Delete" : cart.deleteItem(accessoryId); 
			    	break;
		    	}
		    }
		}
		
		PrintWriter out = response.getWriter();				
		displayProductDetails(out, cart);
		
		
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException
  {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();	
	    
	    HttpSession session = request.getSession();	    
		String userIdForOrder = request.getParameter("userId");
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		if(userIdForOrder == null || !DataStore.getUsers().containsKey(userIdForOrder))
		{
			String htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\salesman.html");
	    	out.println(htmlContent); 
		}
		session.setAttribute("userIdForOrder", userIdForOrder);
		ShoppingCart cart = (ShoppingCart)session.getAttribute("shoppingCart");
		
		if(orderId != null)
		{
			session.setAttribute("orderId", orderId);
			User user = DataStore.getUsers().get(userIdForOrder);
			if(user.getOrders().containsKey(orderId))
			{
				Order order = user.getOrders().get(orderId);
				updateOrderInCart(order.getOrderItems(), cart.getItems());
				user.getOrders().remove(orderId);
			}
		}
		
		displayProductDetails(out, cart);
  }
	
	public void updateOrderInCart(HashMap<Integer, Integer> previousItems, HashMap<Integer, Integer> cartList)
	{
		// Update the order items if any change was made to item in new cart
		for(Map.Entry<Integer, Integer> entry : previousItems.entrySet())
		{
			if(!cartList.containsKey(entry.getKey()))
			{
				cartList.put(entry.getKey(), entry.getValue());
			}
			else
			{
				cartList.put(entry.getKey(), cartList.get(entry.getKey()) + entry.getValue());
			}
		}
	}

	public void displayProductDetails(PrintWriter out, ShoppingCart cart)
	{
		  // Add header and navigation bar content
		  printHeader(out);
		
		  printBody(out, cart.getItems());
		  
		  printFooter(out);
	}

  public void printBody(PrintWriter out, HashMap<Integer, Integer> cartList)
  {		
	int totalAmount = 0;
	//Product details
	HashMap<Integer, Product> products = new HashMap<Integer, Product>();
	
	//Accessory details
	HashMap<Integer, ProductAccessory> productAccessories = new HashMap<Integer, ProductAccessory>();
	
	for(Map.Entry<Integer, Integer> entry : cartList.entrySet())
	{
		if(DataStore.getAllProducts().containsKey(entry.getKey()))
		{
			products.put(entry.getKey(), DataStore.getAllProducts().get(entry.getKey()));
		}
		else if(DataStore.getAllProductAccessories().containsKey(entry.getKey()))
		{
			productAccessories.put(entry.getKey(), DataStore.getAllProductAccessories().get(entry.getKey()));
		}
	}
			
    out.println("	  <div id=\"width\">                                                 ");
    out.println("        <article>                                                       ");
    out.println("           <h3>Order Confirmation</h3>									 ");
    out.println("        </article>                                                      ");
    out.println("        <article>                                                       ");
    out.println("           <form method=\"get\" action=\"CheckOutServlet\">             ");
    out.println("                <table style=\"color:white; box-shadow: 0px 0px 30px red\">");
    out.println("					<tr style=\"color:red\">					   		 "); 
    out.println("  						<td>ProductName</td><td>ProductPrice</td><td>ProducQuantity</td><td>ProductDiscount</td><td>ProductRebate</td><td>ProductTotal</td>");
    out.println("					</tr>														"); 
    for(Map.Entry<Integer, Product> entry : products.entrySet())
    {    	
      out.println("<tr>																	");
      Product product = entry.getValue();
      int ItemPrice = cartList.get(product.getProductId()) * (product.getProductPrice()-product.getProductRetailerDiscount() - product.getProductManufacturerRebate());
      out.println("  <td>" + product.getProductName() + "</td>							");
      out.println("  <td>" + product.getProductPrice() + "</td>							");
      out.println("  <td>" + cartList.get(product.getProductId()) + "</td>		        ");
      out.println("  <td>" + product.getProductRetailerDiscount() + "</td>				");
      out.println("  <td>" + product.getProductManufacturerRebate() +"</td>				");
      out.println("  <td>" + ItemPrice + "</td>							");
      out.println("</tr>   																");
      totalAmount = totalAmount + ItemPrice;
    }
    for(Map.Entry<Integer, ProductAccessory> entry : productAccessories.entrySet())
    {    	
      out.println("<tr>																	");
      ProductAccessory accessory = entry.getValue();
      int ItemPrice = cartList.get(accessory.getProductId()) * (accessory.getProductPrice()-accessory.getProductRetailerDiscount() - accessory.getProductManufacturerRebate());
      out.println("  <td>" + accessory.getProductName() + "</td>							");
      out.println("  <td>" + accessory.getProductPrice() + "</td>							");
      out.println("  <td>" + cartList.get(accessory.getProductId()) + "</td>		        ");
      out.println("  <td>" + accessory.getProductRetailerDiscount() + "</td>				");
      out.println("  <td>" + accessory.getProductManufacturerRebate() +"</td>				");
      out.println("  <td>" + ItemPrice + "</td>							");
      out.println("</tr>   																");
      totalAmount = totalAmount + ItemPrice;
    }
    out.println(" <tr>																	");
    out.println(" <td>TotalAmount</td><td>"+ totalAmount +"</td>						");
    out.println(" </tr>																	");
    out.println(" <tr><td><input style=\"color:white\" type=\"submit\" value=\"Check Out\"/></td></tr> ");
    out.println(" 			     </table> 												");
    out.println(" 			</form> 													");
    out.println("        </article>                                                     ");

    printAccessories(out, products);
    out.println("      </div>                                                           ");
  }

  public void printAccessories(PrintWriter out, HashMap<Integer, Product> products)
  {	  
	  overallAccessories = new HashMap<Integer, ProductAccessory>();
	  for(Map.Entry<Integer, Product> entry : products.entrySet())
	  {
		  for(Map.Entry<Integer, ProductAccessory> productAccessoryEntry : entry.getValue().getProductAccessories().entrySet())
		  {
			  if(!overallAccessories.containsKey(productAccessoryEntry.getValue().getProductId()))
			  {
				  overallAccessories.put(productAccessoryEntry.getValue().getProductId(), productAccessoryEntry.getValue());
			  }
		  }
	  }
	  out.println(" <br/><br/><article>                                                       		    ");
	  //out.println("<form method=\"get\" action=\"OrderViewServlet\">             						");
	  out.println("	 <div id=\"myCarousel\" class=\"carousel slide\" data-ride=\"carousel\">            ");
	  out.println("	  	<ol class=\"carousel-indicators\">                                              ");
	  
	  for(int i = 0; i < (int)Math.ceil((double)overallAccessories.size()/3); i++)
	  {
		  if(i==0)
		  {
			  out.println("	    	<li data-target=\"#myCarousel\" data-slide-to=\""+i+"\" class=\"active\">></li>    ");
		  }
		  else
		  {
			  out.println("	    	<li data-target=\"#myCarousel\" data-slide-to=\""+i+"\"></li>    ");
		  }
	  }
	  out.println("	  	</ol>                                                                           ");
	  out.println("	  	<div class=\"carousel-inner\">				               ");
	  
	  List<ProductAccessory> accessories = new ArrayList<ProductAccessory>(overallAccessories.values());
	  	  
	  for(int i = 0; i < (int)Math.ceil((double)accessories.size()/3); i++)
	  {
		  if(i==0)
		  {
			  out.println("	    	<div class=\"item active\">                                                   ");
		  }
		  else
		  {
			  out.println("	    	<div class=\"item\">                                                  		  ");
		  }
		  out.println("	      		<div class=\"row\">                                                         ");
		  for(int j = 0; j < 3; j++)
		  {
			  if((i+j) < accessories.size())
			  {
				  out.println("<form method=\"get\" action=\"OrderViewServlet\">             						");
				  out.println("	<div class=\"col-xs-4\" style=\"text-align:center\">                                                         		");
				  out.println("	  <img width=\"150px\" height=\"150px\" src=\"images/"+accessories.get(i+j).getProductImage()+"\" /><br/>	");
				  out.println("   <lable>Price: "+accessories.get(i+j).getProductPrice()+"</label>&nbsp");
				  out.println("	  <input type=\"hidden\" name=\"accessoryId\" value=\"" +accessories.get(i+j).getProductId()+ "\"/></td></tr>	");
				  out.println("	  <input style=\"background-color:grey; color:white\" name=\"submitId\" type=\"submit\" value=\"Add\"/>							");
				  out.println("	  <input style=\"background-color:grey; color:white\" name=\"submitId\" type=\"submit\" value=\"Delete\"/>							");
				  out.println("	</div>                                                                       			");
				  out.println("</form>             																	");
			  }
		  }
		  out.println("	      		</div>                                                                       ");
		  out.println("	    	</div>                                                                       ");		
		  i = i+2;
	  }
	  
	  out.println("	  	</div>                                                                          ");
	  out.println("	  	<a class=\"left carousel-control\" href=\"#myCarousel\" data-slide=\"prev\">    ");
	  out.println("	    	<span class=\"glyphicon glyphicon-chevron-left\"></span>                      ");
	  out.println("	    	<span class=\"sr-only\">Previous</span>                                       ");
	  out.println("	  	</a>                                                                            ");
	  out.println("	  	<a class=\"right carousel-control\" href=\"#myCarousel\" data-slide=\"next\">   ");
	  out.println("	    	<span class=\"glyphicon glyphicon-chevron-right\"></span>                     ");
	  out.println("	    	<span class=\"sr-only\">Next</span>                                           ");
	  out.println("	  	</a>                                                                            ");
	  out.println("	</div>                                                                            	");  
	  //out.println("</form>             																	");
	  out.println("</article>                                                       					");
  }
  
  public void printHeader(PrintWriter out)
  {
    out.println("<!doctype html>                                                                                    ");
    out.println("  <html>                                                                                             ");
    out.println("   <head>                                                                                             ");
    out.println("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                         ");
    out.println("    <title>Order Confirmation</title>                                                                  ");
    out.println("    <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                              	");
    out.println("    <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />	");
    out.println("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\"> ");
    out.println("    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script> 		");
    out.println("    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script> 		");    
    out.println("	 <style>"																						);
	out.println("      body {"																						);
	out.println("        color:white;"																				);
	out.println("        padding:10px 20px 10px 10px;"																);
	out.println("        border:1px solid black;"																	);
	out.println("        margin:50px auto;"																			);
	out.println("        box-shadow: 0px 0px 30px gray;"															);
	out.println("       }"																							);
	out.println("	 </style>"																							);
    out.println("  </head>                                                                                          ");
    out.println("  <body>                                                                    						            ");
    out.println("  <div id=\"container\">                                                    						            ");
    out.println("    <header>                                                                						            ");
    out.println("    	<div class=\"width\">                                                						              ");
    out.println("    		<h1><a href=\"/\">Smart<span>Portables</span></a></h1>           						                ");
    out.println("        <h3>The best ever deal you can get</h3>                             						            ");
    out.println("      </div>                                                                						            ");
    out.println("    </header>                                                               						            ");
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