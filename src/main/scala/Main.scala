import dsl.Parser

object Main {
  def main(args: Array[String]): Unit = {
    val parser = if(args.length == 0) new Parser() else new Parser(Some(args(0)), Some(args(0).replace(".txt", "") + "Result.txt"))
    parser.parse()
  }
}
