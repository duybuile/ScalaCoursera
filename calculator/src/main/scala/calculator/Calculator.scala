package calculator

sealed abstract class Expr
final case class Literal(v: Double) extends Expr
/**
 * The Ref case class represents a reference to another variable in the map namedExpressions.
 */
final case class Ref(name: String) extends Expr
final case class Plus(a: Expr, b: Expr) extends Expr
final case class Minus(a: Expr, b: Expr) extends Expr
final case class Times(a: Expr, b: Expr) extends Expr
final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator {
  /**
   * The function returns another map from the same set of variable names to their actual values,
   * computed from their expressions.
   * 
   * Note: 1) Refs to other variables could cause cyclic dependencies (eg, a = b + 1, and b = 2 *a)
   * Such cyclic dependencies are considered as error
   * 2) Referencing a variable not in the map is an error.
   * 
   * Such errors should be handled by returning Double.NaN (i.e. not a number)
   */
  def computeValues(
      namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    namedExpressions mapValues { signal => Signal(eval(signal(), namedExpressions)) }
  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]]): Double = expr match {
    case Literal(v) => v
    case Plus(a, b) => eval(a, references) + eval(b, references)
    case Minus(a, b) => eval(a, references) - eval(b, references)
    case Times(a, b) => eval(a, references) * eval(b, references)
    case Divide(a, b) => eval(a, references) / eval(b, references)
    case Ref(name) => eval(getReferenceExpr(name, references), references - name)
  }

  /** Get the Expr for a referenced variables.
   *  If the variable is not known, returns a literal NaN.
   */
  private def getReferenceExpr(name: String,
      references: Map[String, Signal[Expr]]) = {
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
  }
}
