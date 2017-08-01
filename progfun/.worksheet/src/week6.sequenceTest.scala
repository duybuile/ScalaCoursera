package week6

object sequenceTest {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(80); 
  println("Welcome to the Scala worksheet");$skip(43); 
  
  /* Array */
  val xs = Array(1,2,3,4);System.out.println("""xs  : Array[Int] = """ + $show(xs ));$skip(30); 
  
  
  val s = "Hello World";System.out.println("""s  : String = """ + $show(s ));$skip(59); 
  
  /* Range is for integer */
  val r: Range = 1 until 5;System.out.println("""r  : Range = """ + $show(r ));$skip(25); 
  val r1: Range = 1 to 5;System.out.println("""r1  : Range = """ + $show(r1 ));$skip(62); val res$0 = 
  
  
  /*
  Play with the functions
  */
  xs map (x => x*2);System.out.println("""res0: Array[Int] = """ + $show(res$0));$skip(28); val res$1 = 
  s filter (c => c.isUpper);System.out.println("""res1: String = """ + $show(res$1));$skip(28); val res$2 = 
  s exists (c => c.isUpper);System.out.println("""res2: Boolean = """ + $show(res$2));$skip(28); val res$3 = 
  s forall (c => c.isUpper);System.out.println("""res3: Boolean = """ + $show(res$3));$skip(34); 
  
  val pair = List(1,2,3) zip s;System.out.println("""pair  : List[(Int, Char)] = """ + $show(pair ));$skip(13); val res$4 = 
  pair.unzip;System.out.println("""res4: (List[Int], List[Char]) = """ + $show(res$4));$skip(35); val res$5 = 
  
  s flatMap (c => List('.', c));System.out.println("""res5: String = """ + $show(res$5));$skip(12); val res$6 = 
  
  xs.sum;System.out.println("""res6: Int = """ + $show(res$6));$skip(9); val res$7 = 
  xs.max;System.out.println("""res7: Int = """ + $show(res$7));$skip(176); 
  
  /* Scalar product - calculate the product of 2 vectors*/
  def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
  	(xs zip ys).map(xy => xy._1 * xy._2).sum;System.out.println("""scalarProduct: (xs: Vector[Double], ys: Vector[Double])Double""");$skip(133); 
  
  /* A number is prime if it can divide by 1 and itself
  */
  def isPrime(n: Int): Boolean = (2 until n) forall (d => n % d !=0);System.out.println("""isPrime: (n: Int)Boolean""")}
}
