package week10

object iterate {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(76); 
  println("Welcome to the Scala worksheet");$skip(96); 
	def iterate(n: Int, f: Int => Int, x: Int): Int =
		if (n == 0) x else iterate(n - 1, f, f(x));System.out.println("""iterate: (n: Int, f: Int => Int, x: Int)Int""");$skip(26); 
	def square(x: Int) = x*x;System.out.println("""square: (x: Int)Int""");$skip(33); val res$0 = 
	
	iterate(0, square, square(3));System.out.println("""res0: Int = """ + $show(res$0))}
	

}
