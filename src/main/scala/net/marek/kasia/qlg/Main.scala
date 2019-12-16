package net.marek.kasia.qlg

object Main {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      throw new NoArgsProvidedException()
    }
    val outputFile = if (args.length == 1) Option.empty else Some(args(1))
    new InputOutputParser(Some(args(0)), outputFile).parse()
  }
}
