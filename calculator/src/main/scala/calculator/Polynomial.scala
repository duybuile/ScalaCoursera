package calculator

object Polynomial {
  
  /**
   * Compute the delta = b^2 - 4*a*c
   * We use Signal/Var here so that whenever the coefficients a, b, c change, delta will change
   * immediately
   */
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal(b() * b() - 4* a() * c())
    //Var(b() * b() - 4* a() * c()) //This also works
  }
  
  /**
   * Use delta to solve the problem. The sets of roots are (-b +/- sqrt(delta))/(2*a)
   */
  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    Var(computeDelta(a, b, c)() match{
      case d: Double if d > 0 => Set( (-b() + Math.sqrt(d))/(2 * a() ), (b() + Math.sqrt(d) )/(2 * a()))
      case d: Double if d == 0 => Set( -b() / (2* a() ) ) 
      case d: Double if d < 0 => Set()
      
    })
  }
}
