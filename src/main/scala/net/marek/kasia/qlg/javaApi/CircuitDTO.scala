package net.marek.kasia.qlg.javaApi
import scala.collection.JavaConverters._
import net.marek.kasia.qlg.quantum.CircuitExecutor
import net.marek.kasia.qlg.quantum.gates.{CircuitGate, FredkinGate, HadamardGate, NotGate, ToffoliGate}

class CircuitDTO(size: Int, input: List[Int], gates: List[GateDTO]) {
  def getSize(): Integer = size
  def getInput(): java.util.List[Integer] = input.map(e => new Integer(e)).asJava
  def getGates(): java.util.List[GateDTO] = gates.asJava

}

object CircuitDTO {
  def makeCircuitDTO(circuitExecutor: CircuitExecutor): CircuitDTO = new CircuitDTO(circuitExecutor.circuit.size, circuitExecutor.bits, circuitExecutor.circuit.gates.map(convertGate))

  def convertGate(circuitGate: CircuitGate): GateDTO = {
    circuitGate match {
      case e : HadamardGate => new HdmGateDTO(e.index)
      case e : NotGate => new TflGateDTO(List(), e.index)
      case e : ToffoliGate => new TflGateDTO(e.control, e.index)
      case e : FredkinGate => new FrdGateDTO(e.control, List(e.swap._1, e.swap._2))
    }
  }
}
