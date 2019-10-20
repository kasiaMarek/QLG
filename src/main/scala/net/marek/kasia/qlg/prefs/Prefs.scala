package net.marek.kasia.qlg.prefs
import net.marek.kasia.qlg.quantum.QNum

object Prefs {
  type DSLVariable = String
  implicit val semiring = QNum
}
