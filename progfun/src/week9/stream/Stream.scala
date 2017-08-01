package week9.stream

//trait Stream[+A] extends Seq[A] {
//  def isEmpty: Boolean
//  def head: A
//  def tail: Stream[A]
//}

object Stream{
  def cons[T](hd: T, tl: => Stream[T]) = new Stream[T]{
    def isEmpty = false
    def head = hd
    def tail = tl
  }
  
  val empty = new Stream[Nothing]{
    def isEmpty = true
    def head = throw new NoSuchElementException("empty.head")
    def tail = throw new NoSuchElementException("empty.tail")
  }
}

class Stream[+T]{
  
  def filter(p: T=> Boolean): Stream[T]={
    if(isEmpty) this
    else if (p(head)) cons(head, tail.filter(p))
    else tail.filter(p)
  }
  
  def streamRange(lo: Int, hi: Int): Stream[Int] = {
      print(lo+"")
      if(lo >= hi) Stream.empty
      else Stream.cons(lo, streamRange(lo+1, hi))
    }
}