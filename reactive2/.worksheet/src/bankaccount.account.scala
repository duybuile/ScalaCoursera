package bankaccount

object account {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(81); 
  println("Welcome to the Scala worksheet");$skip(35); 
  
  val account = new BankAccount;System.out.println("""account  : bankaccount.BankAccount = """ + $show(account ));$skip(21); 
  account deposit 50;$skip(22); 
  account withdraw 20;$skip(29); 
  
  val x = new BankAccount;System.out.println("""x  : bankaccount.BankAccount = """ + $show(x ));$skip(26); 
  val y = new BankAccount;System.out.println("""y  : bankaccount.BankAccount = """ + $show(y ));$skip(15); 
  x deposit 30;$skip(17); 
  y withdraw(20);$skip(16); 
  x withdraw 20}

}
