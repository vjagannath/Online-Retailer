import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class AddProductServlet extends HttpServlet
{
	boolean addProductSuccessful = false;
	
public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");
    
    // Check if user clicked on "Home" option, id=1
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
    
    String productName = null;
    String productImage = null;
    String productRetailer = null;
    String productManufacturer = null;
    String productCondition = null;
    String productType = null;
    Integer productId = null;
	Integer productPrice = null;
    Integer productRetailerDiscount = null;
    Integer productManufacturerRebate = null;
    Integer productZip = null;
    
    boolean valueIsValid = true;
    try
    {
	    productName = request.getParameter("productName");
	    productImage = request.getParameter("productImage");
	    productRetailer = request.getParameter("productRetailer");
	    productManufacturer = request.getParameter("productManufacturer");
	    productCondition = request.getParameter("productCondition");
	    productType = request.getParameter("productType");    
	    productId = Integer.parseInt(request.getParameter("productId"));
    	productPrice = Integer.parseInt(request.getParameter("productPrice"));
        productRetailerDiscount = Integer.parseInt(request.getParameter("productRetailerDiscount"));
        productManufacturerRebate = Integer.parseInt(request.getParameter("productManufacturerRebate"));
        productZip = Integer.parseInt(request.getParameter("productZip"));
    }
    catch(Exception e) 
    { 
    	valueIsValid = false; 
    }

    // Add the product if the product with the given product id was previously not added and all values are valid
    if(valueIsValid && (!DataStore.getAllProducts().containsKey(productId)) &&
    		(productId != null && productName != null && productImage != null && productRetailer != null && 
    		 productManufacturer != null && productCondition != null && productType != null && 
    		 productPrice != null && productRetailerDiscount != null &&
    		 productManufacturerRebate != null && productZip != null))
    {
        addProductSuccessful = true;
        
      // Add data to the products list
      DataStore.AddProduct(new Product(productId, productName, productImage, productRetailer, productManufacturer,
      productCondition, productType, productPrice, productRetailerDiscount, productManufacturerRebate, productZip));
    }

    PrintWriter out = response.getWriter();
    displayAddProductPage(out);
  }

public void displayAddProductPage(PrintWriter out)
{
	out.println("	<!doctype html>                                                                                      ");
	out.println("	<html>                                                                                               ");
	out.println("	<head>                                                                                               ");
	out.println("	  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                          ");
	out.println("	  <title>Smart Portables</title>                                                                     ");
	out.println("	  <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                                  ");
	out.println("	  <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />    ");
	out.println("	</head>                                                                                              ");
	out.println("	                                                                                                     ");
	out.println("	<body>                                                                                               ");
	out.println("	  <div id=\"container\">                                                                             ");
	out.println("	    <header>                                                                                         ");
	out.println("	      <div class=\"width\">                                                                          ");
	out.println("	    		<h1><a href=\"/\">Smart<span>Portables</span></a></h1>                                   ");
	out.println("	        <h3>The best ever deal you can get</h3>                                                      ");
	out.println("	      </div>                                                                                         ");
	out.println("	      <a href=\"AddProductServlet?id=1\" style='font-weight: bold;float:right'>Home</a>              ");
	out.println("	    </header>                                                                                        ");
	if(!addProductSuccessful)
	{
		out.println("	    <h2>Add Product</h2>                                                                             ");
	}
	else
	{
		addProductSuccessful = false;
		out.println("	    <h2>Added Product Successfully</h2>                                                                             ");
	}
	out.println("	    <form method='post' action=\"AddProductServlet\">                                                ");
	out.println("	      <table>                                                                                        ");
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td>Product Id</td>                                                                        ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productId\" required/></td>                   ");
	out.println("	        </tr>                                                                                        ");
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td>Product Name</td>                                                                      ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productName\" required/></td>                 ");
	out.println("	        </tr>                                                                                        ");
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td>Product Image</td>                                                                     ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productImage\" required/></td>                ");
	out.println("	        </tr>                                                                                        ");
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td>Retailer</td>                                                                          ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productRetailer\" required/></td>             ");
	out.println("	        </tr>                                                                                        ");
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td>Manufacturer</td>                                                                      ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productManufacturer\" required/></td>         ");
	out.println("	        </tr>                                                                                        ");
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td>Condition</td>                                                                         ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productCondition\" required/></td>            ");
	out.println("	        </tr>                                                                                        ");
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td>Type</td>                                                                              ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productType\" required/></td>                 ");
	out.println("	        </tr>                                                                                        ");
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td>Price</td>                                                                             ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productPrice\" required/></td>                ");
	out.println("	        </tr>                                                                                        ");
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td>Retailer Discount</td>                                                                 ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productRetailerDiscount\" required/></td>     ");
	out.println("	        </tr>                                                                                        ");
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td>Manufacturer Rebate</td>                                                               ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productManufacturerRebate\" required/></td>   ");
	out.println("	        </tr>                                                                                        ");
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td>Zip</td>                                                               ");
	out.println("	          <td><input style=\"color:white\" type=\"text\" name=\"productZip\" required/></td>   ");
	out.println("	        </tr>                                                                                        ");
	
	out.println("	        <tr>                                                                                         ");
	out.println("	          <td><input style=\"color:white\" type=\"submit\" value=\"Add\"/></td>                      ");
	out.println("	        </tr>                                                                                        ");
	out.println("	      </table>                                                                                       ");
	out.println("	    </form>                                                                                          ");
	out.println("	    <footer>                                                                                         ");
	out.println("	      <div class=\"footer-content width\">                                                           ");
	out.println("	        <ul>                                                                                         ");
	out.println("	          <li><h2>Helpful Links</h2></li>                                                            ");
	out.println("	          <li><a href=\"Home?id=2\">About Us</a></li>                                                ");
	out.println("	          <li><a href=\"Home?id=3\">Contact Us</a></li>                                              ");
	out.println("	        </ul>                                                                                        ");
	out.println("	        <div class=\"clear\"></div>                                                                  ");
	out.println("	      </div>                                                                                         ");
	out.println("	      <div class=\"footer-bottom\">                                                                  ");
	out.println("	        <p>&copy;2017 Developed by Vinay Jagannath</p>                                               ");
	out.println("	      </div>                                                                                         ");
	out.println("	    </footer>                                                                                        ");
	out.println("	  </div>                                                                                             ");
	out.println("	</body>                                                                                              ");
	out.println("	                                                                                                     ");
	out.println("	</html>                                                                                              ");

}
}
