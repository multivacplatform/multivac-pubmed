import com.typesafe.config.ConfigFactory
import dataframe_helpers.{InputHelper, ParquetHelper, WindowHelper}
import org.apache.spark.sql.{DataFrame, SparkSession}
import spark_helpers.SparkSessionHelper

object Main {
  def main(args: Array[String]) {
    val spark = SparkSessionHelper.buildSession()
    val demoFilePath = ConfigFactory.load().getString("spark.conf.inputPath.value")

    val df = InputHelper.create_dataframe(
      demoFilePath,
      spark
    )
    println("Total number of articles: ", df.count())
    df.show()

    val uniquePubMedDF = WindowHelper.findLatestUpdatedArticles(df, spark)

    println("Total number of unique articles: ", uniquePubMedDF.count())

    println("========================== \n")
    println("========================== \n")
    println("========================== \n")
    println("Before saving as a Parquet: \n")
    println("Number of rows in DataFrame: \n")
    println(uniquePubMedDF.count())

    println("Displaying 10 rows of DataFrame: \n")
    uniquePubMedDF.show(10)

    val parguetPath = ConfigFactory.load().getString("spark.conf.outputPath.value")

    ParquetHelper.saveDataFrameAsParquet(uniquePubMedDF, spark, parguetPath)
  }

}
