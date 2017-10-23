package observatory

import java.time.LocalDate

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions.{udf, _}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * 1st milestone: data extraction
  */
object Extraction {
  
  private lazy val spark = SparkSession.builder.master("local[4]").getOrCreate()
  private lazy val combinedId = udf((stn: String, wban: String) => (stn, wban))

  /** This method returns all the temperature records converted to Celsius along with their date and location
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] = {
    val stationsDF = spark.read.csv(Extraction.getClass.getResource(stationsFile).getPath)
    val temperatureDF = spark.read.csv(Extraction.getClass.getResource(temperaturesFile).getPath)
    
    locateTemperatures(year, stationsDF, temperatureDF)
  }
  
  /**
   * @param year             Year number
   * @param stations		     DF read from stations.csv
   * @param temperature		   DF read from temperatures resource file
   * @return A sequence containing triplets (date, location, temperature)
   */
  def locateTemperatures(year: Year, stations: DataFrame, temperature: DataFrame): Iterable[(LocalDate, Location, Temperature)] = {
    
  }
  
  def createLocalTemperatures(year: Year, stationsDF: DataFrame, temperatureDF: DataFrame): Dataset[LocalTemperature] = {
    
    val stations_ = stationsDF
      .toDF("stn", "wban", "lat", "long")
      .na.fill("", Seq("stn", "wban"))
      .withColumn("id", combinedId(col("stn"), col("wban")))
    
    val temperature_ = temperatureDF
      .toDF("stn", "wban", "month", "day", "temp")
      .na.fill("", Seq("stn", "wban"))
      .withColumn("id", combinedId(col("stn"), col("wban")))
      
    val joined = temperature_.join(stations_, "id")
    
    joined
      .select("lat", "long", "month", "day", "temp")
      .na.drop()
      .map{row => LocalTemperature(
          year,
          Integer.parseInt(row.getAs[String]("month")),
          Integer.parseInt(row.getAs[String]("day")),
          Double.parseDouble(row.getAs[String]("lat")),
          Double.parseDouble(row.getAs[String]("long")),
          toCelsius(Double.parseDouble(row.getAs[String]("temp")))
          )}
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
    ???
  }
  
  private lazy val bd32 = BigDecimal(32)
  private lazy val bd1dot8 = BigDecimal(1.8)
  
  def toCelsius(fahrenheit: Double): Double ={
    ((BigDecimal(fahrenheit).setScale(10, BigDecimal.RoundingMode.HALF_EVEN) - bd32) / bd1dot8)
      .setScale(4, BigDecimal.RoundingMode.HALF_EVEN)
      .rounded
      .toDouble
  }
  
  case class ParseOp[T](op: String => T)
  implicit val popDouble = ParseOp[Double](_.toDouble)
  implicit val popInt = ParseOp[Int](_.toInt)
  // etc.
  def parse[T: ParseOp](s: String) = try { Some(implicitly[ParseOp[T]].op(s)) } 
                                   catch {case _ => None}


}
