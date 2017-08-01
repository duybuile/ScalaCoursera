package week4

object MoreOnList {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(78); 
  println("Welcome to the Scala worksheet");$skip(158); 
  
	/*
	* removeAt(1, List('a', 'b', 'c', 'd')) // List(a, c, d)
	* b is at index 1
	*/
	def removeAt[T](n: Int, xs: List[T]) = (xs take n):::(xs drop n + 1);System.out.println("""removeAt: [T](n: Int, xs: List[T])List[T]""");$skip(39); val res$0 = 
	removeAt(1, List('a', 'b', 'c', 'd'));System.out.println("""res0: List[Char] = """ + $show(res$0));$skip(133); 

	//def flatten(xs: List[Any]): List[Any] = ???
  //flatten(List(List(1, 1), 2, List(3, List(5, 8))))
  
  val pair = ("answer", 32);System.out.println("""pair  : (String, Int) = """ + $show(pair ));$skip(28); 
  val (label, value) = pair;System.out.println("""label  : String = """ + $show(label ));System.out.println("""value  : Int = """ + $show(value ));$skip(35); 
	
	val nums = List(2, -4, 5, 7, 1);System.out.println("""nums  : List[Int] = """ + $show(nums ));$skip(61); 
	val fruits = List("apple", "orange", "pineapple", "banana");System.out.println("""fruits  : List[String] = """ + $show(fruits ));$skip(23); 
	val m = new mergesort;System.out.println("""m  : week4.mergesort = """ + $show(m ));$skip(15); val res$1 = 
	m.msort(nums);System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(43); val res$2 = 
	m.msort2(nums)((x: Int, y: Int) => x < y);System.out.println("""res2: List[Int] = """ + $show(res$2));$skip(64); val res$3 = 
	m.msort2(fruits)((x: String, y: String) => x.compareTo(y) < 0);System.out.println("""res3: List[String] = """ + $show(res$3));$skip(17); val res$4 = 
  m.msort3(nums);System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(19); val res$5 = 
  m.msort3(fruits);System.out.println("""res5: List[String] = """ + $show(res$5));$skip(82); 
  
  def concat[T](xs: List[T], ys: List[T]): List[T] = (xs foldRight ys)(_ :: _);System.out.println("""concat: [T](xs: List[T], ys: List[T])List[T]""");$skip(92); 
  
  def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  	(xs foldRight List[U]())( ??? );System.out.println("""mapFun: [T, U](xs: List[T], f: T => U)List[U]""");$skip(66); 

	def lengthFun[T](xs: List[T]): Int =
	  (xs foldRight 0)( ??? );System.out.println("""lengthFun: [T](xs: List[T])Int""")}
}
