package dataframe_helpers

import org.apache.spark.sql.{DataFrame, SparkSession}

object ParquetHelper {

  def saveDataFrameAsParquet(
                              pubmedDF: DataFrame,
                              spark: SparkSession,
                              outputPath: String
                            ): Unit ={
    import spark.implicits._

    println("daily partition path ", outputPath)
    pubmedDF
      .filter("Article.Journal.JournalIssue.PubDate.Year IS NOT null")
      .withColumn("Year", $"Article.Journal.JournalIssue.PubDate.Year")
      .repartition($"Year")
      .write
      .partitionBy("Year")
      .mode("overwrite")
      .option("compression","snappy")
      .parquet(outputPath)

  }

  def readParquetAsDataFrame(
                              demoFilePath: String,
                              spark: SparkSession
                            ): DataFrame ={
    spark.read.parquet(demoFilePath)
  }
}