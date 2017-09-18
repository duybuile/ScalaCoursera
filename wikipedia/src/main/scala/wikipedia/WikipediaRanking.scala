package wikipedia

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

import org.apache.spark.rdd.RDD

case class WikipediaArticle(title: String, text: String) {
  /**
    * @return Whether the text of this article mentions `lang` or not
    * @param lang Language to look for (e.g. "Scala")
    */
  def mentionsLanguage(lang: String): Boolean = text.split(' ').contains(lang)
}

object WikipediaRanking {

  val langs = List(
    "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
    "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")

  
  val conf: SparkConf = new SparkConf().setMaster("local[4]").setAppName("Wikipedia").set("spark.driver.host", "localhost")
  conf.set("spark.testing.memory", "2147480000")
  val sc: SparkContext = new SparkContext(conf)
  
  // Hint: use a combination of `sc.textFile`, `WikipediaData.filePath` and `WikipediaData.parse`
  val wikiRdd: RDD[WikipediaArticle] = sc.textFile(WikipediaData.filePath).map { WikipediaData.parse }
  
  /** Returns TRUE if the article contains the language,  FALSE otherwise
   * @author buidu
   * @param lang Language
   * @param article Wikipedia Article
   */
  def containsLanguage(lang: String, article: WikipediaArticle): Int = 
    if(article.text
      .split(" ")
      .contains(lang)) 1 else 0

  /** Returns the number of articles on which the language `lang` occurs.
   *  Hint1: consider using method `aggregate` on RDD[T].
   *  Hint2: consider using method `mentionsLanguage` on `WikipediaArticle`
   *  
   *  Step1: filter the rdd text (convert to lower case and split them by a space) if they are contains words in lang
   *  Step2: count the number
   */
  def occurrencesOfLang(lang: String, rdd: RDD[WikipediaArticle]): Int = 
//    rdd.filter(_.text.toLowerCase.split(" ")
//        .contains(lang.toLowerCase))
//        .count()
//        .toInt
      rdd.aggregate(0)((sum, article) => sum + containsLanguage(lang, article), _+_)

  /** (1) Use `occurrencesOfLang` to compute the ranking of the languages
   *     (`val langs`) by determining the number of Wikipedia articles that
   *     mention each language at least once. Don't forget to sort the
   *     languages by their occurrence, in decreasing order!
   *
   *   Note: this operation is long-running. It can potentially run for
   *   several seconds.
   *   
   *   Eg: return - List(("Scala", 999999), ("JavaScript", 1278), ("LOLCODE", 982), ("Java", 42))
   */
  def rankLangs(langs: List[String], rdd: RDD[WikipediaArticle]): List[(String, Int)] = {
    rdd.cache()
    langs.map { lan => (lan, occurrencesOfLang(lan, rdd)) }
      .sortBy(_._2)
      .reverse
  }

  /** Compute an inverted index of the set of articles, mapping each language
   *  to the Wikipedia pages in which it occurs.
   *  
   *  Inverted index: an index data structure storing a mapping from content, 
   *  such as words or numbers, to a set of documents. Its purpose is fast full text searches.
   *  
   *  Return: an RDD of the following type: RDD[(String, Iterable[WikipediaArticle])]
   *  This RDD contains pairs, such that for each language in the given langs list there is at most one pair
   *  The second component of each pair (the Iterable) contains the WikipediaArticles 
   *  that mention the language at least once.
   *  
   *  Hint: You might want to use methods flatMap and groupByKey on RDD for this part
   */
  def makeIndex(langs: List[String], rdd: RDD[WikipediaArticle]): RDD[(String, Iterable[WikipediaArticle])] = {
//    rdd.flatMap { article => {
//      val langsMentioned = langs.filter { lang => article.mentionsLanguage(lang) }
//      langsMentioned.map { lang => (lang, article) }
//    } }.groupByKey()
    
    rdd.flatMap{ article => for (l <- langs if article.mentionsLanguage(l)) yield (l, article) }
      .groupByKey()
  }

  /** (2) Compute the language ranking again, but now using the inverted index. Can you notice
   *     a performance improvement?
   *
   *   Note: this operation is long-running. It can potentially run for
   *   several seconds.
   *   
   *   Return: 
   *   Eg: List(("Scala", 999999), ("JavaScript", 1278), ("LOLCODE", 982), ("Java", 42))
   */
  def rankLangsUsingIndex(index: RDD[(String, Iterable[WikipediaArticle])]): List[(String, Int)] =
    index.mapValues {_.size} //mapValues is a func from rdd
      .sortBy(-_._2)
      .collect()
      .toList 

  /** (3) Use `reduceByKey` so that the computation of the index and the ranking are combined.
   *     Can you notice an improvement in performance compared to measuring *both* the computation of the index
   *     and the computation of the ranking? If so, can you think of a reason?
   *
   *   Note: this operation is long-running. It can potentially run for
   *   several seconds.
   */
  def rankLangsReduceByKey(langs: List[String], rdd: RDD[WikipediaArticle]): List[(String, Int)] = {
    rdd.flatMap(article => for(l <- langs if article.mentionsLanguage(l)) yield (l, 1))
      .reduceByKey(_ + _)
      .sortBy(-_._2)
      .collect()
      .toList
    }

    /**
     * @author buidu
     *
     */
  def main(args: Array[String]) {

    /* Languages ranked according to (1) */
    val langsRanked: List[(String, Int)] = timed("Part 1: naive ranking", rankLangs(langs, wikiRdd))

    /* An inverted index mapping languages to wikipedia pages on which they appear */
    def index: RDD[(String, Iterable[WikipediaArticle])] = makeIndex(langs, wikiRdd)

    /* Languages ranked according to (2), using the inverted index */
    val langsRanked2: List[(String, Int)] = timed("Part 2: ranking using inverted index", rankLangsUsingIndex(index))

    /* Languages ranked according to (3) */
    val langsRanked3: List[(String, Int)] = timed("Part 3: ranking using reduceByKey", rankLangsReduceByKey(langs, wikiRdd))

    /* Output the speed of each ranking */
    println(timing)
    sc.stop()
  }

  val timing = new StringBuffer
  def timed[T](label: String, code: => T): T = {
    val start = System.currentTimeMillis()
    val result = code
    val stop = System.currentTimeMillis()
    timing.append(s"Processing $label took ${stop - start} ms.\n")
    result
  }
}
