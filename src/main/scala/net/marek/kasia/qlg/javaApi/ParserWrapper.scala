package net.marek.kasia.qlg.javaApi

import net.marek.kasia.qlg.FromStringParser
import net.marek.kasia.qlg.quantum.CircuitExecutor
import net.marek.kasia.qlg.quantum.qubits.Qubits

class ParserWrapper(input: String) {
  var circuitExecutor: CircuitExecutor = null
  var isSuccess: java.lang.Boolean = true
  var exception: RuntimeException = null
  var result: Qubits = null

  def parse() {
    try {
      circuitExecutor = new FromStringParser(input).parse()
      isSuccess = true
    } catch {
      case e : RuntimeException => {
        isSuccess = false
        exception = e
      }
    }
  }

  def exec() = {
    if(circuitExecutor == null) {
      parse()
    }
    if(circuitExecutor != null) {
      result = circuitExecutor.getResult
    } else {
      throw exception
    }
  }

  def getCircuitDTO(): CircuitDTO = CircuitDTO.makeCircuitDTO(circuitExecutor)

  def getResultDTO(): ResultDTO = ResultDTO.makeResultDTO(result)

  def getErrorMsg(): String = exception.getMessage

}
