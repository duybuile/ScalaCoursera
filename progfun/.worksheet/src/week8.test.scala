package week8

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(72); 
  println("Welcome to the Scala worksheet");$skip(55); 
  
  val f: String => String = {case "ping" => "pong"};System.out.println("""f  : String => String = """ + $show(f ));$skip(12); val res$0 = 
  f("ping");System.out.println("""res0: String = """ + $show(res$0));$skip(11); val res$1 = 
  f("abc");System.out.println("""res1: String = """ + $show(res$1));$skip(71); 
  
  val f1: PartialFunction[String, String] = {case "ping" => "pong"};System.out.println("""f1  : PartialFunction[String,String] = """ + $show(f1 ));$skip(25); val res$2 = 
  f1.isDefinedAt("ping");System.out.println("""res2: Boolean = """ + $show(res$2));$skip(24); val res$3 = 
  f1.isDefinedAt("abc");System.out.println("""res3: Boolean = """ + $show(res$3))}
}
