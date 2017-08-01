package week6

object map {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val romanNumerals = Map("I" -> 1, "V" -> 5, "X" -> 10)
                                                  //> romanNumerals  : scala.collection.immutable.Map[String,Int] = Map(I -> 1, V 
                                                  //| -> 5, X -> 10)
  val capitalOfCountry = Map("US" -> "Washington", "Switzerland" -> "Bern", "Vietnam" -> "Hanoi")
                                                  //> capitalOfCountry  : scala.collection.immutable.Map[String,String] = Map(US -
                                                  //| > Washington, Switzerland -> Bern, Vietnam -> Hanoi)
  def showCapital(country: String) = capitalOfCountry.get(country) match{
  	case None => "missing data"
  	case Some(capital) => capital
  }                                               //> showCapital: (country: String)String
  
  capitalOfCountry get "andorra"                  //> res0: Option[String] = None
  capitalOfCountry get "Vietnam"                  //> res1: Option[String] = Some(Hanoi)
  
  val fruit = List("apple", "pear", "orange", "pineapple")
                                                  //> fruit  : List[String] = List(apple, pear, orange, pineapple)
  fruit sortWith (_.length < _.length)            //> res2: List[String] = List(pear, apple, orange, pineapple)
  fruit.sorted                                    //> res3: List[String] = List(apple, orange, pear, pineapple)
  
  fruit groupBy (_.head)                          //> res4: scala.collection.immutable.Map[Char,List[String]] = Map(p -> List(pear
                                                  //| , pineapple), a -> List(apple), o -> List(orange))
}