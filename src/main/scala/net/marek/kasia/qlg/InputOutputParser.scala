package net.marek.kasia.qlg

import java.io._

import net.marek.kasia.qlg.parser.{CQLL, Expression, ParseCQLL}

class InputOutputParser(inFile: Option[String] = Option.empty, outFile: Option[String] = Option.empty) extends CQLL {
  val reader = new BufferedReader (if (inFile.isEmpty) new InputStreamReader(System.in) else new FileReader(inFile.get))
  val writer = new BufferedWriter(if (outFile.isEmpty) new OutputStreamWriter(System.out) else new FileWriter(outFile.get))

  def parse(): Unit = {
    try {
      val parsed: ParseResult[List[Expression]] = parseAll(program, reader)
      val p = ParseCQLL.parseToCircuitExec(parsed.get)
      writer.write(p.toString())
      writer.flush()
    } catch {
      case e : RuntimeException => e.printStackTrace()
    } finally {
      writer.close()
    }
  }

}
