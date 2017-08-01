package loops

object loops {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  // While
  def power(x: Double, exp: Int): Double = {
  	var r = 1.0
  	var i = exp
  	while(i > 0){r = r * x; i = i - 1}
  	r
  }                                               //> power: (x: Double, exp: Int)Double
  
  power(3.0, 2)                                   //> res0: Double = 9.0
}