package bankaccount

object account {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val account = new BankAccount                   //> java.lang.NoClassDefFoundError: bankaccount/BankAccount
                                                  //| 	at bankaccount.account$$anonfun$main$1.apply$mcV$sp(bankaccount.account.
                                                  //| scala:6)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at bankaccount.account$.main(bankaccount.account.scala:3)
                                                  //| 	at bankaccount.account.main(bankaccount.account.scala)
                                                  //| Caused by: java.lang.ClassNotFoundException: bankaccount.BankAccount
                                                  //| 	at java.net.URLClassLoader.findClass(Unknown Source)
                                                  //| 	at java.lang.ClassLoader.loadClass(Unknown Source)
                                                  //| 	at sun.misc.Launcher$AppClassLoader.loadClass(Unknown Source)
                                                  //| 	at java.lang.ClassLoader.loadClass(Unknown Source)
                                                  //| 	... 6 more
  account deposit 50
  account withdraw 20
  
  val x = new BankAccount
  val y = new BankAccount
  x deposit 30
  y withdraw(20)
  x withdraw 20

}