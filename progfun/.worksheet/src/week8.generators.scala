package week8

object generators {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(78); 
  println("Welcome to the Scala worksheet");$skip(112); 
  
  val integers = new Generator[Int]{
  	val rand = new java.util.Random
  	def generate = rand.nextInt()
  };System.out.println("""integers  : week8.Generator[Int]{val rand: java.util.Random} = """ + $show(integers ));$skip(88); 
  
  val booleans = new Generator[Boolean]{
  	def generate = integers.generate > 0
  };System.out.println("""booleans  : week8.Generator[Boolean] = """ + $show(booleans ));$skip(105); 
  
  val pairs = new Generator[(Int, Int)]{
  	def generate = (integers.generate, integers.generate)
  };System.out.println("""pairs  : week8.Generator[(Int, Int)] = """ + $show(pairs ));$skip(73); 
  
  def leafs: Generator[Leaf] = for{
  	x <- integers
  }yield Leaf(x);System.out.println("""leafs: => week8.Generator[<error>]""");$skip(89); 
  
  def inner: Generator[Inner] = for{
  	l <- trees
  	r <- trees
  }yield Inner(l, r);System.out.println("""inner: => week8.Generator[<error>]""");$skip(115); 
  
  def trees: Generator[Tree] = for{
  	isLeaf <- booleans
  	tree <- if(isLeaf) leafs else inners
  }yield tree;System.out.println("""trees: => week8.Generator[<error>]""")}
}
