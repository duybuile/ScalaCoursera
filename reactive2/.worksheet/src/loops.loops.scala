package loops

object loops {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(73); 
  println("Welcome to the Scala worksheet");$skip(136); 
  
  // While
  def power(x: Double, exp: Int): Double = {
  	var r = 1.0
  	var i = exp
  	while(i > 0){r = r * x; i = i - 1}
  	r
  };System.out.println("""power: (x: Double, exp: Int)Double""");$skip(19); val res$0 = 
  
  power(3.0, 2);System.out.println("""res0: Double = """ + $show(res$0))}
}
