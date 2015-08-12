name := "logdata-demo-07-22-2015"

version := "1.0"

scalaVersion := "2.10.4"

resolvers ++= Seq()

val sparkVersion = "1.3.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "com.databricks" %% "spark-csv" % "1.1.0",
  "com.github.scopt" %% "scopt" % "3.3.0",
  "org.skife.com.typesafe.config" % "typesafe-config" % "0.3.0"
)

assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}

val projectName = scala.io.Source.fromFile(".projectname")
val lines = try projectName.mkString finally projectName.close()
val test = println(lines)


assemblyOutputPath in assembly := new File(s"output/${lines}.jar")