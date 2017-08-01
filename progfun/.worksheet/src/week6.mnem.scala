package week6

import scala.io.Source

object mnem {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(96); 
  println("Welcome to the Scala worksheet");$skip(111); 
  
  val in = Source.fromURL("http://lamp.epfl.ch/files/content/sites/lamp/files/teaching/progfun/linuxwords");System.out.println("""in  : scala.io.BufferedSource = """ + $show(in ));$skip(84); 
  val words = in.getLines.toList filter (word => word forall (chr => chr.isLetter));System.out.println("""words  : List[String] = """ + $show(words ));$skip(138); 
  val mnem = Map(
  	'2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
  	'6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ");System.out.println("""mnem  : scala.collection.immutable.Map[Char,String] = """ + $show(mnem ));$skip(160); 
  
  /** Invert the mnem to give the map from chars to number*/
  val charCode: 	Map[Char, Char] =
  	for( (digit, str) <- mnem; ltr <- str) yield ltr -> digit;System.out.println("""charCode  : Map[Char,Char] = """ + $show(charCode ));$skip(100); 
  /** Maps a word to a digit*/
  def wordCode(word: String): String = word.toUpperCase map charCode;System.out.println("""wordCode: (word: String)String""");$skip(22); val res$0 = 
  
  wordCode("JAVA");System.out.println("""res0: String = """ + $show(res$0));$skip(95); 
  
  val wordsForNum: Map[String, Seq[String]] = words groupBy wordCode withDefaultValue Seq();System.out.println("""wordsForNum  : Map[String,Seq[String]] = """ + $show(wordsForNum ));$skip(257); 
  
  def encode(number: String): Set[List[String]] =
  	if(number.isEmpty) Set(List())
  	else {
  		for{
  			split <- 1 to number.length
  			word <- wordsForNum(number take split)
  			rest <- encode(number drop split)
  		}yield word :: rest
  	}.toSet;System.out.println("""encode: (number: String)Set[List[String]]""");$skip(27); val res$1 = 
  	
  encode("7225247386");System.out.println("""res1: Set[List[String]] = """ + $show(res$1));$skip(86); 
  
  def translate(number: String): Set[String] = encode(number) map (_ mkString " ");System.out.println("""translate: (number: String)Set[String]""");$skip(29); val res$2 = 
  
  translate("7225247386");System.out.println("""res2: Set[String] = """ + $show(res$2))}
}
