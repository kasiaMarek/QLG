package net.marek.kasia.qlg

import net.marek.kasia.qlg.parser.{CQLL, Expression, ParseCQLL}
import net.marek.kasia.qlg.quantum.CircuitExecutor

class FromStringParser(input: String) extends CQLL {
  lazy val circuitExecutor : CircuitExecutor = parse()

  def parse(): CircuitExecutor = {
    try {
      val parsed: ParseResult[List[Expression]] = parseAll(program, input)
      ParseCQLL.parseToCircuitExec(parsed.get)
    } catch {
      case e : RuntimeException => e.printStackTrace()
      null
    }
  }

}
