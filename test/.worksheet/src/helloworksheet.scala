object helloworksheet {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(67); 
  println("Welcome to the Scala worksheet");$skip(12); 
  val x = 2;System.out.println("""x  : Int = """ + $show(x ));$skip(12); 
  var y = 3;System.out.println("""y  : Int = """ + $show(y ));$skip(31); 
  def increase(i: Int) = i + 1;System.out.println("""increase: (i: Int)Int""");$skip(22); 
  var t = increase(x);System.out.println("""t  : Int = """ + $show(t ));$skip(14); val res$0 = 
  increase(t);System.out.println("""res0: Int = """ + $show(res$0));$skip(14); val res$1 = 
  increase(y);System.out.println("""res1: Int = """ + $show(res$1));$skip(24); val res$2 = 
  increase(increase(y));System.out.println("""res2: Int = """ + $show(res$2));$skip(14); val res$3 = 
  increase(t);System.out.println("""res3: Int = """ + $show(res$3))}
}
