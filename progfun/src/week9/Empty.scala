package week9

object Empty extends IntSet{
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = NonEmpty(x, Empty, Empty)
  def union(other: IntSet) = other
}