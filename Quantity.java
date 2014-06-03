import java.util.*;
import java.text.DecimalFormat;

/* quantity.java start
 * it's going to be a long tuesday,,
 * 
 * */


public class Quantity
{
  private double value;
  private Map<String,Integer> unit;
  private List<String> numerator;
  private List<String> denominator;
  
  public Quantity()
  {
    value = 1.0;
    // reference : http://stackoverflow.com/questions/5468664/how-do-i-set-an-empty-list-of-a-certain-type
    unit = new HashMap< String,Integer >();
    numerator = Collections.<String> emptyList();
    denominator = Collections.<String> emptyList();
  }
  
  public Quantity(Quantity target)
  {
    value = target.value;
    unit = new HashMap< String,Integer >();
    
    for(String keyString : target.unit.keySet())
    {
      int value2 = target.unit.get(keyString);
      unit.put(keyString, value2);
    }
    
  }
  
  public Quantity(double inputValue, List<String> numerator,
                  List<String> denominator) throws IllegalArgumentException
  {
    if(numerator == null || denominator == null)
      throw new IllegalArgumentException();
    
    value = inputValue;
    
    this.numerator = numerator;
    this.denominator = denominator;
    unit = new HashMap< String, Integer >();
    
    for(int i = 0 ; i < numerator.size() ; i++)
    {
      String keyString = numerator.get(i);
      
      if(unit.containsKey(keyString))
      {
        int keyValue = unit.get(keyString) + 1;
        unit.put(keyString, keyValue);
      }
      
      else
        unit.put(keyString, 1);
    }
    
    for(int i = 0 ; i < denominator.size() ; i++)
    {
      String keyString = denominator.get(i);
      
      if(unit.containsKey(keyString))
      {
        int keyValue = unit.get(keyString) - 1;
        unit.put(keyString, keyValue);
      }
      
      else
        unit.put(keyString, -1);
    }
    
    
  }
  
  public Quantity mul (Quantity target)
  {
    if( target == null)
    {  throw new IllegalArgumentException(); }
    
    Quantity result = new Quantity();
    result.value = this.value * target.value;
    
    if(this.unit.isEmpty())
    {
      result.unit.putAll(target.unit);
      return result;
    }
    
    else if(target.unit.isEmpty())
    {
      result.unit.putAll(this.unit);
      return result;
    }
    
    else
    {
      for(String keyString : target.unit.keySet())
      {
        for(String keyString2 : this.unit.keySet())
        {
          if(this.unit.containsKey(keyString))
          {
            int keyValue = this.unit.get(keyString) + target.unit.get(keyString);
            result.unit.put(keyString, keyValue);
          }
          else
          {
            result.unit.put(keyString2, this.unit.get(keyString2));
            result.unit.put(keyString, target.unit.get(keyString));
          }
          
        }
      }
      
    }
    return result;
  }
  
  public Quantity div (Quantity target)
  {
      if( target == null)
    {  throw new IllegalArgumentException(); }
    
    Quantity result = new Quantity();
    result.value = this.value / target.value;
    
    if(this.unit.isEmpty())
    {
      result.unit.putAll(target.unit);
      return result;
    }
    
    else if(target.unit.isEmpty())
    {
      result.unit.putAll(this.unit);
      return result;
    }
    
    else
    {
      for(String keyString : target.unit.keySet())
      {
        for(String keyString2 : this.unit.keySet())
        {
          if(this.unit.containsKey(keyString))
          {
            int keyValue = this.unit.get(keyString) - target.unit.get(keyString);
            result.unit.put(keyString, keyValue);
          }
          else
          {
            result.unit.put(keyString2, this.unit.get(keyString2));
            result.unit.put(keyString, target.unit.get(keyString));
          }
          
        }
      }
      
    }
    
    return result;
  }
  
  public Quantity pow (int power)
  {
    Quantity result = new Quantity(this);
    
    double resultValue = result.value;
    result.value = Math.pow(resultValue, power);
    
    for(String keyString : result.unit.keySet())
    {
      int keyValue = result.unit.get(keyString) * power;
      result.unit.put(keyString, keyValue);
    }
    
    return result;
    
  }
  
  public Quantity add (Quantity target) throws IllegalArgumentException
  {
    if(target.unit == null || !target.unit.equals(this.unit))
    { throw new IllegalArgumentException(); }
    
    double newValue = this.value + target.value;
    Quantity result = new Quantity (newValue, target.numerator, target.denominator);
    
    return result;
    
  }
  
  public Quantity sub (Quantity target) throws IllegalArgumentException
  {
    if(target.unit == null || !target.unit.equals(this.unit))
    { throw new IllegalArgumentException(); }
    
    double newValue = this.value - target.value;
    Quantity result = new Quantity (newValue, target.numerator, target.denominator);
    
    return result;
  }
  
  // ezpz lol 
  public Quantity negate()
  {
    Quantity result = new Quantity(this);
    
    result.value = -1 * this.value;
    
    return result;
    
  }
  
  public static Quantity normalizedUnit (String unitName, Map<String, Quantity> db)
  {
    if(db.containsKey(unitName))
    {
      Quantity result = db.get(unitName);
      return result.normalize(db);
    }
    
    return null;
  }
  
  public Quantity normalize (Map<String, Quantity> db)
  {
    return null;
  }
  
  public boolean equals(Object toCompare)
  {
    boolean result = false;
    
    if(toCompare instanceof Quantity)
    {
      if(this.toString().equals(toCompare.toString()))
        result = true;
    }
    
    return result;
  }
  
  public int testHashCode()
  {
    return 0;
  }
  
  public String toString()
  {
// XXX You will need to fix these lines to match your fields!
    double valueOfTheQuantity = this.value;
      Map<String,Integer> mapOfTheQuantity = this.unit;
// Ensure we get the units in order
      TreeSet<String> orderedUnits =
      new TreeSet<String>(mapOfTheQuantity.keySet());
      StringBuffer unitsString = new StringBuffer();
        
    for (String key : orderedUnits) {
      int expt = mapOfTheQuantity.get(key);
        unitsString.append(" " + key);
        if (expt != 1)
        unitsString.append("^" + expt);
    }
      
// Used to convert doubles to a string with a
// fixed maximum number of decimal places.
    DecimalFormat df = new DecimalFormat("0.0####");
// Put it all together and return.
      return df.format(valueOfTheQuantity)
      + unitsString.toString();
  }

}