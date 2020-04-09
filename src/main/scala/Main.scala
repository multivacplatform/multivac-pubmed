import com.typesafe.config.ConfigFactory
import dataframe_helpers.{InputHelper, ParquetHelper, WindowHelper}
import spark_helpers.SparkSessionHelper

object Main {
  def main(args: Array[String]) {
    val spark = SparkSessionHelper.buildSession()
    val demoFilePath = ConfigFactory.load().getString("spark.conf.inputPath.value")

    val df = InputHelper.create_dataframe(
      demoFilePath,
      spark
    )
    val uniquePubMedDF = WindowHelper.findLatestUpdatedArticles(df, spark)
    val parguetPath = ConfigFactory.load().getString("spark.conf.outputPath.value")

    ParquetHelper.saveDataFrameAsParquet(uniquePubMedDF, spark, parguetPath)
  }

}
