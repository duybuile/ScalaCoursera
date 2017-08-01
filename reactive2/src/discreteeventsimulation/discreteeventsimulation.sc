package discreteeventsimulation

object discreteeventsimulation {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  object sim extends Circuits with Parameters
  import sim._
  val in1, in2, sum, carry = new Wire             //> in1  : discreteeventsimulation.discreteeventsimulation.sim.Wire = discreteev
                                                  //| entsimulation.Gates$Wire@11baa65
                                                  //| in2  : discreteeventsimulation.discreteeventsimulation.sim.Wire = discreteev
                                                  //| entsimulation.Gates$Wire@f438e
                                                  //| sum  : discreteeventsimulation.discreteeventsimulation.sim.Wire = discreteev
                                                  //| entsimulation.Gates$Wire@c7da1e
                                                  //| carry  : discreteeventsimulation.discreteeventsimulation.sim.Wire = discrete
                                                  //| eventsimulation.Gates$Wire@1464ce8
  halfAdder(in1, in2, sum, carry)
  probe("sum", sum)                               //> sum 0 value = false
  probe("carry", carry)                           //> carry 0 value = false
  
  in1 setSignal true
  run()                                           //> **** simulation started, time =0 ****
                                                  //| sum 8 value = true
  in2 setSignal true
  run()                                           //> **** simulation started, time =8 ****
                                                  //| carry 11 value = true
                                                  //| sum 16 value = false
  in1 setSignal false
  run()                                           //> **** simulation started, time =16 ****
                                                  //| carry 19 value = false
                                                  //| sum 24 value = true
}