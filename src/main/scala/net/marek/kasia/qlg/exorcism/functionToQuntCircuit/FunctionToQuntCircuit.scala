package net.marek.kasia.qlg.exorcism.functionToQuntCircuit

import net.marek.kasia.qlg.exorcism.{ESOP, Term}
import net.marek.kasia.qlg.parser.{NotQ, QGate, Tfl, Variable}

object FunctionToQuntCircuit {

  def ESOPToGates(esop: ESOP, outVariable: Variable): List[QGate] = {
    val (set, gates) = esop.terms.foldLeft(
      (Set(): Set[Variable], List(): List[QGate])
    )(
      (acc, term) => termToGates(term, acc._1, acc._2, outVariable)
    )
    gates ::: set.toList.map(NotQ)
  }

  private def termToGates(term: Term, negativePolarization: Set[Variable], list: List[QGate], outVariable: Variable): (Set[Variable], List[QGate]) = {
    val (set, notGates) = term.variables.foldLeft(Set(): Set[Variable], list)((acc, v) => {
      if (negativePolarization(v._1)) {
        if (v._2 == 0) {
          (acc._1, NotQ(v._1) :: acc._2)
        } else {
          (acc._1 + v._1, acc._2)
        }
      } else {
        if (v._2 == 0) {
          (acc._1, acc._2)
        } else {
          (acc._1 + v._1, NotQ(v._1) :: acc._2)
        }
      }
    })
    (set, notGates :+ Tfl(term.variables.map(_._1), outVariable))
  }

}