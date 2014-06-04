import junit.framework.*;
import java.util.*;
import java.util.Arrays;

/** 
 * Name : Sunggwan Choi, Daniel Yang 
 * PID : A99092888 / A11442331
 * Section : B00 / B00
 * 2014/5/28
 */

public class QuantityTester extends TestCase 
{
  List<String> testList = new ArrayList<String>();
  
  Map<String,Quantity> db = new HashMap<String,Quantity>();
  List<String> emp = new ArrayList<String>();
  
  
  public void setUp() throws Exception
  {
    super.setUp();
    
    
    //from HW8.pdf... 
    db.put("km", new Quantity(1000, Arrays.asList("meter"),emp));
    db.put("day", new Quantity(24,Arrays.asList("hour"),emp));
    db.put("hour", new Quantity(60,Arrays.asList("minute"),emp));
    db.put("minute", new Quantity(60,Arrays.asList("second"), emp));
    db.put("hertz", new Quantity(1,emp,Arrays.asList("second")));
    db.put("kph", new Quantity(1,Arrays.asList("km"), Arrays.asList("hour")));
  }
  
  /** Test for 0 argument constructor */
  public void testZeroArgsConstructor()
  {
    List<String> testList = new ArrayList<String>();
    Quantity testo = new Quantity();
    Quantity testo2 = new Quantity(1,testList,testList);  
    
    
   assertEquals(testo2, testo);
  }
  
  /** Test for 1 argument constructor */
  public void testDeepCopyOneArgsConstructor()
  {
    Quantity testo = new Quantity(9.8 , Arrays.asList("m") , Arrays.asList("s","s"));
    Quantity deepCopy = new Quantity(testo);
    
    assertEquals("9.8 m s^-2",deepCopy.toString());
  }
  
  /** Test for 3 argument constructor */
  public void testThreeArgsConstructor()
  {
    Quantity testo = new Quantity(9.8 , Arrays.asList("m"), Arrays.asList("s","s"));
    
    assertEquals("9.8 m s^-2" , testo.toString());
  }
  
  /** Test for Exception for the 3 agrument constructor. */
  public void testThreeArgsConstructorException()
  {
    
    // first case : the nominator is null. constructor should fail.
    try 
    {
      Quantity testo = new Quantity(100 , null, Arrays.asList("this homework is too hard"));
      
      fail();
    }
    
    // test is passed. no need to catch anything.
    catch(IllegalArgumentException e)
    {}
    
    // second case : denumerator is null. constructor should fail.
    try 
    {
      Quantity testo = new Quantity(100 , Arrays.asList("Idon'twanttogotoKoreanarmy"), null);
      
      fail();
    }
    
    // test is passed. no need to catch anything.
    catch(IllegalArgumentException e)
    {}
    
  }
  
  /** Test for Mul method */
  public void testMul()
  {
    Quantity testo1 = new Quantity(1.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo2 = new Quantity(6.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    Quantity testo3 = testo1.mul(testo2);
    Quantity result = new Quantity(6.0, Arrays.asList("m","m"), Arrays.asList("s","s","s","s"));
    
    assertEquals(testo3, result);
  }
  
  /** Test for Div method */
  // need to check Div method. the units got fucked up 
  public void testDiv()
  {
    Quantity testo1 = new Quantity(12.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo2 = new Quantity(6.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo3 = testo1.div(testo2);
    
    Quantity result = new Quantity(2.0, emp,emp);
    
    assertEquals(testo3, result);
    
  }
  
  /** Test for Pow method */
  public void testPow()
  {
    Quantity testo1 = new Quantity(2.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo2 = testo1.pow(3);
    
    Quantity result = new Quantity(8.0 , Arrays.asList("m","m","m") ,
                                   Arrays.asList("s","s","s","s","s","s"));
    
    assertEquals(result, testo2);
  }
  
  /** Test for Add method */
  public void testAdd()
  {
    Quantity testo1 = new Quantity(1.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo2 = new Quantity(2.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo3 = testo1.add(testo2);
    
    Quantity result = new Quantity(3.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    assertEquals(result, testo3);
  }
  
  /** Test for Sub method */
  public void testSub()
  {
    Quantity testo1 = new Quantity(3.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo2 = new Quantity(1.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo3 = testo1.sub(testo2);
    
    Quantity result = new Quantity(2.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    assertEquals(result, testo3);
  }
  
  /** Test for Negate method */
  public void testNegate()
  {
    Quantity testo1 = new Quantity(1.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo2 = testo1.negate();
    
    Quantity result = new Quantity(-1.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    assertEquals(result, testo2);
  }
  
  /** Test for NormalizedUnit, static method */
  public void testNormalizedUnit()
  {
    Quantity testo = new Quantity();
    testo = Quantity.normalizedUnit("hour", db);
    String normalized = new String("3600.0 second");
    
    assertEquals(normalized, testo.toString());
  }
  
  /** Test for just normalize method */
  public void testNormalize()
  {
    Quantity testo = new Quantity(36.0, Arrays.asList("kph"), emp);
    testo = testo.normalize(db);
    
    String result = new String("10.0 meter second^-1");
    
    assertEquals(result, testo.toString());
  }
  
  /** Test for Hashcode method */
  public void testHashCode()
  {
    Quantity testo1 = new Quantity(3.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo2 = new Quantity(3.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    // both hashCode values should be the same.
    assertEquals(testo1.hashCode(), testo2.hashCode());
  }
  
  
  
}