package reductions

import org.scalameter._
import common._

object LineOfSightRunner {
  
  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 100,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]) {
    val length = 10000000
    val input = (0 until length).map(_ % 100 * 1.0f).toArray
    val output = new Array[Float](length + 1)
    val seqtime = standardConfig measure {
      LineOfSight.lineOfSight(input, output)
    }
    println(s"sequential time: $seqtime ms")

    val partime = standardConfig measure {
      LineOfSight.parLineOfSight(input, output, 10000)
    }
    println(s"parallel time: $partime ms")
    println(s"speedup: ${seqtime / partime}")
  }
}

object LineOfSight {

  def max(a: Float, b: Float): Float = if (a > b) a else b

  /**
   * For each height entry in the input array (except the location of observer at the 0),
   * writes the maximum angle to the output array
   * output(0) = 0
   */
  def lineOfSight(input: Array[Float], output: Array[Float]): Unit = {
    input.zipWithIndex.foreach{
      case(xs, 0) => output(0) = 0
      case(xs, i) => output(i) = Math.max(xs/i, output(i - 1))
    }
  }

  sealed abstract class Tree {
    def maxPrevious: Float
  }

  case class Node(left: Tree, right: Tree) extends Tree {
    val maxPrevious = max(left.maxPrevious, right.maxPrevious)
  }

  case class Leaf(from: Int, until: Int, maxPrevious: Float) extends Tree

  /** Traverses the specified part of the array and returns the maximum angle.
   *  @param input: an array of angles
   *  @param from
   *  @param until
   */
  def upsweepSequential(input: Array[Float], from: Int, until: Int): Float = {
    
//    def findMaximumAngle(index: Int, angle: Float): Float = {
//      if(index < until) findMaximumAngle(index, angle)
//      else angle
//    }
//    
//    findMaximumAngle(0, input(0))
    
    //use foldLeft to find the maxium angle
    (from until until).foldLeft(0f)((maxAngle, index) => List(input(index)/index, maxAngle).max)
  }

  /** Traverses the part of the array starting at `from` and until `end`, and
   *  returns the reduction tree for that part of the array.
   *
   *  The reduction tree is a `Leaf` if the length of the specified part of the
   *  array is smaller or equal to `threshold`, and a `Node` otherwise.
   *  If the specified part of the array is longer than `threshold`, then the
   *  work is divided and done recursively in parallel.
   */
  def upsweep(input: Array[Float], from: Int, end: Int,
    threshold: Int): Tree = {
    val len = end - from
    if(len <= threshold) Leaf(from, end, upsweepSequential(input, from, end))
    else {
      val half = from + len/2
      val (left, right) = parallel(upsweep(input, from, half, threshold), 
          upsweep(input, half, end, threshold)) 
      Node(left, right)
    }
  }

  /** Traverses the part of the `input` array starting at `from` and until
   *  `until`, and computes the maximum angle for each entry of the output array,
   *  given the `startingAngle`.
   */
  def downsweepSequential(input: Array[Float], output: Array[Float],
    startingAngle: Float, from: Int, until: Int): Unit = {
    if(from < until) {
      val maxAngle = max(input(from)/from, startingAngle)
      output(from) = maxAngle
      downsweepSequential(input, output, maxAngle, from + 1, until)
    }
  }

  /** Pushes the maximum angle in the prefix of the array to each leaf of the
   *  reduction `tree` in parallel, and then calls `downsweepTraverse` to write
   *  the `output` angles.
   *  
   *  Two cases -- 
   *   -- Leaf node -- use the sequential
   *   -- Node -- divide the rest of them into 2 trees -- left and right
   */
  def downsweep(input: Array[Float], output: Array[Float], startingAngle: Float,
    tree: Tree): Unit = {
    tree match{
      //Left node from to until, maxPrevious is anything
      case Leaf(from, until, _) => downsweepSequential(input, output, startingAngle, from, until)
      //A node with left tree and right tree
      case Node(left, right) => parallel(downsweep(input, output, startingAngle, left), 
          downsweep(input, output, startingAngle, right))
    }
  }

  /** Compute the line-of-sight in parallel. */
  def parLineOfSight(input: Array[Float], output: Array[Float],
    threshold: Int): Unit = {
    downsweep(input, output, 1, upsweep(input, 1, input.length, threshold))
  }
}
