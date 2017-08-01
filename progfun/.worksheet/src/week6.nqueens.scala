package week6

object nqueens {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(62); 
  println("N-Queens solution");$skip(294); 
  
  def queens(n: Int): Set[List[Int]] = {
  	
  	def placeQueens(k: Int): Set[List[Int]] =
  		// no queen
  		if(k == 0) Set(List())
  		else
  			for{
  				queens <- placeQueens(k - 1)
  				col <- 0 until n
  				if isSafe(col, queens)
  			}yield col :: queens
  	
  	placeQueens(n)
  };System.out.println("""queens: (n: Int)Set[List[Int]]""");$skip(235); 
  
  def isSafe(col: Int, queens: List[Int]): Boolean = {
  	val row = queens.length
  	val queensWithRow = (row - 1 to 0 by  -1) zip queens
  	queensWithRow forall{
  		case (r, c) => col != c && math.abs(col - c) != row - r
  	}
  };System.out.println("""isSafe: (col: Int, queens: List[Int])Boolean""");$skip(15); val res$0 = 
  
  queens(4);System.out.println("""res0: Set[List[Int]] = """ + $show(res$0));$skip(192); 
  
  def show(queens: List[Int]) = {
  	val lines =
  		for (col <- queens.reverse)
  		yield Vector.fill(queens.length)("* ").updated(col, "X ").mkString
  		"\n" + (lines mkString "\n")
  };System.out.println("""show: (queens: List[Int])String""");$skip(47); val res$1 = 
  
  (queens(4) take 2 map show) mkString "\n";System.out.println("""res1: String = """ + $show(res$1))}
}
