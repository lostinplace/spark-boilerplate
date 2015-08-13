import org.apache.spark.sql._
import org.apache.spark.{SparkConf, SparkContext}

object Main {

  def main(args: Array[String]) {

    val argString = args.deep.mkString(",")
    val config: Config = ArgParser.parse(args)

    println(s"********************************\n" +
      s"${argString}\n" +
      s"\n${config.fileName}\n" +
      s"\n${config.outFileName}\n" +
      s"********************************")
    if(config == null) return;

    val conf = new SparkConf().setAppName("LogData-Demo")
    val sc = new SparkContext(conf)

    val sqlContext = new org.apache.spark.sql.SQLContext(sc)

    val logFileDF = sqlContext.jsonFile(config.fileName)

    val sampleDF = logFileDF.sample(false,0.001);
    val regApiLines = logFileDF.filter("service = 'RegistrationApi'")

    val options = Map(("header", "true"), ("path",config.outFileName))

    regApiLines.save("com.databricks.spark.csv", SaveMode.Overwrite, options  )
  }

}
