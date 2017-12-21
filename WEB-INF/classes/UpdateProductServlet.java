import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class UpdateProductServlet extends HttpServlet
{
	boolean updateProductSuccessful = false;
	
public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	String id = request.getParameter("id");
	if(id.equals("1"))
	{
		String htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\storemanager.html");
		out.println(htmlContent);
	}
  }

public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    
    Integer productId = null;
	Integer productPrice = null;
    Integer productRetailerDiscount = null;
    Integer productManufacturerRebate = null;
    Integer productRetailerZip = null;
    
    boolean valueIsValid = true;
    
    try
    {
	    productId = Integer.parseInt(request.getParameter("productId"));
	    productPrice = Integer.parseInt(request.getParameter("productPrice"));
	    productRetailerDiscount = Integer.parseInt(request.getParameter("productRetailerDiscount"));
	    productManufacturerRebate = Integer.parseInt(request.getParameter("productManufacturerRebate"));
	    productRetailerZip = Integer.parseInt(request.getParameter("productRetailerZip"));
    }
    catch(Exception e) 
    { 
    	valueIsValid = false; 
    }

    // Update the product if the product with the given product id was previously added and all values are valid
    if(valueIsValid && DataStore.getAllProducts().containsKey(productId) && (productId != null && productPrice != null &&
       productRetailerDiscount != null && productManufacturerRebate != null && productRetailerZip != null))
    {
      updateProductSuccessful = true;
      
      // Update data to category specific list
      DataStore.UpdateProduct(productId, productPrice, productRetailerDiscount, productManufacturerRebate, productRetailerZip);
    }

    PrintWriter out = response.getWriter();
    displayUpdateProductPage(out);
  }

public void displayUpdateProductPage(PrintWriter out)
{
	out.println("	<!doctype html>                                                                                     ");
	out.println("	<html>                                                                                              ");
	out.println("	<head>                                                                                              ");
	out.println("	  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                         ");
	out.println("	  <title>Smart Portables</title>                                                                    ");
	out.println("	  <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                                 ");
	out.println("	  <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />   ");
	out.println("	</head>                                                                                             ");
	out.println("	                                                                                                    ");
	out.println("	<body>                                                                                              ");
	out.println("	  <div id=\"container\">                                                                            ");
	out.println("	    <header>                                                                                        ");
	out.println("	      <div class=\"width\">                                                                         ");
	out.println("	          <h1><a href=\"/\">Smart<span>Portables</span></a></h1>                                    ");
	out.println("	          <h3>The best ever deal you can get</h3>                                                   ");
	out.println("	      </div>                                                                                        ");
	out.println("		 <a href=\"UpdateProductServlet?id=1\" style='font-weight: bold;float:right'>Home</a>           ");
	out.println("	    </header>                                                                                       ");
	if(!updateProductSuccessful)
	{
		out.println("	    <h2>Update Product</h2>                                                                     ");
	}
	else
	{
		updateProductSuccessful = false;
		out.println("	    <h2>Updated Product Successfully</h2>                                                       ");
	}
	out.println("	    <form method='post' action=\"UpdateProductServlet\">                                            ");
	out.println("	      <table>                                                                                       ");
	out.println("	        <tr>                                                                                        ");
	out.println("	          <td>Product Id</td>                                                                       ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productId\" required/></td>         ");
	out.println("	        </tr>                                                                                       ");
	out.println("	        <tr>                                                                                        ");
	out.println("	          <td>Price</td>                                                                            ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productPrice\" required/></td>      ");
	out.println("	        </tr>                                                                                       ");
	out.println("	        <tr>                                                                                        ");
	out.println("	          <td>Retailer Discount</td>                                                                ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productRetailerDiscount\" required/></td>    ");
	out.println("	        </tr>                                                                                       ");
	out.println("	        <tr>                                                                                        ");
	out.println("	          <td>Manufacturer Rebate</td>                                                              ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productManufacturerRebate\" required/></td>  ");
	out.println("	        </tr>                                                                                       ");
	out.println("	        <tr>                                                                                        ");
	out.println("	          <td>Zip</td>                                                              				");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productRetailerZip\" required/></td>");
	out.println("	        </tr>                                                                                       ");
	out.println("	        <tr>                                                                                        ");
	out.println("	          <td><input style=\"color:white\" type=\"submit\" value=\"Update\"/></td>                  ");
	out.println("	        </tr>                                                                                       ");
	out.println("	      </table>                                                                                      ");
	out.println("	    </form>                                                                                         ");
	out.println("	    <footer>                                                                                        ");
	out.println("	      <div class=\"footer-content width\">                                                          ");
	out.println("	        <ul>                                                                                        ");
	out.println("	          <li><h2>Helpful Links</h2></li>                                                           ");
	out.println("	          <li><a href=\"Home?id=2\">About Us</a></li>                                               ");
	out.println("	          <li><a href=\"Home?id=3\">Contact Us</a></li>                                             ");
	out.println("	        </ul>                                                                                       ");
	out.println("	        <div class=\"clear\"></div>                                                                 ");
	out.println("	      </div>                                                                                        ");
	out.println("	      <div class=\"footer-bottom\">                                                                 ");
	out.println("	        <p>&copy;2017 Developed by Vinay Jagannath</p>                                              ");
	out.println("	      </div>                                                                                        ");
	out.println("	    </footer>                                                                                       ");
	out.println("	  </div>                                                                                            ");
	out.println("	</body>                                                                                             ");
	out.println("	                                                                                                    ");
	out.println("	</html>                                                                                             ");

}
}
