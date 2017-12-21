import java.util.*;

public class ShoppingCart
{
  // productId, productCount
  private HashMap<Integer, Integer> items;

  public ShoppingCart()
  {
    items = new HashMap<Integer, Integer>();
  }

  // Increment the product count in the cart if the product was previously added, else
  // add the new product to the cart
  public void addItem(Integer productId)
  {
	int availableCount = DataStore.getproductCount(productId);
	if(availableCount == 0)
	{
		return;
	}
    if(items.containsKey(productId))
    {
      items.put(productId, items.get(productId)+1);
    }
    else
    {
      items.put(productId, 1);
    }
  }

  // Decrement the product count if the product is present in the cart
  // remove the item from the cart if the product count is 0
  public void deleteItem(Integer productId)
  {
    if(!items.containsKey(productId))
    {
      return;
    }
    items.put(productId, items.get(productId)-1);
    if(items.get(productId) == 0)
    {
      items.remove(productId);
    }
  }
  
  public HashMap<Integer, Integer> getItems()
  {
	  return items;
  }
}
