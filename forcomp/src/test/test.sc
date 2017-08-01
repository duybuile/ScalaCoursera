import forcomp.Anagrams._

object test {
  println("Test functions on Anagrams")           //> Test functions on Anagrams
  
  val w:String = "ABABS"                          //> w  : String = ABABS
  w.toLowerCase().groupBy { x => x }              //> res0: scala.collection.immutable.Map[Char,String] = Map(b -> bb, s -> s, a -
                                                  //| > aa)
  w.toLowerCase().groupBy { x => x }.map(x => (x._1, x._2.size))
                                                  //> res1: scala.collection.immutable.Map[Char,Int] = Map(b -> 2, s -> 1, a -> 2)
                                                  //| 
  val t = List(('a', 2), ('b', 2), ('c', 3))      //> t  : List[(Char, Int)] = List((a,2), (b,2), (c,3))
  t.head                                          //> res2: (Char, Int) = (a,2)
  
  var shortList = 1 to 10 toList                  //> shortList  : List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  
 	wordOccurrences("abcda")                  //> java.lang.ExceptionInInitializerError
                                                  //| 	at test$$anonfun$main$1.apply$mcV$sp(test.scala:14)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at test$.main(test.scala:3)
                                                  //| 	at test.main(test.scala)
                                                  //| Caused by: java.lang.RuntimeException: Could not load word list, dictionary 
                                                  //| file not found
                                                  //| 	at scala.sys.package$.error(package.scala:27)
                                                  //| 	at forcomp.package$$anonfun$2.apply(package.scala:10)
                                                  //| 	at forcomp.package$$anonfun$2.apply(package.scala:10)
                                                  //| 	at scala.Option.getOrElse(Option.scala:121)
                                                  //| 	at forcomp.package$.loadDictionary(package.scala:9)
                                                  //| 	at forcomp.Anagrams$.<init>(Anagrams.scala:29)
                                                  //| 	at forcomp.Anagrams$.<clinit>(Anagrams.scala)
  //sentenceAnagrams(List("I", "love", "you"))
}