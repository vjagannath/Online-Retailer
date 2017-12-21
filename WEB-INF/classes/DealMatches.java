import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DealMatches
{
	public static HashMap<Product, String> GetDealMatchProducts()
	{
		HashMap<Product, String> products = new HashMap<Product, String>();
		Collection<Product> existingProducts = DataStore.getAllProducts().values();
		try
	    {
	      BufferedReader reader = new BufferedReader(new FileReader(DataStore.TOMCAT_HOME + "\\webapps\\csj\\WEB-INF\\classes\\DealMatches.txt"));
	      String line;
	      while ((line = reader.readLine()) != null)
	      {
					if(products.containsValue(line))
	    	  {
	    		  continue;
	    	  }
	    	  for(Product product : existingProducts)
	    	  {
	    		  if(line.contains(product.getProductName()) || line.contains(product.getProductType()))
				  	{
	    			  products.put(product, line);
				  	}
	    	  }
	      }
	      reader.close();
	    }
	    catch (IOException e)
	    {
	      // Do-Nothing
	    	e.printStackTrace();
	    }
		return products;
	}
}
