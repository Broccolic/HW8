

import junit.framework.*;
import java.util.*;
import java.util.regex.*; 

/** 
 * Name : Sunggwan Choi, Daniel Yang 
 * PID : A99092888 / A11442331
 * Section : B00 / B00
 * 2014/5/28
 * 
 * final version
 */


public class UnicalcTester extends TestCase {
  private Unicalc uni;
  
  Map<String,Quantity> db = new HashMap<String,Quantity>();
  List<String> emp = new ArrayList<String>();
  
  
  // Calls the super for the 0 argument constructor 
  public UnicalcTester()
  {
    super();
  }
  
  // Default instance. DB setup for the unicalc tester.
  public void setUp() throws Exception
  {
    super.setUp();
    
    db.put("km", new Quantity(1000, Arrays.asList("meter"),emp));
    db.put("day", new Quantity(24,Arrays.asList("hour"),emp));
    db.put("hour", new Quantity(60,Arrays.asList("minute"),emp));
    db.put("minute", new Quantity(60,Arrays.asList("second"), emp));
    db.put("hertz", new Quantity(1,emp,Arrays.asList("second")));
    db.put("kph", new Quantity(1,Arrays.asList("km"), Arrays.asList("hour")));
  }
  
  /** Test for method S */
  public void testS() 
  {
    // Make a new unit called smoot. smoot = 5 meter
    Unicalc uni = new Unicalc(); 
    uni.tokenize("def smoot 5 meter");
    AST uniValue = uni.S();
    Quantity smootResult = uniValue.eval(db);
    
    Unicalc uni2 = new Unicalc();
    uni2.tokenize("# 3 smoot");
    AST uniValue2 = uni2.L();
    Quantity smootResult2 = new Quantity(15.0 , Arrays.asList("meter"), emp);
    Quantity smootResult3 = uniValue2.eval(db);
    
    assertEquals(smootResult2, smootResult3);
    
  }
  
  
  /** Test for method L */
  public void testL()
  {
    Unicalc uni = new Unicalc();
    uni.tokenize("# 1 km");
    AST astL = uni.L();
    
    Quantity result = astL.eval(db);
    Quantity result2 = new Quantity(1000.0, Arrays.asList("meter"), emp);
    
    assertEquals(result2, result);
  }
  
  /** Test for method E */
  public void testE()
  {
    Unicalc uni = new Unicalc();
    uni.tokenize("1m+2m");
    AST uniAST = uni.E();
    
    Quantity result = uniAST.eval(db);
    Quantity result2 = new Quantity(3.0, Arrays.asList("m"), emp);
    
    assertEquals(result2, result);
  }
  
  /** Test for method P */
  public void testP()
  {
    Unicalc uni = new Unicalc();
    uni.tokenize("2m*3m");
    
    AST uniAST = uni.P();
    
    Quantity result = uniAST.eval(db);
    Quantity result2 = new Quantity(6.0, Arrays.asList("m","m"), emp);
    
    assertEquals(result2, result);
  }
  
  /** Test for method K */
  public void testK() {
    Unicalc uni = new Unicalc();
    uni.tokenize("--18m");
    AST a = uni.K();
    Quantity q = a.eval(db);
    Quantity toCompare = new Quantity(18.0, Arrays.asList("m"), emp);
    
    assertEquals( toCompare, q);
  }
  
  /** Test for method Q */
  public void testQ() {
    Unicalc uni = new Unicalc();
    uni.tokenize("(8m + 3m)");
    AST a = uni.Q();
    Quantity q = a.eval(db);
    Quantity toCompare = new Quantity(11.0, Arrays.asList("m"), emp);
    assertEquals( toCompare, q);
  }
  
  /** Test for method R */
  public void testR() {
    Unicalc uni = new Unicalc();
    uni.tokenize("(2.0m)^2");
    AST a = uni.R();
    Quantity result = a.eval(db);
    Quantity toCompare = new Quantity(4.0, Arrays.asList("m","m"), emp);
    assertEquals( toCompare, result);
    
  }
  
  
}
