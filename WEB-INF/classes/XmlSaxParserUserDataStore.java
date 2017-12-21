import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlSaxParserUserDataStore extends DefaultHandler {
    User user;
    String userId;
    String password;
    String userType;
    String userDataFileName;
    String elementValueRead;
    HashMap<Integer, Order> orders;
    Order order;
    Integer productId;
    int productCount;
    SAXParserFactory factory;
	SAXParser parser;

    public XmlSaxParserUserDataStore(String userDataFileName)
    {
      this.userDataFileName = userDataFileName;
      parseDocument();
    }

    private void parseDocument()
    {
      factory = SAXParserFactory.newInstance();
      try
      {
          parser = factory.newSAXParser();
          parser.parse(userDataFileName, this);
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
      if (elementName.equalsIgnoreCase("user"))
      {
    	  orders = new HashMap<Integer, Order>();
      }
      if (elementName.equalsIgnoreCase("order"))
      {
        order = new Order();
      }
    }

    @Override
    public void endElement(String str1, String str2, String element) throws SAXException
    {
      // Product details
      if (element.equals("user"))
      {
        user = new User(userId, password, userType);
        user.setOrders(orders);
        DataStore.AddUserToDataBase(user);
        return;
      }
      if (element.equalsIgnoreCase("userId"))
      {
        userId = elementValueRead;
        return;
      }
      if (element.equalsIgnoreCase("password"))
      {
        password = elementValueRead;
        return;
      }
      if (element.equalsIgnoreCase("userType"))
      {
        userType = elementValueRead;
        return;
      }
      if (element.equalsIgnoreCase("orderId"))
      {
        order.setOrderId(Integer.parseInt(elementValueRead));
        return;
      }
      if (element.equalsIgnoreCase("orderDate"))
      {
        order.setDate(elementValueRead);
        return;
      }
      if (element.equalsIgnoreCase("orderDeliveryDate"))
      {
        order.setDeliveryDate(elementValueRead);
        return;
      }
      if (element.equalsIgnoreCase("address"))
      {
        order.setAddress(elementValueRead);
        return;
      }
      if (element.equalsIgnoreCase("productId"))
      {
        productId = Integer.parseInt(elementValueRead);
        return;
      }
      if (element.equalsIgnoreCase("productCount"))
      {
        productCount = Integer.parseInt(elementValueRead);
        return;
      }
      if (element.equalsIgnoreCase("product"))
      {
        order.getOrderItems().put(productId, productCount);
        return;
      }
      if (element.equalsIgnoreCase("order"))
      {
        //user.getOrders().put(order.getOrderId(), order);
    	  orders.put(order.getOrderId(), order);
        return;
      }
    }

    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }    
}
