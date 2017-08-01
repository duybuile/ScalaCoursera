import forcomp.Anagrams._

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(80); 
  println("Test functions on Anagrams");$skip(28); 
  
  val w:String = "ABABS";System.out.println("""w  : String = """ + $show(w ));$skip(37); val res$0 = 
  w.toLowerCase().groupBy { x => x };System.out.println("""res0: scala.collection.immutable.Map[Char,String] = """ + $show(res$0));$skip(65); val res$1 = 
  w.toLowerCase().groupBy { x => x }.map(x => (x._1, x._2.size));System.out.println("""res1: scala.collection.immutable.Map[Char,Int] = """ + $show(res$1));$skip(45); 
  val t = List(('a', 2), ('b', 2), ('c', 3));System.out.println("""t  : List[(Char, Int)] = """ + $show(t ));$skip(9); val res$2 = 
  t.head;System.out.println("""res2: (Char, Int) = """ + $show(res$2));$skip(36); 
  
  var shortList = 1 to 10 toList;System.out.println("""shortList  : List[Int] = """ + $show(shortList ));$skip(30); val res$3 = 
  
 	wordOccurrences("abcda");System.out.println("""res3: forcomp.Anagrams.Occurrences = """ + $show(res$3))}
  //sentenceAnagrams(List("I", "love", "you"))
}
