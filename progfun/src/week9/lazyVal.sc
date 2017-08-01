package week9

object lazyVal {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  //Example of lazy values
  
  val x = {print("x"); 1}                         //> xx  : Int = 1
  lazy val y = {print("y"); 2}                    //> y: => Int
  def z = {print("z"); 3}                         //> z: => Int
  
  z + y + x + z+ y + x                            //> zyzres0: Int = 12
}