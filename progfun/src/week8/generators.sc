package week8

object generators {
  println("Welcome to the Scala worksheet")
  
  val integers = new Generator[Int]{
  	val rand = new java.util.Random
  	def generate = rand.nextInt()
  }
  
  val booleans = new Generator[Boolean]{
  	def generate = integers.generate > 0
  }
  
  val pairs = new Generator[(Int, Int)]{
  	def generate = (integers.generate, integers.generate)
  }
  
  def leafs: Generator[Leaf] = for{
  	x <- integers
  }yield Leaf(x)
  
  def inner: Generator[Inner] = for{
  	l <- trees
  	r <- trees
  }yield Inner(l, r)
  
  def trees: Generator[Tree] = for{
  	isLeaf <- booleans
  	tree <- if(isLeaf) leafs else inners
  }yield tree
}