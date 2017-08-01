package week9.waterprob

object PouringTest {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(89); 
  println("Welcome to the Scala worksheet");$skip(45); 
  
  val problem = new Pouring(Vector(4, 7));System.out.println("""problem  : week9.waterprob.Pouring = """ + $show(problem ));$skip(16); val res$0 = 
  problem.moves;System.out.println("""res0: scala.collection.immutable.IndexedSeq[Product with Serializable with week9.waterprob.PouringTest.problem.Move] = """ + $show(res$0));$skip(25); val res$1 = 
  
  problem.solution(6);System.out.println("""res1: Stream[week9.waterprob.PouringTest.problem.Path] = """ + $show(res$1))}
}
