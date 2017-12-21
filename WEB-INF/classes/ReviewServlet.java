import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class ReviewServlet extends HttpServlet
{
	
public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    
    Integer productId = null;
    String productName = null;
    String productType = null;
    Integer productPrice = null;
    String productRetailer = null;
    Integer productRetailerZip = null;
    String productRetailerCity = null;
    String productRetailerState = null;
    boolean productOnSale = false;
    String productManufacturer = null;
    boolean productManufacturerRebate = false;
    String userId = null;
    Integer userAge = null;
    String userGender = null;
    String userOccupation = null;
    Integer reviewRating = null;
    String reviewDate = null;
    String reviewText= null;
    
    boolean valueIsValid = true;
    try
    {
    	productId = Integer.parseInt(request.getParameter("productId"));
	    productName = request.getParameter("productName");
	    productType = request.getParameter("productType");    
    	productPrice = Integer.parseInt(request.getParameter("productPrice"));
	    productRetailer = request.getParameter("productRetailer");
	    productRetailerZip = Integer.parseInt(request.getParameter("productRetailerZip"));
	    productRetailerCity = request.getParameter("productRetailerCity");
	    productRetailerState = request.getParameter("productRetailerState");
	    if(request.getParameter("productOnSale").equals("Yes"))
	    {
	    	productOnSale = true;
	    }
	    else
	    {
	    	productOnSale = false;
	    }
	    productManufacturer = request.getParameter("productManufacturer");
	    if(request.getParameter("productManufacturerRebate").equals("Yes"))
	    {
	    	productManufacturerRebate = true;
	    }
	    else
	    {
	    	productManufacturerRebate = false;
	    }
	    userId = request.getParameter("userId");  
	    userAge = Integer.parseInt(request.getParameter("userAge"));
	    userGender = request.getParameter("userGender");  
	    userOccupation = request.getParameter("userOccupation");
	    reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
	    reviewDate = request.getParameter("reviewDate");
	    reviewText = request.getParameter("reviewText");
    }
    catch(Exception e) 
    { 
    	valueIsValid = false; 
    }

    // Add review to the database
    if(valueIsValid && (productName != null && productType != null && productPrice != null
       && productRetailer != null && productRetailerZip != null && productRetailerCity != null && productRetailerState != null && productManufacturer != null
       && userId != null && userAge != null && userGender != null && userOccupation != null && reviewRating != null && reviewDate != null && reviewText != null))
    {    
      DataStore.AddProductReview(new ProductReview(productId, productName, productType, productPrice, productRetailer, productRetailerZip, productRetailerCity,
    	productRetailerState, productOnSale, productManufacturer, productManufacturerRebate, userId, userAge, userGender, userOccupation, reviewRating,
    	reviewDate, reviewText));
    }

    PrintWriter out = response.getWriter();
    String htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\login.html");
    out.println(htmlContent);
  }

public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
	HttpSession session = request.getSession();
	String loggedInBy = (String)session.getAttribute("userId");
	String loggedInFor = (String)session.getAttribute("userIdForOrder");
	
	Integer productId = Integer.parseInt(request.getParameter("productId"));
	String submitId = request.getParameter("submitId");
	
	PrintWriter out = response.getWriter();
    if (productId != null) 
	{
    	Product product = DataStore.allproducts.get(productId);
    	User user = DataStore.getUsers().get(loggedInFor);
    	switch(submitId)
    	{ 
	    	case "Write Review" : displayWriteReviewProductPage(out, product, user);
	    	break;
	    	case "View Review" : displayViewReviewProductPage(out, productId);
	    	break;
    	}
    }	
  }

public void displayHeader(PrintWriter out)
{
	out.println(" <!doctype html>                                                                                                 ");
	out.println(" <html>                                                                                                          ");
	out.println(" <head>                                                                                                          ");
	out.println("   <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                                     ");
	out.println("   <title>Smart Portables</title>                                                                                ");
	out.println("   <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                                             ");
	out.println("   <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />               ");
	out.println(" </head>                                                                                                         ");
	out.println("                                                                                                                 ");
	out.println(" <body>                                                                                                          ");
	out.println("   <div id=\"container\">                                                                                        ");
	out.println("     <header>                                                                                                    ");
	out.println("       <div class=\"width\">                                                                                     ");
	out.println("     		<h1><a href=\"/\">Smart<span>Portables</span></a></h1>                                                ");
	out.println("         <h3>The best ever deal you can get</h3>                                                                 ");
	out.println("       </div>                                                                                                    ");
	out.println("     </header>                                                                                                   ");
}

public void displayFooter(PrintWriter out)
{
	out.println("     <footer>                                                                                                    ");
	out.println("       <div class=\"footer-content width\">                                                                      ");
	out.println("         <ul>                                                                                                    ");
	out.println("           <li><h2>Helpful Links</h2></li>                                                                       ");
	out.println("           <li><a href=\"Home?id=2\">About Us</a></li>                                                           ");
	out.println("           <li><a href=\"Home?id=3\">Contact Us</a></li>                                                         ");
	out.println("         </ul>                                                                                                   ");
	out.println("         <div class=\"clear\"></div>                                                                             ");
	out.println("       </div>                                                                                                    ");
	out.println("       <div class=\"footer-bottom\">                                                                             ");
	out.println("         <p>&copy;2017 Developed by Vinay Jagannath</p>                                                          ");
	out.println("       </div>                                                                                                    ");
	out.println("     </footer>                                                                                                   ");
	out.println("   </div>                                                                                                        ");
	out.println(" </body>                                                                                                         ");
	out.println("                                                                                                                 ");
	out.println(" </html>                                                                                                         ");
}

public void displayViewReviewProductPage(PrintWriter out, Integer productId)
{
	ArrayList<ProductReview> reviews = DataStore.GetProductReviews(productId);
	displayHeader(out);
	
	out.println("     <h2>Product Reviews</h2>                                                                                    ");
	out.println("       <table>                                                                                                   ");
	if(!reviews.isEmpty())
	{
		for(ProductReview entry : DataStore.GetProductReviews(productId))
		{
			out.println("         <tr>                                                                                                ");
			out.println("           <td>"+entry.getReviewRating()+" Stars</td>  													  ");
			out.println("           <td>"+entry.getReviewText()+"</td> 			     												  ");
			out.println("         </tr>                                                                                               ");	
			out.println("         <tr></tr>                                                                                           ");		
		}
	}
	else
	{
		out.println("         <tr> <td> No Reviews Available </td> </tr>                                                          ");
	}
	out.println("       </table>                                                                                                  ");
	
	displayFooter(out);
}

public void displayWriteReviewProductPage(PrintWriter out, Product product, User user)
  {
	String manufacturerRebate = product.getProductManufacturerRebate()>0 ? "Yes" : "No";
	
	displayHeader(out);
	
	out.println("     <h2>Submit Product Review</h2>                                                                              ");
	out.println("     <form method='get' action=\"ReviewServlet\">                                                                ");
	out.println("       <table>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Product Model Name</td>                                                                           ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"productName\" value='"+product.getProductName()+"' readonly/></td> ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Product Category</td>                                                                             ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"productType\" value='"+product.getProductType()+"' readonly/></td>                   ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Product Price</td>                                                                                ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"productPrice\" value='"+product.getProductPrice()+"' readonly/></td>                  ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Retailer Name</td>                                                                                ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"productRetailer\" value='"+product.getProductRetailer()+"' readonly/></td>               ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Retailer Zip</td>                                                                                 ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"productRetailerZip\" value='"+product.getProductZip()+"' readonly/></td> ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Retailer City</td>                                                                                ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"productRetailerCity\" value=\"Chicago\" readonly/></td> ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Retailer State</td>                                                                               ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"productRetailerState\" value=\"Illinois\" readonly/></td> ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Product On Sale</td>                                                                              ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"productOnSale\" value=\"Yes\" readonly/></td>   ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Manufacturer Name</td>                                                                            ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"productManufacturer\" value='"+product.getProductManufacturer()+"' readonly/></td>           ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Manufacturer Rebate</td>                                                                          ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"productManufacturerRebate\" value='"+manufacturerRebate+"' readonly/></td> ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>User Id</td>                                                                                      ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"userId\" value='"+user.getUserId()+"' readonly/></td>                        ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>User Age</td>                                                                                     ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"userAge\" required/></td>                       ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>User Gender</td>                                                                                  ");
	out.println("           <td>                                                                                                  ");
	out.println("                <input type=\"radio\" name=\"userGender\" value=\"male\" checked=\"true\">Male</input>           ");
	out.println("                <input type=\"radio\" name=\"userGender\" value=\"female\">Female</input>                        ");
	out.println("           </td>                                                                                                 ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>User Occupation</td>                                                                              ");
	out.println("           <td><input style=\"color:white\" type=\"text\" name=\"userOccupation\" required/></td>                ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Review Rating</td>                                                                                ");
	out.println("           <td>                                                                                                  ");
	out.println("                <input type=\"radio\" name=\"reviewRating\" value=\"1\">1 Star</input>                           ");
	out.println("                <input type=\"radio\" name=\"reviewRating\" value=\"2\">2 Stars</input>                          ");
	out.println("                <input type=\"radio\" name=\"reviewRating\" value=\"3\">3 Stars</input>                          ");
	out.println("                <input type=\"radio\" name=\"reviewRating\" value=\"4\">4 Stars</input>                          ");
	out.println("                <input type=\"radio\" name=\"reviewRating\" value=\"5\" checked=\"true\">5 Stars</input>         ");
	out.println("           </td>                                                                                                 ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Review Date</td>                                                                                  ");
	out.println("           <td><input style=\"color:white; width:200px\" type=\"text\" name=\"reviewDate\" value='"+new Date().toString()+"' readonly/></td>                    ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td>Review Text</td>                                                                                  ");
	out.println("           <td><textarea style=\"color:white; width:500px; height:50px\" type=\"text\" name=\"reviewText\"></textarea></td>                             ");
	out.println("         </tr>                                                                                                   ");
	out.println("         <tr>                                                                                                    ");
	out.println("           <td><input style=\"color:white\" type=\"submit\" value=\"Submit Review\"/></td>                       ");
	out.println("           <td><input type='hidden' name='productId' value='"+product.getProductId()+"'/></td>                   ");
	out.println("         </tr>                                                                                                   ");
	out.println("       </table>                                                                                                  ");
	out.println("     </form>                                                                                                     ");
	
	displayFooter(out);
  }
}
