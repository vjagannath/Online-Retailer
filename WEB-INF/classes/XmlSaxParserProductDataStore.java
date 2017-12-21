import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlSaxParserProductDataStore extends DefaultHandler {
	Product product;
	ProductAccessory accessory;
	String productCatalogFileName;
	String elementValueRead;
	SAXParserFactory factory;
	SAXParser parser;

	public XmlSaxParserProductDataStore(String productCatalogFileName)
	{
		this.productCatalogFileName = productCatalogFileName;
		parseDocument();
	}

	private void parseDocument()
	{
		factory = SAXParserFactory.newInstance();
		try
		{
			parser = factory.newSAXParser();
			parser.parse(productCatalogFileName, this);
		}
		catch (ParserConfigurationException e)
		{
			System.out.println("ParserConfig error");
		}
		catch (SAXException e)
		{
			System.out.println("SAXException : xml not well formed");
		}
		catch (IOException e)
		{
			System.out.println("IO error");
		}
	}
	
	@Override
	public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException
	{
		if (elementName.equalsIgnoreCase("product"))
		{
			product = new Product();
		}
		if (elementName.equalsIgnoreCase("productAccessories"))
		{
			product.setProductAccessories(new HashMap<Integer,ProductAccessory>());
		}
		if (elementName.equalsIgnoreCase("productAccessory"))
		{
			accessory = new ProductAccessory();
		}
	}

	@Override
	public void endElement(String str1, String str2, String element) throws SAXException
	{
		// Product details
		if (element.equals("product"))
		{
			DataStore.AddProductToDataBase(product);
			return;
		}
		if (element.equalsIgnoreCase("productId"))
		{
			product.setProductId(Integer.parseInt(elementValueRead));
			return;
		}
		if (element.equalsIgnoreCase("productName"))
		{
			product.setProductName(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("productImage"))
		{
			product.setProductImage(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("productRetailer"))
		{
			product.setProductRetailer(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("productManufacturer"))
		{
			product.setProductManufacturer(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("productCondition"))
		{
			product.setProductCondition(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("productType"))
		{
			product.setProductType(elementValueRead);
			return;
		}
		if(element.equalsIgnoreCase("productPrice"))
		{
			product.setProductPrice(Integer.parseInt(elementValueRead));
			return;
		}
		if(element.equalsIgnoreCase("productRetailerDiscount"))
		{
			product.setProductRetailerDiscount(Integer.parseInt(elementValueRead));
			return;
		}
		if(element.equalsIgnoreCase("productManufacturerRebate"))
		{
			product.setProductManufacturerRebate(Integer.parseInt(elementValueRead));
			return;
		}
		if(element.equalsIgnoreCase("productZip"))
		{
			product.setProductZip(Integer.parseInt(elementValueRead));
			return;
		}
		if (element.equals("productAccessory"))
		{
			DataStore.AddProductAccessoryToDataBase(accessory);
			product.getProductAccessories().put(accessory.getProductId(), accessory);
			return;
		}

		// Accessory details

		if (element.equalsIgnoreCase("accessoryId"))
		{
			accessory.setProductId(Integer.parseInt(elementValueRead));
			return;
		}
		if (element.equalsIgnoreCase("accessoryName"))
		{
			accessory.setProductName(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("accessoryImage"))
		{
			accessory.setProductImage(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("accessoryRetailer"))
		{
			accessory.setProductRetailer(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("accessoryManufacturer"))
		{
			accessory.setProductManufacturer(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("accessoryCondition"))
		{
			accessory.setProductCondition(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("accessoryType"))
		{
			accessory.setProductType(elementValueRead);
			return;
		}
		if(element.equalsIgnoreCase("accessoryPrice"))
		{
			accessory.setProductPrice(Integer.parseInt(elementValueRead));
			return;
		}
		if(element.equalsIgnoreCase("accessoryRetailerDiscount"))
		{
			accessory.setProductRetailerDiscount(Integer.parseInt(elementValueRead));
			return;
		}
		if(element.equalsIgnoreCase("accessoryManufacturerRebate"))
		{
			accessory.setProductManufacturerRebate(Integer.parseInt(elementValueRead));
			return;
		}
		if(element.equalsIgnoreCase("accessoryZip"))
		{
			accessory.setProductZip(Integer.parseInt(elementValueRead));
			return;
		}
	}

	@Override
	public void characters(char[] content, int begin, int end) throws SAXException {
		elementValueRead = new String(content, begin, end);
	}	
}
