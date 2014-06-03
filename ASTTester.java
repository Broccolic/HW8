import java.util.*;
import junit.framework.TestCase;

/** 
 * Name : Sunggwan Choi, Daniel Yang 
 * PID : A99092888 / A11442331
 * Section : B00 / B00
 * 2014/5/28
 */

public class ASTTester extends TestCase
{
  Map<String,Quantity> db = new HashMap<String,Quantity>();
  List<String> emp = new ArrayList<String>();
  
  protected void setUp()
  {
    // super.setUp();
    
    db.put("km", new Quantity(1000, Arrays.asList("meter"),emp));
    db.put("day", new Quantity(24,Arrays.asList("hour"),emp));
    db.put("hour", new Quantity(60,Arrays.asList("minute"),emp));
    db.put("minute", new Quantity(60,Arrays.asList("second"), emp));
    db.put("hertz", new Quantity(1,emp,Arrays.asList("second")));
    db.put("kph", new Quantity(1,Arrays.asList("km"), Arrays.asList("hour")));
  }
  
  public void testProduct()
  {
    Quantity testo1 = new Quantity(2.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo2 = new Quantity(5.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    AST left = new Value(testo1);
    AST right = new Value(testo2);
    AST product = new Product(left, right);
    
    Quantity result = product.eval(db);
    Quantity result2 = new Quantity(10.0, Arrays.asList("m","m"), Arrays.asList("s","s","s","s"));
    
    assertEquals(result, result2);
  }
  
  public void testQuotient()
  {
    Quantity testo1 = new Quantity(10.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo2 = new Quantity(5.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    AST left = new Value(testo1);
    AST right = new Value(testo2);
    AST quotient = new Quotient(left, right);
    
    Quantity result = quotient.eval(db);
    Quantity result2 = new Quantity(2.0, emp,emp);
    assertEquals(result, result2);
  }
  
  public void testSum()
  {
    Quantity testo1 = new Quantity(1.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo2 = new Quantity(2.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    AST left = new Value(testo1);
    AST right = new Value(testo2);
    AST sum = new Sum(left, right);
    
    Quantity result = sum.eval(db);
    Quantity result2 = new Quantity(3.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    assertEquals(result, result2);
  }
  
  public void testDifference()
  {
    Quantity testo1 = new Quantity(2.0, Arrays.asList("m"), Arrays.asList("s","s"));
    Quantity testo2 = new Quantity(5.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    AST left = new Value(testo1);
    AST right = new Value(testo2);
    AST difference = new Difference(left, right);
    
    Quantity result = difference.eval(db);
    Quantity result2 = new Quantity(-3.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    assertEquals(result, result2);
  }
  
  public void testPower()
  {
    Quantity testo1 = new Quantity(2.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    AST left = new Value(testo1);
    AST power = new Power(left , 2);
    
    Quantity result = power.eval(db);
    Quantity result2 = new Quantity(4.0, Arrays.asList("m","m"), Arrays.asList("s","s","s","s"));
    
    assertEquals(result, result2);
  }
  
  public void testNegation()
  {
    Quantity testo1 = new Quantity(2.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    AST left = new Value(testo1);
    AST negation = new Negation(left);
    
    Quantity result = negation.eval(db);
    Quantity result2 = new Quantity(-2.0, Arrays.asList("m"), Arrays.asList("s","s"));
    
    assertEquals(result, result2);
  }
  
  public void testNormalize()
  {
    Quantity testo1 = new Quantity(1.0, Arrays.asList("hour") , emp);
    
    AST left = new Value(testo1);
    AST normalize = new Normalize(left);
    
    Quantity result = normalize.eval(db);
    Quantity result2 = new Quantity(3600.0 , Arrays.asList("second"), emp);
    
    assertEquals(result, result2);
  }
  
  
  // test the method "define", using the examples from the pdf file; smoot. 
  public void testDefine()
  {
    String smoot = new String("smoot");
    Quantity testo1 = new Quantity(364.4, emp, emp);
    
    AST left = new Value(testo1);
    AST normalize = new Normalize(left);
    
    Quantity normalizeSmoot = normalize.eval(db);
    
    // --------------------------------------
    
    String smoot2 = new String("smoot");
    Quantity secondSmoot = new Quantity(67.0, Arrays.asList("in") , emp);
    
    AST left2 = new Value(secondSmoot);
    AST define = new Define(smoot2, left2);
    
    Quantity inSmoot = define.eval(db);
    
    // -------------------------------------
    
    Quantity testo2 = new Quantity(364.4, emp, emp);
    Quantity testo3 = new Quantity(1.0, Arrays.asList("smoot") , emp);
    
    AST leftTesto = new Value(testo2);
    AST rightTesto = new Value(testo3);
    AST product = new Product(leftTesto, rightTesto);
    AST normalizeFinal = new Normalize(product);
    
    Quantity expectedResult = normalizeFinal.eval(db);
    
    // 364.4 * 67inch = 24414.8 inch 
    // but we normalize here(as the pdf did), so... thats about... 24414.8 inch 
    Quantity finalResult = new Quantity( 24414.8, Arrays.asList("in"), emp); 
    
    assertEquals(finalResult, expectedResult);
  }
  
  public void testValue()
  {
    Quantity valueTesto = new Quantity(9.8 , Arrays.asList("m"), Arrays.asList("s","s"));
    AST value = new Value(valueTesto);
    String result = new String("9.8 m s^-2");
    
    assertEquals(result, valueTesto.toString());
  }
}