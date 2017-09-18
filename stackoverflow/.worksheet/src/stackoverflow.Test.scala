package stackoverflow

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

object Test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(223); 
  println("Welcome to the Scala worksheet");$skip(71); 
  
  val words = Array("one", "two", "two", "three", "three", "three");System.out.println("""words  : Array[String] = """ + $show(words ));$skip(93); 
	
	lazy val conf: SparkConf = new SparkConf().setMaster("local").setAppName("StackOverflow");System.out.println("""conf: => org.apache.spark.SparkConf""");$skip(53); 
  lazy val sc: SparkContext = new SparkContext(conf);System.out.println("""sc: => org.apache.spark.SparkContext""");$skip(60); 
	val wordRDD = sc.parallelize(words).map(word => (word, 1));System.out.println("""wordRDD  : org.apache.spark.rdd.RDD[(String, Int)] = """ + $show(wordRDD ));$skip(78); 
	
	val wordCountsreducebykey =
		wordRDD
			.reduceByKey(_ + _)
			.collect();System.out.println("""wordCountsreducebykey  : Array[(String, Int)] = """ + $show(wordCountsreducebykey ));$skip(87); 

	val wordCountsGroupbykey = wordRDD.groupByKey().map(t => (t._1, t._2.sum)).collect();System.out.println("""wordCountsGroupbykey  : Array[(String, Int)] = """ + $show(wordCountsGroupbykey ))}
}
