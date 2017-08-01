package week6

import scala.io.Source

object mnem {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val in = Source.fromURL("http://lamp.epfl.ch/files/content/sites/lamp/files/teaching/progfun/linuxwords")
                                                  //> java.io.FileNotFoundException: http://lamp.epfl.ch/files/content/sites/lamp/
                                                  //| files/teaching/progfun/linuxwords
                                                  //| 	at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(Unknown S
                                                  //| ource)
                                                  //| 	at sun.net.www.protocol.http.HttpURLConnection.getInputStream(Unknown So
                                                  //| urce)
                                                  //| 	at java.net.URL.openStream(Unknown Source)
                                                  //| 	at scala.io.Source$.fromURL(Source.scala:141)
                                                  //| 	at scala.io.Source$.fromURL(Source.scala:131)
                                                  //| 	at week6.mnem$$anonfun$main$1.apply$mcV$sp(week6.mnem.scala:8)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at week6.mnem$.main(week6.mnem.scala:5)
                                                  //| 	at week6.mnem.main(week6.mnem.scala)
  val words = in.getLines.toList filter (word => word forall (chr => chr.isLetter))
  val mnem = Map(
  	'2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
  	'6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ")
  
  /** Invert the mnem to give the map from chars to number*/
  val charCode: 	Map[Char, Char] =
  	for( (digit, str) <- mnem; ltr <- str) yield ltr -> digit
  /** Maps a word to a digit*/
  def wordCode(word: String): String = word.toUpperCase map charCode
  
  wordCode("JAVA")
  
  val wordsForNum: Map[String, Seq[String]] = words groupBy wordCode withDefaultValue Seq()
  
  def encode(number: String): Set[List[String]] =
  	if(number.isEmpty) Set(List())
  	else {
  		for{
  			split <- 1 to number.length
  			word <- wordsForNum(number take split)
  			rest <- encode(number drop split)
  		}yield word :: rest
  	}.toSet
  	
  encode("7225247386")
  
  def translate(number: String): Set[String] = encode(number) map (_ mkString " ")
  
  translate("7225247386")
}