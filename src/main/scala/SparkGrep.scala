import org.apache.spark.sql._
import org.apache.spark.{SparkConf, SparkContext}

object SparkGrep {

  def main(args: Array[String]) {

    val argString = args.deep.mkString(",")
    val config = argsParser(args)

    val hdfssource = config("hdfssource")
    val filename = config("filename")
    val outfilename = config("outfilename")

    println(s"********************************" +
      s"${argString}\n" +
      s"\n${config("filename")}\n" +
      s"\n${config("outfilename")}\n" +
      s"\n${config("hdfssource")}\n" +
      s"********************************")
    if(config == null) return;

    val conf = new SparkConf().setAppName("LogData-Demo")
    val sc = new SparkContext(conf)
    // Create a SQLContext (sc is an existing SparkContext)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)



    val logFileDF = sqlContext.jsonFile(s"${hdfssource}${filename}")



//    val regApiLines = sqlContext.sql("SELECT * FROM logEntries WHERE service = 'RegistrationAPI'")


//    regApiLines.repartition(1).save("hdfs://192.168.16.105/user/admin/jobout.csv", "com.databricks.spark.csv")
    val sampleDF = logFileDF.sample(false,0.001);
    val regApiLines = logFileDF.filter("service = 'RegistrationAPI'")


    val options = Map(("header", "true"), ("path",s"${hdfssource}${outfilename}"))

    regApiLines.save("com.databricks.spark.csv", SaveMode.Overwrite, options  )
  }


  def argsParser(args: Array[String]): collection.mutable.Map[String, String] = {
    println(scala.tools.nsc.Properties.versionString)

    println("-------------Attach debugger now!--------------")
//    Thread.sleep(8000)


    val argsMap =  collection.mutable.Map[String,String](
      "hdfssource" -> "hdfs://192.168.16.105",
      "filename" -> "/data/esprod/201.json",
      "outfilename" -> "/data/jobout"
    )

    args.foreach( v => {
      val argRegEx = "--([^=]*)=(.*)".r

      val p4Matches = v match {
        case argRegEx(argname, argval) => argsMap(argname) = argval;
        case _  => false
      }
    })

    return argsMap
  }
}
