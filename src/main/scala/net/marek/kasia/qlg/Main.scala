package net.marek.kasia.qlg


object Main {
  def main(args: Array[String]): Unit = {
    val parser = if(args.length == 0) new InputOutputParser() else new InputOutputParser(Some(args(0)), Some(args(0).replace(".txt", "") + "Result"))
    parser.parse()
  }
}
