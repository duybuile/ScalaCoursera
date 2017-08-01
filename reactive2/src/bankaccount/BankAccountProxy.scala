package bankaccount

class BankAccountProxy (ba: BankAccount){
  def deposit(amount: Int): Unit = ba.deposit(amount)
  def withdraw(amount: Int): Unit  = ba.withdraw(amount)
}