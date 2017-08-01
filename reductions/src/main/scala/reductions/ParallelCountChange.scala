package reductions

import org.scalameter._
import common._

object ParallelCountChangeRunner {

  @volatile var seqResult = 0

  @volatile var parResult = 0

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 80,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val amount = 250
    val coins = List(1, 2, 5, 10, 20, 50)
    val seqtime = standardConfig measure {
      seqResult = ParallelCountChange.countChange(amount, coins)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential count time: $seqtime ms")

    def measureParallelCountChange(threshold: ParallelCountChange.Threshold): Unit = {
      val fjtime = standardConfig measure {
        parResult = ParallelCountChange.parCountChange(amount, coins, threshold)
      }
      println(s"parallel result = $parResult")
      println(s"parallel count time: $fjtime ms")
      println(s"speedup: ${seqtime / fjtime}")
    }

    measureParallelCountChange(ParallelCountChange.moneyThreshold(amount))
    measureParallelCountChange(ParallelCountChange.totalCoinsThreshold(coins.length))
    measureParallelCountChange(ParallelCountChange.combinedThreshold(amount, coins))
  }
}

object ParallelCountChange {

  /** Returns the number of ways change can be made from the specified list of
   *  coins for the specified amount of money.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    money match{
      case 0 => 1
      case _ => if(money < 0) 0 else {
        coins match{
          case Nil => 0
          case (head :: tail) => countChange(money - head, coins) + countChange(money, tail)
        }
      }
    }
  }

  type Threshold = (Int, List[Int]) => Boolean

  /** In parallel, counts the number of ways change can be made from the
   *  specified list of coins for the specified amount of money.
   */
  def parCountChange(money: Int, coins: List[Int], threshold: Threshold): Int = {
    if(threshold(money, coins) || money <= 0 || coins.isEmpty) countChange(money, coins)
    else { //use parallel on two sides -- left and right 
      val (left, right) = parallel[Int, Int](
          parCountChange(money - coins.head, coins, threshold),
          parCountChange(money, coins.tail, threshold)
          )
      left + right
    }
  }

  /** Threshold heuristic based on the starting money. Return TRUE when the amount of money
   *  is less than or equal to 2/3 of the starting amount */
  def moneyThreshold(startingMoney: Int): Threshold = 
    (money, coins) => money <= (2 * startingMoney) / 3

  /** Threshold heuristic based on the total number of initial coins. Return TRUE when the 
   *  number of coins is less than or equal to the 2/3 of the initial number of coins*/
  def totalCoinsThreshold(totalCoins: Int): Threshold = 
    (money, coins) => coins.length <= (2 * totalCoins) / 3 

  /** Threshold heuristic based on the starting money and the initial list of coins. Return TRUE 
   *  when the amount of money multiplied with the number of remaining coins is less than or 
   *  equal to the starting money multiplied with the initial number of coins divided by 2*/
  def combinedThreshold(startingMoney: Int, allCoins: List[Int]): Threshold = {
    //(money, coins) => money * coins.length <= (2/3) * (startingMoney * allCoins.length)
      (money, coins) => moneyThreshold(startingMoney)(money, coins) && totalCoinsThreshold(allCoins.length)(money, coins)
  }
}