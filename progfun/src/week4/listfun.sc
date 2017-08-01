package week4

object listfun {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val nums = List(2, -4, 5, 7, 1)                 //> nums  : List[Int] = List(2, -4, 5, 7, 1)
	val fruits = List("apple", "orange", "pineapple", "banana")
                                                  //> fruits  : List[String] = List(apple, orange, pineapple, banana)
	
	nums filter (x => x>0)                    //> res0: List[Int] = List(2, 5, 7, 1)
	nums filterNot (x => x>0)                 //> res1: List[Int] = List(-4)
	nums partition (x => x>0)                 //> res2: (List[Int], List[Int]) = (List(2, 5, 7, 1),List(-4))
	// take the first elem
	nums takeWhile(x => x>0)                  //> res3: List[Int] = List(2)
	nums dropWhile(x => x>0)                  //> res4: List[Int] = List(-4, 5, 7, 1)
	nums span (x=> x>0)                       //> res5: (List[Int], List[Int]) = (List(2),List(-4, 5, 7, 1))
	
	def pack[T](xs: List[T]): List[List[T]] = xs match{
		case Nil => Nil
  	case x :: xs1 =>
  		val (first, rest) = xs span (y => y == x)
  		first :: pack(rest)
	}                                         //> pack: [T](xs: List[T])List[List[T]]
	
	def encode[T](xs:List[T]): List[(T, Int)] =
		pack(xs) map (ys => (ys.head, ys.length))
                                                  //> encode: [T](xs: List[T])List[(T, Int)]
	
	pack(List("a", "a", "a", "b", "c", "c", "a"))
                                                  //> res6: List[List[String]] = List(List(a, a, a), List(b), List(c, c), List(a))
                                                  //| 
  encode(List("a", "a", "a", "b", "c", "c", "a")) //> res7: List[(String, Int)] = List((a,3), (b,1), (c,2), (a,1))
}