import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class AjaxUtility 
{
	public ProductAccessory getData(String searchId)
	{
		ProductAccessory product = null;
		Integer id = Integer.parseInt(searchId);
		HashMap<Integer, Product> productData = DataStore.getAllProducts();
		if(DataStore.getAllProducts().containsKey(id))
		{
			product = DataStore.getAllProducts().get(id);
		}
		if(DataStore.getAllProductAccessories().containsKey(id))
		{
			product = DataStore.getAllProductAccessories().get(id);
		}
		return product;
	}
	
	public StringBuffer readData(String searchId)
	{
		HashMap<Integer, Product> productData = DataStore.getAllProducts();
		Iterator<Entry<Integer, Product>> productIterator = productData.entrySet().iterator();
		StringBuffer sb = new StringBuffer();
		while(productIterator.hasNext())
		{
			Map.Entry<Integer, Product> entry = productIterator.next();
			Product product = entry.getValue();
			if(product.getProductName().toLowerCase().startsWith(searchId))
			{
				sb.append("<composer>");
				sb.append("<id>" +product.getProductId() + "</id>");
				sb.append("<name>" + product.getProductName() + "</name>");
				sb.append("</composer>");
			}
		}	

		HashMap<Integer, ProductAccessory> accessoryData = DataStore.getAllProductAccessories();
		Iterator<Entry<Integer, ProductAccessory>> accessoryIterator = accessoryData.entrySet().iterator();
		while(accessoryIterator.hasNext())
		{
			Map.Entry<Integer, ProductAccessory> entry = accessoryIterator.next();
			ProductAccessory product = entry.getValue();
			if(product.getProductName().toLowerCase().startsWith(searchId))
			{
				sb.append("<composer>");
				sb.append("<id>" +product.getProductId() + "</id>");
				sb.append("<name>" + product.getProductName() + "</name>");
				sb.append("</composer>");
			}
		}
		
		return sb;
	}
}
