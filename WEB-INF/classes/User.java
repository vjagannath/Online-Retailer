import java.util.*;

public class User implements java.io.Serializable
{
  private String userId;
  private String password;
  private String userType;
  private HashMap<Integer, Order> orders;

  public User(String id, String pswd, String usertype)
  {
    userId = id;
    password = pswd;
    userType = usertype;
    orders = new HashMap<Integer, Order>();
  }

  public String getUserId()
  {
    return userId;
  }

  public String getPassword()
  {
    return password;
  }
  
  public String getUserType()
  {
    return userType;
  }

  public HashMap<Integer, Order> getOrders()
  {
    return orders;
  }

  public void setOrders(HashMap<Integer, Order> ordrs)
  {
    orders = ordrs;
  }
}
