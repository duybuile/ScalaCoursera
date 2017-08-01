package week8

object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val f: String => String = {case "ping" => "pong"}
                                                  //> f  : String => String = <function1>
  f("ping")                                       //> res0: String = pong
  f("abc")                                        //> scala.MatchError: abc (of class java.lang.String)
                                                  //| 	at week8.test$$anonfun$main$1$$anonfun$2.apply(week8.test.scala:6)
                                                  //| 	at week8.test$$anonfun$main$1$$anonfun$2.apply(week8.test.scala:6)
                                                  //| 	at week8.test$$anonfun$main$1.apply$mcV$sp(week8.test.scala:8)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at week8.test$.main(week8.test.scala:3)
                                                  //| 	at week8.test.main(week8.test.scala)
  
  val f1: PartialFunction[String, String] = {case "ping" => "pong"}
  f1.isDefinedAt("ping")
  f1.isDefinedAt("abc")
}