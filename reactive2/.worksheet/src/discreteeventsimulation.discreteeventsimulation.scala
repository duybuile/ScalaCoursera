package discreteeventsimulation

object discreteeventsimulation {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(109); 
  println("Welcome to the Scala worksheet")
  
  object sim extends Circuits with Parameters
  import sim._;$skip(102); 
  val in1, in2, sum, carry = new Wire;System.out.println("""in1  : discreteeventsimulation.discreteeventsimulation.sim.Wire = """ + $show(in1 ));System.out.println("""in2  : discreteeventsimulation.discreteeventsimulation.sim.Wire = """ + $show(in2 ));System.out.println("""sum  : discreteeventsimulation.discreteeventsimulation.sim.Wire = """ + $show(sum ));System.out.println("""carry  : discreteeventsimulation.discreteeventsimulation.sim.Wire = """ + $show(carry ));$skip(34); 
  halfAdder(in1, in2, sum, carry);$skip(20); 
  probe("sum", sum);$skip(24); 
  probe("carry", carry);$skip(24); 
  
  in1 setSignal true;$skip(8); 
  run();$skip(21); 
  in2 setSignal true;$skip(8); 
  run();$skip(22); 
  in1 setSignal false;$skip(8); 
  run()}
}
