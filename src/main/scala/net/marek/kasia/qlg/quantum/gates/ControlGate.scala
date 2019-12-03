package net.marek.kasia.qlg.quantum.gates

import breeze.linalg.CSCMatrix
import net.marek.kasia.qlg.quantum.QNum
import net.marek.kasia.qlg.prefs.Prefs._

abstract class ControlGate extends CircuitGate {

  override def getExpandedGate: Int => AnyGate = (size: Int) => AnyGate(getGate(size))

  def getGate(i: Int): CSCMatrix[QNum]

  def getGate(size: Int, genPair: Int => (Int, Int), controls: List[Int]): CSCMatrix[QNum] = {
    val mappedControls = controls.map(size - _ - 1).sorted
    val builder = new CSCMatrix.Builder[QNum](rows = 1 << size, cols = 1 << size)
    for(i <- 0 until (1 << size)) {
      if(checkIndex(i, mappedControls)) {
        val p = genPair(i)
        builder.add(p._1, p._2, QNum.one)
      } else {
        builder.add(i, i, QNum.one)
      }
    }
    builder.result
  }

  private def checkIndex(i:Int, controls: List[Int]): Boolean = controls.forall(checkIndex(i, _))

  private def checkIndex(i:Int, control: Int): Boolean = (i >> control) % 2 == 1

}
