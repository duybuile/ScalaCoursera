package week4

import math.Ordering

class mergesort {
  def msort(xs: List[Int]): List[Int] = {
    val n = xs.length/2
    if(n == 0) xs
    else{
      def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
        case(Nil, ys) => ys //nothing to merge
        case(xs, Nil) => xs //nothing to merge
        case(x::xs1, y::ys1) =>
          if (x < y) x :: merge(xs1, ys)
          else y :: merge(xs, ys1) 
      }
      val (fst, snd) = xs splitAt n
      merge(msort(fst), msort(snd))
    }
  }
  
  def msort2[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {
    val n = xs.length/2
    if(n == 0) xs
    else{
      def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
        case(Nil, ys) => ys //nothing to merge
        case(xs, Nil) => xs //nothing to merge
        case(x::xs1, y::ys1) =>
          if (lt(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1) 
      }
      val (fst, snd) = xs splitAt n
      merge(msort2(fst)(lt), msort2(snd)(lt))
    }
  }
  
  /**
   * implicit parameter will show the compiler the Ordering so we don't need to specify ordering
   * in the function call
   */
  def msort3[T](xs: List[T])(implicit ord: math.Ordering[T]): List[T] = {
    val n = xs.length/2
    if(n == 0) xs
    else{
      def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
        case(Nil, ys) => ys //nothing to merge
        case(xs, Nil) => xs //nothing to merge
        case(x::xs1, y::ys1) =>
          if (ord.lt(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1) 
      }
      val (fst, snd) = xs splitAt n
      merge(msort3(fst), msort3(snd))
    }
  }
}