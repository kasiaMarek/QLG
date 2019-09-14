package prefs

import breeze.storage.Zero
import quantum.{QNum, QNumSemiring}

object Prefs {
  type DSLVariable = String
  implicit val semiring = new QNumSemiring()
  implicit val zeroQ : Zero[QNum] = new Zero[QNum] {
    override def zero: QNum = QNum.zero
  }
}
