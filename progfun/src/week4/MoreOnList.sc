package week4

object MoreOnList {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
	/*
	* removeAt(1, List('a', 'b', 'c', 'd')) // List(a, c, d)
	* b is at index 1
	*/
	def removeAt[T](n: Int, xs: List[T]) = (xs take n):::(xs drop n + 1)
                                                  //> removeAt: [T](n: Int, xs: List[T])List[T]
	removeAt(1, List('a', 'b', 'c', 'd'))     //> res0: List[Char] = List(a, c, d)

	//def flatten(xs: List[Any]): List[Any] = ???
  //flatten(List(List(1, 1), 2, List(3, List(5, 8))))
  
  val pair = ("answer", 32)                       //> pair  : (String, Int) = (answer,32)
  val (label, value) = pair                       //> label  : String = answer
                                                  //| value  : Int = 32
	
	val nums = List(2, -4, 5, 7, 1)           //> nums  : List[Int] = List(2, -4, 5, 7, 1)
	val fruits = List("apple", "orange", "pineapple", "banana")
                                                  //> fruits  : List[String] = List(apple, orange, pineapple, banana)
	val m = new mergesort                     //> m  : week4.mergesort = week4.mergesort@13f5a29
	m.msort(nums)                             //> res1: List[Int] = List(-4, 1, 2, 5, 7)
	m.msort2(nums)((x: Int, y: Int) => x < y) //> res2: List[Int] = List(-4, 1, 2, 5, 7)
	m.msort2(fruits)((x: String, y: String) => x.compareTo(y) < 0)
                                                  //> res3: List[String] = List(apple, banana, orange, pineapple)
  m.msort3(nums)                                  //> res4: List[Int] = List(-4, 1, 2, 5, 7)
  m.msort3(fruits)                                //> res5: List[String] = List(apple, banana, orange, pineapple)
  
  def concat[T](xs: List[T], ys: List[T]): List[T] = (xs foldRight ys)(_ :: _)
                                                  //> concat: [T](xs: List[T], ys: List[T])List[T]
  
  def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  	(xs foldRight List[U]())( ??? )           //> mapFun: [T, U](xs: List[T], f: T => U)List[U]

	def lengthFun[T](xs: List[T]): Int =
	  (xs foldRight 0)( ??? )                 //> lengthFun: [T](xs: List[T])Int
}