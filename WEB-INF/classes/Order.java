import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Random;

public class Order
{
  private int orderId;

  private String address;
  
  private String orderDate;
  
  private String orderDeliveryDate;

  // productId, productCount
  private HashMap<Integer, Integer> orderItems;

  public Order()
  {
    orderItems = new HashMap<Integer, Integer>();
  }

  public Order(HashMap<Integer, Integer> items)
  {
    Random random = new Random();
    orderId = Math.abs(random.nextInt());
    
    DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
    Date today = new Date();
    try 
    {
    	orderDate = formatter.parse(formatter.format(today)).toString();
	} 
    catch (Exception e) 
    {
		e.printStackTrace();
	}
    
    Calendar c = Calendar.getInstance();
    c.setTime(today);
    c.add(Calendar.DATE, 14);
    try 
    {
		orderDeliveryDate = formatter.parse(formatter.format(c.getTime())).toString();
	} 
    catch (Exception e) 
    {
		e.printStackTrace();
	}
    
    orderItems = items;
  }

  public void setOrderId(int id)
  {
    orderId = id;
  }

  public void setAddress(String addr)
  {
    address = addr;
  }
  
  public void setDate(String date)
  {
    orderDate = date;
  } 
  
  public void setDeliveryDate(String date)
  {
    orderDeliveryDate = date;
  } 
  
  public void setOrderItems(HashMap<Integer, Integer> items)
  {
    orderItems = items;
  }
  
  public String getAddress()
  {
	  return address;
  }

  public int getOrderId()
  {
	  return orderId;
  }
  
  public String getDate()
  {
    return orderDate;
  }
  
  public String getDeliveryDate()
  {
    return orderDeliveryDate;
  }
  
  public HashMap<Integer, Integer> getOrderItems()
  {
    return orderItems;
  }
}
