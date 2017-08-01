package bankaccountsignal

import scala.util.DynamicVariable

/**
 * Each signal maintains 1) its current value, 2) the current expression that defines the signal
 * value, 3) a set of observers: the other signals that depend on its value
 * 
 * Then if a signal changes, all of its observations need to be re-calculated
 */
class Signal[T](expr: => T) {
  import Signal._
  private var myExpr:() => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set()
  update(expr)
  
  def apply() = {
    observers += caller.value
    assert(!caller.value.observers.contains(this), "cyclic signal definition")
    myValue
  }
  
  /**
   * The signal's current value can change when somebody calls an update operation
   */
  protected def update(expr: => T): Unit = {
    myExpr = () => expr
    computeValue()
  }
  
  protected def computeValue(): Unit = {
    val newValue = caller.withValue(this)(myExpr()) +
    if (myValue != newValue) {
      myValue = newValue
      val obs = observers
      observers = Set()
      obs.foreach(_.computeValue())
    }
  }
}

object Signal{
  /**
   * Dynamic Variable can help with thread implementation, however, it still has a few disadvantages
   * 1) hard to manage, 2) JDK involves global hash table look up --> low performance
   * 3) does not work well when threads are multiplexed between several tasks 
   * 
   * New version of Scala may solve this
   */
  private val caller = new DynamicVariable[Signal[_]](NoSignal)
  def apply[T](expr: => T) = new Signal(expr)
}

/**
 * A special Signal but does not have anything at all
 */
object NoSignal extends Signal[Nothing](???) { 
  
  override def computeValue() = ()
}