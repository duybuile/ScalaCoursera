package week6

object sequenceTest {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  /* Array */
  val xs = Array(1,2,3,4)                         //> xs  : Array[Int] = Array(1, 2, 3, 4)
  
  
  val s = "Hello World"                           //> s  : String = Hello World
  
  /* Range is for integer */
  val r: Range = 1 until 5                        //> r  : Range = Range(1, 2, 3, 4)
  val r1: Range = 1 to 5                          //> r1  : Range = Range(1, 2, 3, 4, 5)
  
  
  /*
  Play with the functions
  */
  xs map (x => x*2)                               //> res0: Array[Int] = Array(2, 4, 6, 8)
  s filter (c => c.isUpper)                       //> res1: String = HW
  s exists (c => c.isUpper)                       //> res2: Boolean = true
  s forall (c => c.isUpper)                       //> res3: Boolean = false
  
  val pair = List(1,2,3) zip s                    //> pair  : List[(Int, Char)] = List((1,H), (2,e), (3,l))
  pair.unzip                                      //> res4: (List[Int], List[Char]) = (List(1, 2, 3),List(H, e, l))
  
  s flatMap (c => List('.', c))                   //> res5: String = .H.e.l.l.o. .W.o.r.l.d
  
  xs.sum                                          //> res6: Int = 10
  xs.max                                          //> res7: Int = 4
  
  /* Scalar product - calculate the product of 2 vectors*/
  def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
  	(xs zip ys).map(xy => xy._1 * xy._2).sum  //> scalarProduct: (xs: Vector[Double], ys: Vector[Double])Double
  
  /* A number is prime if it can divide by 1 and itself
  */
  def isPrime(n: Int): Boolean = (2 until n) forall (d => n % d !=0)
                                                  //> isPrime: (n: Int)Boolean
}