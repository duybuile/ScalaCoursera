package week8

object books {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  case class Book(title: String, authors: List[String])
  
  val books = Set(
  	Book(
  		title = "Structure and Interpretation of Computer Programs",
  		authors = List("Abelson, Harald", "Sussman, Gerald J.")
  	),
  	Book(
  		title = "Introduction of Functional Programing",
  		authors = List("Bird, Richard", "Wadler, Phil")
  	),
  	Book(
  		title = "Effective Java",
  		authors = List("Bloch, Joshua")
  	),
  	Book(
  		title = "Effective Java 2",
  		authors = List("Bloch, Joshua")
  	),
  	Book(
  		title = "Java Puzzlers",
  		authors = List("Bloch, Joshua", "Gafter, Neal")
  	),
  	Book(
  		title = "Programming in Scala",
  		authors = List("Odersky, Martin", "Spoon, Lex", "Venners, Bill")
  	)
  )                                               //> books  : scala.collection.immutable.Set[week8.books.Book] = Set(Book(Introdu
                                                  //| ction of Functional Programing,List(Bird, Richard, Wadler, Phil)), Book(Effe
                                                  //| ctive Java 2,List(Bloch, Joshua)), Book(Programming in Scala,List(Odersky, M
                                                  //| artin, Spoon, Lex, Venners, Bill)), Book(Structure and Interpretation of Com
                                                  //| puter Programs,List(Abelson, Harald, Sussman, Gerald J.)), Book(Effective Ja
                                                  //| va,List(Bloch, Joshua)), Book(Java Puzzlers,List(Bloch, Joshua, Gafter, Neal
                                                  //| )))
  
  for(b <- books; a <- b.authors if a startsWith "Bloch,") yield b.title
                                                  //> res0: scala.collection.immutable.Set[String] = Set(Effective Java 2, Effecti
                                                  //| ve Java, Java Puzzlers)

  //Find people who write more than 1 book -- better solution, only one author shown
  for{
  		b1 <- books
  		b2 <- books
	  	if b1.title < b2.title
	  	a1 <- b1.authors
	  	a2 <- b2.authors
	  	if a1 == a2
	  } yield a1                              //> res1: scala.collection.immutable.Set[String] = Set(Bloch, Joshua)
 	//Find people who write more than 1 book
  for{
  	b1 <- books
  	b2 <- books
  	if b1 != b2
  	a1 <- b1.authors
  	a2 <- b2.authors
  	if a1 == a2
  }yield a1                                       //> res2: scala.collection.immutable.Set[String] = Set(Bloch, Joshua)
  
  for(b <- books; a <- b.authors if a startsWith "Bird") yield b.title
                                                  //> res3: scala.collection.immutable.Set[String] = Set(Introduction of Function
                                                  //| al Programing)
}