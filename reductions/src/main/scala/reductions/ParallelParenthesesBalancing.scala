package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {
    def checkCount(chars: Array[Char], count: Int): Boolean = {
      if (chars.isEmpty) count == 0
      else if (count < 0) false
      else if (chars.head == '(') checkCount(chars.tail, count + 1)
      else if (chars.head == ')') checkCount(chars.tail, count - 1)
      else checkCount(chars.tail, count)
    }
    
    checkCount(chars, 0)
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   *  Use a fixed threshold
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    /**
     * The function runs through the string from the index to the until (the starting 
     * and ending point of the string)
     * @param idx: index of the string
     * @param until: the end of the string
     * @param arg1: count of '('
     * @param arg2: count of ')'
     * A string has the correct syntax of parentheses if arg1 = arg2 = 0 after traverse finishes
     */
    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int) : (Int, Int) = {
      if(idx < until){
        chars(idx) match{
          case '(' => traverse(idx + 1, until, arg1 + 1, arg2)
          case ')' => 
            if (arg1 > 0)
              traverse(idx + 1, until, arg1 - 1, arg2)
            else traverse(idx + 1, until, arg1, arg2 + 1)
          case _ => traverse(idx + 1, until, arg1, arg2)
        }
        
      } else (arg1, arg2)
    }

    /**
     * The function to divide the string into two parts and run reduce with parallel
     * @param from: starting of the string
     * @param until: ending of the string
     */
    def reduce(from: Int, until: Int) : (Int, Int) = {
      val len = until - from
      if(len <= threshold) traverse(from, until, 0, 0)
      else {
        val half = from + len/2
        // Divide the job into two parts
        val ((x1, x2),(y1, y2)) = parallel(reduce(from, half), reduce(half, until))
        if(x1 > y2) (x1 - y2 + y1, x2)
        else (y1, y2 - x1 + x2)
      }
    }
    //check if it will return (0, 0). TRUE == Balanced
    reduce(0, chars.length) == (0, 0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
