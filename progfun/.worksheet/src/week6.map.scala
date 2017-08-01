package week6

object map {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(71); 
  println("Welcome to the Scala worksheet");$skip(60); 
  
  val romanNumerals = Map("I" -> 1, "V" -> 5, "X" -> 10);System.out.println("""romanNumerals  : scala.collection.immutable.Map[String,Int] = """ + $show(romanNumerals ));$skip(98); 
  val capitalOfCountry = Map("US" -> "Washington", "Switzerland" -> "Bern", "Vietnam" -> "Hanoi");System.out.println("""capitalOfCountry  : scala.collection.immutable.Map[String,String] = """ + $show(capitalOfCountry ));$skip(142); 
  def showCapital(country: String) = capitalOfCountry.get(country) match{
  	case None => "missing data"
  	case Some(capital) => capital
  };System.out.println("""showCapital: (country: String)String""");$skip(36); val res$0 = 
  
  capitalOfCountry get "andorra";System.out.println("""res0: Option[String] = """ + $show(res$0));$skip(33); val res$1 = 
  capitalOfCountry get "Vietnam";System.out.println("""res1: Option[String] = """ + $show(res$1));$skip(62); 
  
  val fruit = List("apple", "pear", "orange", "pineapple");System.out.println("""fruit  : List[String] = """ + $show(fruit ));$skip(39); val res$2 = 
  fruit sortWith (_.length < _.length);System.out.println("""res2: List[String] = """ + $show(res$2));$skip(15); val res$3 = 
  fruit.sorted;System.out.println("""res3: List[String] = """ + $show(res$3));$skip(28); val res$4 = 
  
  fruit groupBy (_.head);System.out.println("""res4: scala.collection.immutable.Map[Char,List[String]] = """ + $show(res$4))}
}
