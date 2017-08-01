package week6

object polynomials {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(79); 
  println("Welcome to the Scala worksheet")
  
  class Poly(val terms0: Map[Int, Double]){
    def this(bindings: (Int, Double)*) = this(bindings.toMap)
    
    val terms = terms0 withDefaultValue 0.0
    def + (other: Poly) = new Poly(terms ++ (other.terms foldLeft terms)(addTerm) )
    def adjust(term: (Int, Double)): (Int, Double) = {
      val (exp, coeff) = term
      terms get exp match{
        case Some(coeff1) => exp -> (coeff + coeff1)
        case None => exp -> coeff
      }
      
    }
    
    def addTerm(terms: Map[Int, Double], term: (Int, Double)): Map[Int, Double] = {
    	val (exp, coeff) = term
    	terms + (exp -> (coeff + terms(exp)))
    }
    override def toString =
       (for ( (exp, coeff) <- terms.toList.sorted.reverse) yield coeff+ "x^"+ exp) mkString " + "
  };$skip(814); 
  
  val p1 = new Poly((1 -> 2.0, 3 -> 4.0, 5 -> 6.2));System.out.println("""p1  : <error> = """ + $show(p1 ));$skip(42); 
  val p2 = new Poly((0 -> 3.0, 3 -> 7.0));System.out.println("""p2  : <error> = """ + $show(p2 ));$skip(10); val res$0 = 
  p1 + p2;System.out.println("""res0: <error> = """ + $show(res$0));$skip(14); val res$1 = 
  p1.terms(7);System.out.println("""res1: <error> = """ + $show(res$1))}
  
}
