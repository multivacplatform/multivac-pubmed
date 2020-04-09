name := "multivac-pubmed"
organization := "fr.iscpif.multivac"

version := "1.0.0"

scalaVersion := "2.11.12"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

licenses := Seq("Apache-2.0" -> url("https://opensource.org/licenses/Apache-2.0"))

import com.typesafe.sbt.packager.archetypes.JavaAppPackaging

enablePlugins(JavaServerAppPackaging)
enablePlugins(JavaAppPackaging)


libraryDependencies ++= {
  val sparkVer = "2.4.4"
  val sparkNLP = "2.4.5"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVer % "provided" withSources(),
    "org.apache.spark" %% "spark-sql" % sparkVer % "provided" withSources(),
    "org.apache.spark" %% "spark-streaming" % sparkVer % "provided" withSources(),
    "org.apache.spark" %% "spark-mllib" %sparkVer % "provided" withSources(),
    "org.apache.spark" %% "spark-hive" % sparkVer % "provided" withSources(),
    "org.apache.spark" %% "spark-graphx" % sparkVer % "provided" withSources(),
    "org.apache.spark" %% "spark-yarn" % sparkVer % "provided" withSources(),
    "com.johnsnowlabs.nlp" %% "spark-nlp" % sparkNLP,
    "com.databricks" %% "spark-xml" % "0.9.0",
    "com.typesafe" % "config" % "1.3.3"
  )
}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" => MergeStrategy.concat
  case x => MergeStrategy.first
}

assemblyExcludedJars in assembly := {
  val cp = (fullClasspath in assembly).value
  cp filter {
    j => {
      j.data.getName.startsWith("spark-core") ||
        j.data.getName.startsWith("spark-sql") ||
        j.data.getName.startsWith("spark-hive") ||
        j.data.getName.startsWith("spark-mllib") ||
        j.data.getName.startsWith("spark-graphx") ||
        j.data.getName.startsWith("spark-yarn") ||
        j.data.getName.startsWith("spark-streaming") ||
        j.data.getName.startsWith("hadoop")
    }
  }
}