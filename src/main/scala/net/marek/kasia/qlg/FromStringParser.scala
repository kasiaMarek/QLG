package net.marek.kasia.qlg

import net.marek.kasia.qlg.parser.{CQLL, Expression, ParseCQLL}
import net.marek.kasia.qlg.quantum.CircuitExecutor

class FromStringParser(input: String) extends CQLL {

  def parse(): CircuitExecutor = {
      val parsed: ParseResult[List[Expression]] = parseAll(program, input)
      ParseCQLL.parseToCircuitExec(parsed.get)
  }

}
