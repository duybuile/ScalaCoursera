object helloworksheet {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val x = 2                                       //> x  : Int = 2
  var y = 3                                       //> y  : Int = 3
  def increase(i: Int) = i + 1                    //> increase: (i: Int)Int
  var t = increase(x)                             //> t  : Int = 3
  increase(t)                                     //> res0: Int = 4
  increase(y)                                     //> res1: Int = 4
  increase(increase(y))                           //> res2: Int = 5
  increase(t)                                     //> res3: Int = 4
}