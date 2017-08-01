package week9

object lazyVal {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(75); 
  println("Welcome to the Scala worksheet");$skip(59); 
  
  //Example of lazy values
  
  val x = {print("x"); 1};System.out.println("""x  : Int = """ + $show(x ));$skip(31); 
  lazy val y = {print("y"); 2};System.out.println("""y: => Int""");$skip(26); 
  def z = {print("z"); 3};System.out.println("""z: => Int""");$skip(26); val res$0 = 
  
  z + y + x + z+ y + x;System.out.println("""res0: Int = """ + $show(res$0))}
}
