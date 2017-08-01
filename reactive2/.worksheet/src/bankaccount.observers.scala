package bankaccount

object observers {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(83); 
  println("Welcome to the Scala worksheet");$skip(29); 
  
  val a = new BankAccount;System.out.println("""a  : bankaccount.BankAccount = """ + $show(a ));$skip(26); 
  val b = new BankAccount;System.out.println("""b  : bankaccount.BankAccount = """ + $show(b ));$skip(39); 
  val c = new Consolidator(List(a, b));System.out.println("""c  : bankaccount.Consolidator = """ + $show(c ));$skip(20); val res$0 = 
  
  c.totalBalance;System.out.println("""res0: Int = """ + $show(res$0));$skip(15); 
  a deposit 20;$skip(17); val res$1 = 
  c.totalBalance;System.out.println("""res1: Int = """ + $show(res$1));$skip(15); 
  b deposit 40;$skip(17); val res$2 = 
  c.totalBalance;System.out.println("""res2: Int = """ + $show(res$2))}
}
