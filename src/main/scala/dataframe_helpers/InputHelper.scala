package dataframe_helpers

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._


object InputHelper {

  def create_dataframe(
                  filePath: String,
                  spark: SparkSession
                ): (DataFrame) ={

    println(s"file path: $filePath")

    //read the .gz files from `data/pubmed/updatefiles/`
    val rawDF = spark.read.format("xml")
      .option("rowTag","MedlineCitation")
      .option("excludeAttribute","true")
      .option("inferSchema", value = true)
      .option("mode", "DROPMALFORMED")
      .load(filePath)

    rawDF
  }
}
