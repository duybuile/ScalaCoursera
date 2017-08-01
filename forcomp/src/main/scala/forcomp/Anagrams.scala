package forcomp

object Anagrams {

  /** A word is simply a `String`. Words contain lowercase and uppercase characters, 
   *  and no whitespace, punctuation or other special characters*/
  type Word = String

  /** A sentence is a `List` of words. */
  type Sentence = List[Word]

  /** `Occurrences` is a `List` of pairs of characters and positive integers saying
   *  how often the character appears.
   *  This list is sorted alphabetically w.r.t. to the character in each pair.
   *  All characters in the occurrence list are lowercase.
   *
   *  Any list of pairs of lowercase characters and their frequency which is not sorted
   *  is **not** an occurrence list.
   *
   *  Note: If the frequency of some character is zero, then that character should not be
   *  in the list.
   */
  type Occurrences = List[(Char, Int)]

  /** The dictionary is simply a sequence of words.
   *  It is predefined and obtained as a sequence using the utility method `loadDictionary`.
   */
  val dictionary: List[Word] = loadDictionary

  /** Converts the word into its character occurrence list.
   *
   *  Note: the uppercase and lowercase version of the character are treated as the
   *  same character, and are represented as a lowercase character in the occurrence list.
   *
   *  groupBy { x => x } groups a list of characters into each other. For example
   *  "ABABS" will be Map(b -> bb, s -> s, a - aa)
   *  
   *  map(x => (x._1, x._2.size) turns the list into [Char, Int]. For the above example
   *  Map(b -> bb, s -> s, a - aa) will be Map(b -> 2, s -> 1, a -> 2) 
   */
  def wordOccurrences(w: Word): Occurrences = 
    //w.toLowerCase().groupBy { x => x }.map(x => (x._1, x._2.size) ).toList.sortWith(_._1 < _._1)
    w.groupBy((elem:Char)=>elem.toLower).map((mapping)=>(mapping._1, mapping._2.length)).toList.sorted
    
  /** Converts a sentence into its character occurrence list. 
   *  Sentence is a list of words. We only need to make a String (i.e. a big Word) from all the words
   *  in the sentence and apply the wordOccurrences method on it.*/
  def sentenceOccurrences(s: Sentence): Occurrences = wordOccurrences(s.mkString(""))

  /** The `dictionaryByOccurrences` is a `Map` from different occurrences to a sequence of all
   *  the words that have that occurrence count.
   *  This map serves as an easy way to obtain all the anagrams of a word given its occurrence list.
   *
   *  For example, the word "eat" has the following character occurrence list:
   *
   *     `List(('a', 1), ('e', 1), ('t', 1))`
   *
   *  Incidentally, so do the words "ate" and "tea".
   *
   *  This means that the `dictionaryByOccurrences` map will contain an entry:
   *
   *    List(('a', 1), ('e', 1), ('t', 1)) -> Seq("ate", "eat", "tea")
   *    
   *	Use groupBy again to group all the words in dictionary which has the occurrences in the list we have
   */
  lazy val dictionaryByOccurrences: Map[Occurrences, List[Word]] = dictionary groupBy{(w: Word) => wordOccurrences(w)} 

  /** Returns all the anagrams of a given word. 
   *  This is simply to call the lazy val dictionaryByOccurrences
   */
  def wordAnagrams(word: Word): List[Word] = dictionaryByOccurrences(wordOccurrences(word))

  /** Returns the list of all subsets of the occurrence list.
   *  This includes the occurrence itself, i.e. `List(('k', 1), ('o', 1))`
   *  is a subset of `List(('k', 1), ('o', 1))`.
   *  It also include the empty subset `List()`.
   *
   *  Example: the subsets of the occurrence list `List(('a', 2), ('b', 2))` are:
   *
   *    List(
   *      List(),
   *      List(('a', 1)),
   *      List(('a', 2)),
   *      List(('b', 1)),
   *      List(('a', 1), ('b', 1)),
   *      List(('a', 2), ('b', 1)),
   *      List(('b', 2)),
   *      List(('a', 1), ('b', 2)),
   *      List(('a', 2), ('b', 2))
   *    )
   *
   *  Note that the order of the occurrence list subsets does not matter -- the subsets
   *  in the example above could have been displayed in some other order.
   */
  def combinations(occurrences: Occurrences): List[Occurrences] = occurrences match{
    //when the occurrence is empty 
    case List() => List(List())
    //when the occurrence is not empty
    case head::tail => {
      val tailComb = combinations(tail)
      // ++ is concatenate operator. 
      tailComb ++ (for {
        t <- tailComb      //for each occurrence t in the tailComb
        i <- 1 to head._2  //and for each i from 1 to the number of occurrences of head 
      }yield (head._1, i) :: t ) //returns a List(List(value of head, i), t)
    }
  }

  /** Subtracts occurrence list `y` from occurrence list `x`.
   *
   *  The precondition is that the occurrence list `y` is a subset of
   *  the occurrence list `x` -- any character appearing in `y` must
   *  appear in `x`, and its frequency in `y` must be smaller or equal
   *  than its frequency in `x`.
   *
   *  Note: the resulting value is an occurrence - meaning it is sorted
   *  and has no zero-entries.
   *  
   *  For example: 	val x = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
   *  							val y = List(('r', 1))
   *  subtract(x, y) = List(('a', 1), ('d', 1), ('l', 1))
   */
  def subtract(x: Occurrences, y: Occurrences): Occurrences = 
    // foldLeft goes from the first to the last of a list (vs. foldRight is from last to first)
    // it means we apply the operator to each element of the list
    // here: 
    //    - foldLeft returns List[(Char, Int)]
    //    - foldLeft uses operator (as a function) that returns List[Occurrences] \ an Occurrence 
     x.foldLeft(List[(Char, Int)]())((a: List[(Char, Int)], b: (Char, Int)) => {
       y.filter(_._1 == b._1) match { //b Occurrence is an element of y 
         
         // if no occurrences of y is equal to b -- returns a and b (:+ means append)
         case List() => a :+ b 
         // if there is occurrence and the number of occurrence is the same to what is in b
         // remove b and return only a
         case (h, b._2) :: xs => a 
         // if there is occurrence and the number of occurrence is different to what is in b
         // returns a followed by the remaining number of occurrences
         case h :: xs => a :+ (h._1, b._2 - h._2)
       }
     })

  /** Returns a list of all anagram sentences of the given sentence.
   *
   *  An anagram of a sentence is formed by taking the occurrences of all the characters of
   *  all the words in the sentence, and producing all possible combinations of words with those characters,
   *  such that the words have to be from the dictionary.
   *
   *  The number of words in the sentence and its anagrams does not have to correspond.
   *  For example, the sentence `List("I", "love", "you")` is an anagram of the sentence `List("You", "olive")`.
   *
   *  Also, two sentences with the same words but in a different order are considered two different anagrams.
   *  For example, sentences `List("You", "olive")` and `List("olive", "you")` are different anagrams of
   *  `List("I", "love", "you")`.
   *
   *  Here is a full example of a sentence `List("Yes", "man")` and its anagrams for our dictionary:
   *
   *    List(
   *      List(en, as, my),
   *      List(en, my, as),
   *      List(man, yes),
   *      List(men, say),
   *      List(as, en, my),
   *      List(as, my, en),
   *      List(sane, my),
   *      List(Sean, my),
   *      List(my, en, as),
   *      List(my, as, en),
   *      List(my, sane),
   *      List(my, Sean),
   *      List(say, men),
   *      List(yes, man)
   *    )
   *
   *  The different sentences do not have to be output in the order shown above - any order is fine as long as
   *  all the anagrams are there. Every returned word has to exist in the dictionary.
   *
   *  Note: in case that the words of the sentence are in the dictionary, then the sentence is the anagram of itself,
   *  so it has to be returned in this list.
   *
   *  Note: There is only one anagram of an empty sentence.
   */
  def sentenceAnagrams(sentence: Sentence): List[Sentence] = {
    
    /**
     * The function returns a list of all sentences from all occurrences
     */
    def occurrenceAnagrams(occurrences: Occurrences): List[Sentence] = {
      if(occurrences.isEmpty) List(List())
      else {
        for{
          // Combines all the occurrences of the input sentence to a list of list of occurrences 
          occurrenceCombination <- combinations(occurrences)
          // Takes all the dictionary words constructed from the list of list of generated occurrences
          word <- dictionaryByOccurrences(occurrenceCombination)
          // The remaining words are the occurrences which we haven't work on
          remainingwords <- occurrenceAnagrams(subtract(occurrences, occurrenceCombination))
        } yield word::remainingwords
      }
    }
    
    // Turns the input sentence into a list of Occurrences and invoke the occurrenceAnagrams function
    occurrenceAnagrams(sentenceOccurrences(sentence))
  }
}
