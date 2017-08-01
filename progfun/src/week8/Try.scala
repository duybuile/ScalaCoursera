package week8

abstract class Try[T] {
  def flatMap[U](f: T => Try[U]): Try[U] = this match{
    case Success(x) => try f(x) catch { case NonFatal(ex) => Failure(ex)}
    case fail: Failure => fail
  }
  
  def map[U](f: T => U): Try[U] = this match{
    case Success(x) => Try(f(x))
    case fail: Failure => fail
  }
}

case class Success[T](x: T) extends Try[T]
case class Failure(ex: Exception) extends Try[Nothing]