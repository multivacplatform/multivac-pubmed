package dataframe_helpers

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{rank, row_number}


object WindowHelper {

  def findLatestUpdatedArticles(df: DataFrame, spark: SparkSession): DataFrame = {
    import spark.implicits._

    val w = Window.partitionBy("PMID").orderBy($"Article.Journal.JournalIssue.PubDate.Year".desc)

    val uniqueArticles = df
      .withColumn("rank", rank().over(w))
      .withColumn("rn", row_number.over(w))
      .where($"rn" === 1)
      .drop("rn")
      .drop("rank")

    uniqueArticles

  }

}