package bankaccount

object observers {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val a = new BankAccount                         //> a  : bankaccount.BankAccount = bankaccount.BankAccount@16532d6
  val b = new BankAccount                         //> b  : bankaccount.BankAccount = bankaccount.BankAccount@196751b
  val c = new Consolidator(List(a, b))            //> c  : bankaccount.Consolidator = bankaccount.Consolidator@7471b5
  
  c.totalBalance                                  //> res0: Int = 0
  a deposit 20
  c.totalBalance                                  //> res1: Int = 20
  b deposit 40
  c.totalBalance                                  //> res2: Int = 60
}