package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._
import Math._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    head <- arbitrary[Int]
    tail <- oneOf(const(empty), genHeap)
  }yield insert(head, tail)
  
  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)
  
  /**
   * Property 1: Insert an elem into an empty heap, find the min of the resulting heap and get the elem
   */
  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }
  
  /**
   * Property 2: Insert an integer into an empty heap, find the min of the heap to get the integer
   */
  property("min1") = forAll { a:Int => 
    val h = insert(a, empty)
    findMin(h) == a
  }
  
  /**
   * Property 3: Insert 2 element into heap, heap should result in the smallest
   */
   property("insert2") = forAll { (a: Int, b: Int) =>
     val h1 = insert(a, empty)
     val h2 = insert(b, h1)
     findMin(h2) == min(a, b)
   }
   
   /**
    * Property 4: Insert an elem into an empty heap, delete that elem, hep then should be empty
    */
   property("deleteMin") = forAll{ (a:Int) =>
     val h1 = insert(a, empty)
     val h2 = deleteMin(h1) 
     isEmpty(h2)
   }
   
   /**
    * Property 5: Get a sorted sequence of elems when continually finding and deleting minima
    */
//   property("sort") = forAll{ (h: H) =>
//     def helper(h: H): List[Int] = {
//       if(isEmpty(h)) Nil
//       else findMin(h) :: helper(deleteMin(h))
//     }
//     
//     val l = helper(h)
//     l == l.sorted
//   }
   
//   property("sort") = forAll{ (h: H) =>
//     def isSorted(last: Int, heap: H): Boolean = {
//       isEmpty(heap) || {
//         val m = findMin(heap)
//         last <= m && isSorted(m, deleteMin(heap))
//       }
//     }
//     isEmpty(h) || isSorted(findMin(h), deleteMin(h))
//   }

     property("sortedSequence1") = forAll { (a: Int, b: Int, c: Int) =>
       val list = List(a, b, c).sorted
       val heap1 = deleteMin(list.foldLeft(empty) { (h, e) => insert(e, h) })
       val heap2 = list.tail.foldLeft(empty) { (h, e) => insert(e, h) }
       heap1 == heap2
    }

   /**
    * Property 6: Meld 2 heaps, the min should be min of either of the heaps
    */
   property("meld") = forAll{ (h1: H, h2: H) =>
     val minH = findMin(meld(h1, h2))
     minH == findMin(h1) || minH == findMin(h2)
   }
   
   /**
    * Property 7: Meld heap has the same length as total length of the other two heaps
    */
   property("total meld") = forAll {(h1: H, h2: H) =>
     def helper(h: H): List[Int] = {
       if(isEmpty(h)) Nil
       else findMin(h) :: helper(deleteMin(h))
     }
     val h3 = meld(h1, h2)
     helper(h3).length == helper(h1).length + helper(h2).length
   }
   
   /**
    * Property 8: Same elem twice
    */
   property("same elemement twice") = forAll{ (a: Int) =>
     val h1 = insert(a, insert(a, empty))
     val m1 = findMin(h1)
     val h2 = deleteMin(h1)
     
     if(!isEmpty(h1)) {
       val m2 = findMin(h2)
       val h3 = deleteMin(h2)
       m1 == m2 && isEmpty(h3) == true
     }else false
   }
   
   /**
    * Property 9: Meld the same heap
    */
   property("Meld same heap") = forAll{ (h: H) =>
     def helper(h: H): List[Int] = {
       if(isEmpty(h)) Nil
       else findMin(h) :: helper(deleteMin(h))
     }
     
     val h1 = meld(h, h)
     val m1 = findMin(h1)
     val h2 = deleteMin(h1)
     val m2 = findMin(h2)
     m1 == m2 && helper(h1).length == 2 * helper(h).length
   }
   
   /**
    * Property 10: Minimum of the two heaps
    */
   property("Find min of the two heaps and min of the meld heap") = forAll{(h1: H, h2: H) =>
     val m1 = if(isEmpty(h1)) 0 else findMin(h1)
     val m2 = if(isEmpty(h2)) 0 else findMin(h2)
     val m = findMin(meld(h1, h2))
     m == min(m1, m2)
   }
}
