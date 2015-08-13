import java.io.File


object ArgParser {

  val parser = new scopt.OptionParser[Config]("scopt") {
    head("scopt", "3.x")
    opt[String]('i', "inpath") required()  valueName("<inpath>") action {
      (x, c) => c.copy(fileName = x) } text("required file path to process")
    opt[String]('o', "outpath") required()  valueName("<outpath>") action {
      (x, c) => c.copy(outFileName = x) } text("required path to output")
  }

  def parse (args: Array[String]):Config = {

    parser.parse(args, Config()) match {
      case Some(config) =>
        return config

      case None =>
        return null
    }
  }
}
