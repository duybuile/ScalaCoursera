package bankaccount

/**
 * Consolidater is an subscriber, it subscribe all bank accounts
 */
class Consolidator(observed: List[BankAccount]) extends Subscriber {
  observed.foreach { _.subscribe(this) }
  
  private var total: Int = _
  compute()
  
  private def compute() = 
    total = observed.map { _.currentBalance }.sum
    
  def handler(pub: Publisher) = compute
  
  def totalBalance = total
}