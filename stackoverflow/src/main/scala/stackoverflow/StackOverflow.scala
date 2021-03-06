package stackoverflow

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import annotation.tailrec
import scala.reflect.ClassTag

/** A raw stackoverflow posting, either a question or an answer */
case class Posting(postingType: Int, id: Int, acceptedAnswer: Option[Int], parentId: Option[QID], score: Int, tags: Option[String]) extends Serializable


/** The main class */
object StackOverflow extends StackOverflow {

  @transient lazy val conf: SparkConf = new SparkConf().setMaster("local").setAppName("StackOverflow")
  conf.set("spark.testing.memory", "2147480000")
  @transient lazy val sc: SparkContext = new SparkContext(conf)

  /** Main function 
   *  Download the data stackoverflow.csv at the following address
   *  http://alaska.epfl.ch/~dockermoocs/bigdata/stackoverflow.csv
   *  Leave it in the folder src/main/resources/stackoverflow/stackoverflow.csv
   *  */
  def main(args: Array[String]): Unit = {

    val lines   = sc.textFile("src/main/resources/stackoverflow/stackoverflow.csv")
    /** Raw Posting entries for each line*/
    val raw     = rawPostings(lines)
    /** Questions and Answers grouped together*/
    val grouped = groupedPostings(raw)
    /** Questions and Scores*/
    val scored  = scoredPostings(grouped)
    /** A pair of (language, score) for each question*/
    val vectors = vectorPostings(scored)
//    assert(vectors.count() == 2121822, "Incorrect number of vectors: " + vectors.count())

    val means   = kmeans(sampleVectors(vectors), vectors, debug = true)
    val results = clusterResults(means, vectors)
    printResults(results)
  }
}


/** The parsing and kmeans methods */
class StackOverflow extends Serializable {

  /** Languages */
  val langs =
    List(
      "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
      "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")

  /** K-means parameter: How "far apart" languages should be for the kmeans algorithm? 
   *  Basically, it makes sure posts about different programming languages have at least 
   *  distance 50000 using the distance measure provided by the euclideanDist function
   *  */
  def langSpread = 50000 
  assert(langSpread > 0, "If langSpread is zero we can't recover the language from the input data!")

  /** K-means parameter: Number of clusters */
  def kmeansKernels = 45

  /** K-means parameter: Convergence criteria */
  def kmeansEta: Double = 20.0D

  /** K-means parameter: Maximum iterations */
  def kmeansMaxIterations = 120


  //
  //
  // Parsing utilities:
  //
  //

  /** Load postings from the given file 
   *  @return an RDD of Posting */
  def rawPostings(lines: RDD[String]): RDD[Posting] =
    lines.map(line => {
      val arr = line.split(",")
      Posting(postingType =    arr(0).toInt,
              id =             arr(1).toInt,
              acceptedAnswer = if (arr(2) == "") None else Some(arr(2).toInt),
              parentId =       if (arr(3) == "") None else Some(arr(3).toInt),
              score =          arr(4).toInt,
              tags =           if (arr.length >= 6) Some(arr(5).intern()) else None)
    })


  /** Group the questions and answers together 
   *  @author buidu
   *  @param postings - original RDD
   *  @return a pair RDD: key is postings.id, value is a pair of Question and Answer to that postings.id
   *  [question id, (question, answer)]*/
  def groupedPostings(postings: RDD[Posting]): RDD[(QID, Iterable[(Question, Answer)])] = {
    val arr = postings.map{
      posting => 
        if(posting.postingType == 1) //postingType ==  1: Question 
          (posting.id, posting)  
        else //postingType == 2: Answer
          (posting.parentId.getOrElse(-1), posting)} 
    
    val questions = arr.filter(_._2.postingType == 1) //questions = (posting.id, posting as questions)
    val answers = arr.filter(_._2.postingType == 2)  //answers = (posting.id, posting as answers)
    questions.join(answers).groupByKey() //returns: [question id, (question, answer)]
    
  }


  /** Compute the maximum score for each posting 
   *  @author buidu
   *  @param [QuestionID, (Question, Answer)]
   *  @return [Question, HighScore] with HighScore as integer */
  def scoredPostings(grouped: RDD[(QID, Iterable[(Question, Answer)])]): RDD[(Question, HighScore)] = {

    def answerHighScore(as: Array[Answer]): HighScore = {
      var highScore = 0
          var i = 0
          while (i < as.length) {
            val score = as(i).score
                if (score > highScore)
                  highScore = score
                  i += 1
          }
      highScore
    }
    
    //Transform grouped to (Question, Score)
    grouped.map{gr => {
      val temp = gr._2.unzip //[(Question, Answer)] -> 2 collections
      (temp._1.head, answerHighScore(temp._2.toArray))} 
    }
  
  }


  /** Compute the vectors for the kmeans. Vector has 2 components
   *   - Index of the language (in the language list) multipled by langSpread factor (= 5000)
   *   - The highest answer score (computed above) 
   *  @return RDD[(LangIndex, HighScore)] is a VectorPostings
   *  */
  def vectorPostings(scored: RDD[(Question, HighScore)]): RDD[(LangIndex, HighScore)] = {
    
    /** Return optional index of first language that occurs in `tags` 
     *  @author coursera
     *  @param tag Tag can be found in the Question (could be Java, R, Scala, etc.)
     *  @param ls List of languages and their index (in the variable langs)
     *  @return the corresponding index of a language
     *  */
    def firstLangInTag(tag: Option[String], ls: List[String]): Option[Int] = {
      if (tag.isEmpty) None
      else if (ls.isEmpty) None
      else if (tag.get == ls.head) Some(0) // index: 0
      else {
        val tmp = firstLangInTag(tag, ls.tail)
        tmp match {
          case None => None
          case Some(i) => Some(i + 1) // index i in ls.tail => index i+1
        }
      }
    }

    // Since firstLangInTag returns Option[Int], hint to use getOrElse(0). If the index is not found,
    // it will return 0
    scored.map{sc => 
       (firstLangInTag(sc._1.tags, langs).getOrElse(0)*langSpread, sc._2) 
    }.cache()
  }


  /** Sample the vectors */
  def sampleVectors(vectors: RDD[(LangIndex, HighScore)]): Array[(Int, Int)] = {

    assert(kmeansKernels % langs.length == 0, "kmeansKernels should be a multiple of the number of languages studied.")
    val perLang = kmeansKernels / langs.length

    // http://en.wikipedia.org/wiki/Reservoir_sampling
    def reservoirSampling(lang: Int, iter: Iterator[Int], size: Int): Array[Int] = {
      val res = new Array[Int](size)
      val rnd = new util.Random(lang)

      for (i <- 0 until size) {
        assert(iter.hasNext, s"iterator must have at least $size elements")
        res(i) = iter.next
      }

      var i = size.toLong
      while (iter.hasNext) {
        val elt = iter.next
        val j = math.abs(rnd.nextLong) % i
        if (j < size)
          res(j.toInt) = elt
        i += 1
      }

      res
    }

    val res =
      if (langSpread < 500)
        // sample the space regardless of the language
        vectors.takeSample(false, kmeansKernels, 42)
      else
        // sample the space uniformly from each language partition
        vectors.groupByKey.flatMap({
          case (lang, vectors) => reservoirSampling(lang, vectors.toIterator, perLang).map((lang, _))
        }).collect()

    assert(res.length == kmeansKernels, res.length)
    res
  }


  //
  //
  //  Kmeans method:
  //
  //

  /** Main kmeans computation */
  @tailrec final def kmeans(means: Array[(Int, Int)], vectors: RDD[(Int, Int)], iter: Int = 1, debug: Boolean = false): Array[(Int, Int)] = {
    val newMeans = means.clone() // you need to compute newMeans
    
    val tmp = vectors.map(p => (findClosest(p, means), p)).groupByKey().mapValues(averageVectors).collect()
    tmp.foreach(a => newMeans.update(a._1, a._2))

    // TODO: Fill in the newMeans array
    val distance = euclideanDistance(means, newMeans)

    if (debug) {
      println(s"""Iteration: $iter
                 |  * current distance: $distance
                 |  * desired distance: $kmeansEta
                 |  * means:""".stripMargin)
      for (idx <- 0 until kmeansKernels)
      println(f"   ${means(idx).toString}%20s ==> ${newMeans(idx).toString}%20s  " +
              f"  distance: ${euclideanDistance(means(idx), newMeans(idx))}%8.0f")
    }

    if (converged(distance))
      newMeans
    else if (iter < kmeansMaxIterations)
      kmeans(newMeans, vectors, iter + 1, debug)
    else {
      if (debug) {
        println("Reached max iterations!")
      }
      newMeans
    }
  }




  //
  //
  //  Kmeans utilities:
  //
  //

  /** Decide whether the kmeans clustering converged */
  def converged(distance: Double) =
    distance < kmeansEta


  /** Return the euclidean distance between two points */
  def euclideanDistance(v1: (Int, Int), v2: (Int, Int)): Double = {
    val part1 = (v1._1 - v2._1).toDouble * (v1._1 - v2._1)
    val part2 = (v1._2 - v2._2).toDouble * (v1._2 - v2._2)
    part1 + part2
  }

  /** Return the euclidean distance between two points */
  def euclideanDistance(a1: Array[(Int, Int)], a2: Array[(Int, Int)]): Double = {
    assert(a1.length == a2.length)
    var sum = 0d
    var idx = 0
    while(idx < a1.length) {
      sum += euclideanDistance(a1(idx), a2(idx))
      idx += 1
    }
    sum
  }

  /** Return the closest point */
  def findClosest(p: (Int, Int), centers: Array[(Int, Int)]): Int = {
    var bestIndex = 0
    var closest = Double.PositiveInfinity
    for (i <- 0 until centers.length) {
      val tempDist = euclideanDistance(p, centers(i))
      if (tempDist < closest) {
        closest = tempDist
        bestIndex = i
      }
    }
    bestIndex
  }


  /** Average the vectors */
  def averageVectors(ps: Iterable[(Int, Int)]): (Int, Int) = {
    val iter = ps.iterator
    var count = 0
    var comp1: Long = 0
    var comp2: Long = 0
    while (iter.hasNext) {
      val item = iter.next
      comp1 += item._1
      comp2 += item._2
      count += 1
    }
    ((comp1 / count).toInt, (comp2 / count).toInt)
  }




  //
  //
  //  Displaying results:
  //
  //
  /** Compute the median = mean of 2 numbers in the middle of seq (after sort) if the seq size is even
   *  Otherwise, it is either of the 2 numbers
   * @author buidu
   * @param vec A sequence of Int
   * @return median
   */
  def computeMedian(vec: Seq[Int]): Int ={
    val (lower, upper) = vec
      .sortWith(_<_) //sort the sequence from lower to upper
      .splitAt(vec.size/2) //split the sequence by vec.size/2. The seq becomes a (lower, upper)
    //if length of seq %2, median = mean of 2 numbers at the middle sequence
    //otherwise, it is the smallest in the upper seq (or the biggest in the lower seq)
    if (vec.size % 2 == 0) (lower.last + upper.head)/2 else upper.head
  }
  
  def clusterResults(means: Array[(Int, Int)], vectors: RDD[(LangIndex, HighScore)]): Array[(String, Double, Int, Int)] = {
    val closest = vectors.map(p => (findClosest(p, means), p)) //closest = [(bestIndex, vector Posting)]
    val closestGrouped = closest.groupByKey() 

    val median = closestGrouped.mapValues { vs =>
      val temp = vs.groupBy(_._1).mapValues(_.size).toList.sortBy(_._2)
      val langLabel: String   = langs(temp.head._1/langSpread) // most common language in the cluster
      val langPercent: Double = (temp.head._2/vs.size)*100 // percent of the questions in the most common language
      val clusterSize: Int    = vs.size
      val medianScore: Int    = computeMedian(vs.map(_._2).toList) //map.(_._2) returns the second tuple only

      (langLabel, langPercent, clusterSize, medianScore)
    }

    median.collect().map(_._2).sortBy(_._4)
  }

  def printResults(results: Array[(String, Double, Int, Int)]): Unit = {
    println("Resulting clusters:")
    println("  Score  Dominant language (%percent)  Questions")
    println("================================================")
    for ((lang, percent, size, score) <- results)
      println(f"${score}%7d  ${lang}%-17s (${percent}%-5.1f%%)      ${size}%7d")
  }
}
