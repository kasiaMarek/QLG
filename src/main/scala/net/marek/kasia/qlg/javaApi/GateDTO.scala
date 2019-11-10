package net.marek.kasia.qlg.javaApi
import scala.collection.JavaConverters._

trait GateDTO {
  def getType: String
}

class FrdGateDTO(controls: List[Int], indices: List[Int]) extends GateDTO {
  override def getType: String = "FRD"
  def getControls: java.util.List[Integer] = controls.map(e => new Integer(e)).asJava
  def getIndices: java.util.List[Integer] = indices.map(e => new Integer(e)).asJava
}

class HdmGateDTO(index: Int) extends GateDTO {
  override def getType: String = "HDM"
  def getIndex: Integer = index
}

class TflGateDTO(controls: List[Int], index: Int) extends GateDTO {
  override def getType: String = "TFL"
  def getControls: java.util.List[Integer] = controls.map(e => new Integer(e)).asJava
  def getIndex: Integer = index
}
