package net.marek.kasia.qlg.javaApi
import net.marek.kasia.qlg.quantum.qubits.Qubits

import scala.collection.JavaConverters._

class ResultDTO(result: List[Int]) {
  def getResult(): java.util.List[Integer] = result.map(e => new Integer(e)).asJava
}

object ResultDTO {
  def makeResultDTO(qbits: Qubits): ResultDTO = new ResultDTO(qbits.measureAllReturnAsListOfBits())
}
