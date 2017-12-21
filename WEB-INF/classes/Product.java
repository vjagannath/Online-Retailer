import java.util.HashMap;

/*
Product class to hold details of each product, whole part pattern
*/
public class Product extends ProductAccessory implements java.io.Serializable
{
private HashMap<Integer,ProductAccessory> productAccessories;

  /* Default Constructor must be defined when parameterized constructor is defined */
  public Product(){}

  /* Parameterized constructor */
  public Product(Integer id, String name, String image, String retailer, String manufacturer, String condition, String type, int price,
  int retailerDiscount, int manufacturerRebate, int zip)
  {
    super(id, name, image, retailer, manufacturer, condition, type, price, retailerDiscount, manufacturerRebate, zip);
    productAccessories = new HashMap<Integer,ProductAccessory>();
  }

  public Product(Integer id, String name, String image, String retailer, String manufacturer, String condition, String type, int price,
  int retailerDiscount, int manufacturerRebate, int zip, HashMap<Integer,ProductAccessory> accessories)
  {
    super(id, name, image, retailer, manufacturer, condition, type, price, retailerDiscount, manufacturerRebate, zip);
    productAccessories = accessories;
  }

  /* Getter and Setter properties of productAccessories */
  public HashMap<Integer,ProductAccessory> getProductAccessories()
  {
    return productAccessories;
  }

  public void setProductAccessories(HashMap<Integer,ProductAccessory> accessories)
  {
    productAccessories = accessories;
  }
}
