package week10

object iterate {
  println("Welcome to the Scala worksheet")
	def iterate(n: Int, f: Int => Int, x: Int): Int =
		if (n == 0) x else iterate(n - 1, f, f(x))
	def square(x: Int) = x*x
	
	iterate(0, square, square(3))
	

}