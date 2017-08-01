package week6

object pairs {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(73); 
  println("Welcome to the Scala worksheet");$skip(69); 
  def isPrime(n: Int): Boolean = (2 until n) forall (d => n % d !=0);System.out.println("""isPrime: (n: Int)Boolean""");$skip(12); 
  val n = 7;System.out.println("""n  : Int = """ + $show(n ));$skip(65); val res$0 = 
  ((1 until n) map (i => (1 until i) map (j => (i, j)))).flatten;System.out.println("""res0: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$0));$skip(80); val res$1 = 
  
  //alternatively
  (1 until n) flatMap (i => (1 until i) map (j => (i, j)));System.out.println("""res1: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$1));$skip(106); val res$2 = 
  
  (1 until n) flatMap (i => (1 until i) map (j => (i, j))) filter (pair => isPrime(pair._1 + pair._2));System.out.println("""res2: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$2));$skip(114); 
  def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
  	(for((x, y) <- xs zip ys) yield x*y).sum;System.out.println("""scalarProduct: (xs: Vector[Double], ys: Vector[Double])Double""")}
}
