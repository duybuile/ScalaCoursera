package week8

object books {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(73); 
  println("Welcome to the Scala worksheet")
  
  case class Book(title: String, authors: List[String]);$skip(724); 
  
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
  );System.out.println("""books  : scala.collection.immutable.Set[week8.books.Book] = """ + $show(books ));$skip(76); val res$0 = 
  
  for(b <- books; a <- b.authors if a startsWith "Bloch,") yield b.title;System.out.println("""res0: scala.collection.immutable.Set[String] = """ + $show(res$0));$skip(224); val res$1 = 

  //Find people who write more than 1 book -- better solution, only one author shown
  for{
  		b1 <- books
  		b2 <- books
	  	if b1.title < b2.title
	  	a1 <- b1.authors
	  	a2 <- b2.authors
	  	if a1 == a2
	  } yield a1;System.out.println("""res1: scala.collection.immutable.Set[String] = """ + $show(res$1));$skip(162); val res$2 = 
 	//Find people who write more than 1 book
  for{
  	b1 <- books
  	b2 <- books
  	if b1 != b2
  	a1 <- b1.authors
  	a2 <- b2.authors
  	if a1 == a2
  }yield a1;System.out.println("""res2: scala.collection.immutable.Set[String] = """ + $show(res$2));$skip(74); val res$3 = 
  
  for(b <- books; a <- b.authors if a startsWith "Bird") yield b.title;System.out.println("""res3: scala.collection.immutable.Set[String] = """ + $show(res$3))}
}
