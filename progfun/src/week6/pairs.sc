package week6

object pairs {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  def isPrime(n: Int): Boolean = (2 until n) forall (d => n % d !=0)
                                                  //> isPrime: (n: Int)Boolean
  val n = 7                                       //> n  : Int = 7
  ((1 until n) map (i => (1 until i) map (j => (i, j)))).flatten
                                                  //> res0: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,1
                                                  //| ), (3,2), (4,1), (4,2), (4,3), (5,1), (5,2), (5,3), (5,4), (6,1), (6,2), (6,
                                                  //| 3), (6,4), (6,5))
  
  //alternatively
  (1 until n) flatMap (i => (1 until i) map (j => (i, j)))
                                                  //> res1: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,1
                                                  //| ), (3,2), (4,1), (4,2), (4,3), (5,1), (5,2), (5,3), (5,4), (6,1), (6,2), (6,
                                                  //| 3), (6,4), (6,5))
  
  (1 until n) flatMap (i => (1 until i) map (j => (i, j))) filter (pair => isPrime(pair._1 + pair._2))
                                                  //> res2: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,2
                                                  //| ), (4,1), (4,3), (5,2), (6,1), (6,5))
  def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
  	(for((x, y) <- xs zip ys) yield x*y).sum  //> scalarProduct: (xs: Vector[Double], ys: Vector[Double])Double
}