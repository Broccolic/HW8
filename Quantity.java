import java.util.*;
import java.text.DecimalFormat;

/** 
 * Name : Sunggwan Choi, Daniel Yang 
 * PID : A99092888 / A11442331
 * Section : B00 / B00
 * 2014/5/28
 */

public class Quantity
{
  private double value;
  private Map<String,Integer> unit;
  private List<String> numerator;
  private List<String> denominator;
  
  /**
   * 0 argument constructor. Default value is 1.
   * Unit becomes an empty hashMap, but still exists. 
   * */
  public Quantity()
  {
    value = 1.0;
    // reference for Collection.<String> emptyList()
    // comes from : http://stackoverflow.com/questions/5468664/how-do-i-set-an-empty-list-of-a-certain-type
    unit = new HashMap< String,Integer >();
    numerator = Collections.<String> emptyList();
    denominator = Collections.<String> emptyList();
  }
  
  /**
   * Constructor which takes one parameter and makes a deep copy of it. 
   * @param target a Quantity object to make a deep copy of. 
   * */
  public Quantity(Quantity target)
  {
    value = target.value;
    unit = new HashMap< String,Integer >();
    
    for(String keyString : target.unit.keySet())
      unit.put(keyString, target.unit.get(keyString));
  }
  
  /**
   * Constructor with 3 parameters for value, numerator unit, and denominator unit. 
   * for example, if it is 3 m/s (three meter per second), 3 is value, m is numerator unit,
   * and s will be denominator unit 
   * @param inputValue the value of the unit. 
   * @param numerator the numerator unit 
   * @param denominator the denominator unit. 
   * */
  public Quantity(double inputValue, List<String> numerator,
                  List<String> denominator) throws IllegalArgumentException
  {
    if(numerator == null || denominator == null)
      throw new IllegalArgumentException();
    
    value = inputValue;
    
    this.numerator = numerator;
    this.denominator = denominator;
    
    unit = new HashMap< String, Integer >();
    
    // put numerator to the unit hashmap 
    for(int i = 0 ; i < numerator.size() ; i++)
    {
      String keyString = numerator.get(i);
      
      // if there is already that unit, just +1 to the value of the unit 
      if(unit.containsKey(keyString))
        unit.put(keyString, unit.get(keyString) + 1);
      else
        unit.put(keyString, 1);
    }
    
    // put denominator to the unit hashmap 
    for(int i = 0 ; i < denominator.size() ; i++)
    {
      String keyString = denominator.get(i);
      
      if(unit.containsKey(keyString))
        unit.put(keyString, unit.get(keyString) - 1);
      else
        unit.put(keyString, -1);
    }
  }
  
  /**
   * Method to multiple two Quantity objects. 
   * 
   * @param target another Quantity object to be multipled to "this" object. 
   * @return Quantity the result after the multiplication of "this" and target. 
   * @throws IllegalArgumentException when the target parameter is null. 
   * */
  public Quantity mul (Quantity target)
  {
    if( target == null )
    {  throw new IllegalArgumentException(); }
    
    Quantity result = new Quantity();
    result.value = this.value * target.value;
    
    // if "this" has empty units, just put everything from target to "this". 
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
      for(String targetKeyString : target.unit.keySet())
      {
        for(String thisKeyString : this.unit.keySet())
        {
          // if this has the unit, just simply increment it. 
          if(this.unit.containsKey(targetKeyString))
            result.unit.put(targetKeyString, this.unit.get(targetKeyString) + target.unit.get(targetKeyString));
          
          // if this doesn't have it, add it to this's hashMap. 
          else
          {
            result.unit.put(thisKeyString, this.unit.get(thisKeyString));
            result.unit.put(targetKeyString, target.unit.get(targetKeyString));
          }
        }
      }
    }
    return result;
  }
  
  /**
   * Method to divide two Quantity objects. 
   * 
   * @param target another Quantity object to divid the "this" object.
   * @return Quantity the result after the division of "this" and target. 
   * @throws IllegalArgumentException when the target parameter is null. 
   * */
  public Quantity div (Quantity target)
  {
    Quantity result = new Quantity();
    
      if( target == null)
    {  throw new IllegalArgumentException(); }
    
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
      for(String targetKeyString : target.unit.keySet())
      {
        for(String thisKeyString : this.unit.keySet())
        {
          // if this has the unit, just simply increment it. 
          if(this.unit.containsKey(targetKeyString))
            result.unit.put(targetKeyString, this.unit.get(targetKeyString) - target.unit.get(targetKeyString));
          
          else
          {
            result.unit.put(thisKeyString, this.unit.get(thisKeyString));
            result.unit.put(targetKeyString, target.unit.get(targetKeyString));
          }
        }
      }
    }
    
    return result;
  }
  
  /**
   * Method to rise the value of this to the power of the parameter. 
   * 
   * @param power the amount of power to be risen of "this"'s value.
   * @return Quantity the result after to the power is done. 
   * */
  public Quantity pow (int power)
  {
    Quantity result = new Quantity(this);
    result.value = Math.pow(result.value, power);
    
    for(String keyString : result.unit.keySet())
      result.unit.put(keyString, result.unit.get(keyString) * power);
    
    return result;  
  }
  
  /**
   * Method to add two Quantity objects. 
   * 
   * @param target another Quantity object to add "this" object.
   * @return Quantity the result after the addition of "this" and target. 
   * @throws IllegalArgumentException when the target parameter is null. 
   * */
  public Quantity add (Quantity target) throws IllegalArgumentException
  {
    if(target.unit == null || !target.unit.equals(this.unit))
    { throw new IllegalArgumentException(); }
    
    Quantity result = new Quantity (this.value + target.value, target.numerator, target.denominator);
    
    return result;
  }
  
  /**
   * Method to subtract two Quantity objects. 
   * 
   * @param target another Quantity object to subtract the "this" object.
   * @return Quantity the result after the subtraction of "this" and target. 
   * @throws IllegalArgumentException when the target parameter is null. 
   * */
  public Quantity sub (Quantity target) throws IllegalArgumentException
  {
    if(target.unit == null || !target.unit.equals(this.unit))
    { throw new IllegalArgumentException(); }
    
    Quantity result = new Quantity (this.value - target.value, target.numerator, target.denominator);
    
    return result;
  }
  
  /**
   * Method to negate the sign of this quantity object's value. 
   * 
   * @return the result quantity after the negation of the value is done. 
   * @throws IllegalArgumentException when the target parameter is null. 
   * */
  public Quantity negate()
  {
    Quantity result = new Quantity(this);
    
    result.value = -1 * this.value;
    
    return result;
  }

  /**
   * A method which takes any single object and returns true if and only if 
   * that object is a Quantity object whose units are exactly the same as 
   * the calling object. 
   * 
   * @param toCompare The Quantity object that will be compared to the calling object "this".
   * @return true, if both objects are the same. False if they are different. 
   * */
  public boolean equals(Object toCompare)
  {
    if(toCompare instanceof Quantity)
    {
      if(this.toString().equals(toCompare.toString()))
        return true;
    }
    
    return false;
  }
  
  /**
   * A method which will return the hash code of the calling object Quantity. 
   * 
   * @return the hashcode of the calling object. 
   * */
  public int hashCode()
  {
    return this.toString().hashCode();
  }
  
  public String toString()
  {
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
  
  /**
   * A static method that takes a String and a map and create a normalized
   * Quantity object equivalent to 1 of the given unit. 
   * 
   * @param unitName the name of the unit 
   * @param db database defined above in the class that this Quantity.java will be used. 
   * @return A new Quantity object equivalent to 1 of the given unit. 
   * */
  public static Quantity normalizedUnit (String unitName, Map<String, Quantity> db)
  {
    if(db.containsKey(unitName))
      return db.get(unitName).normalize(db);
    
    else
      return new Quantity(1.0, Arrays.asList(unitName),
          Collections.<String>emptyList());
  }
  
  /**
   * A method which takes in a database in the same format as normalizedUnit and 
   * returns a copy of this but in normalized form
   * 
   * @param db database defined above in the class that this Quantity.java will be used. 
   * @return A new Quantity object that is in the normalized form. 
   * */
  public Quantity normalize (Map<String, Quantity> db)
  {
    Quantity result = new Quantity();
    result.value = this.value;
    
    // for all keys in this unit's hashMap... 
    for(String keyString : this.unit.keySet())
    {
      Quantity beforeNormalize = Quantity.normalizedUnit(keyString, db);
      
      int tothePow = this.unit.get(keyString);
      
      // raise to the power, depends on what the unit was from the "db". 
      Quantity afterNormalize = beforeNormalize.pow(tothePow);
      result = result.mul(afterNormalize);
    }
    
    return result; 
  }
  
  
  
}
