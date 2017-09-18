package byKey

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

object Key {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(214); 
  println("Welcome to the Scala worksheet");$skip(71); 
  
  val words = Array("one", "two", "two", "three", "three", "three")
	
	@transient;System.out.println("""words  : Array[String] = """ + $show(words ));$skip(104);  lazy val conf: SparkConf = new SparkConf().setMaster("local").setAppName("StackOverflow")
  @transient;System.out.println("""conf  : <error> = <lazy>""");$skip(64);  lazy val sc: SparkContext = new SparkContext(conf);System.out.println("""sc  : <error> = <lazy>""");$skip(60); 
	val wordRDD = sc.parallelize(words).map(word => (word, 1));System.out.println("""wordRDD  : <error> = """ + $show(wordRDD ))}
}
