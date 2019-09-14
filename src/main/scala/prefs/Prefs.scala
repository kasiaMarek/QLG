package prefs
import quantum.QNum

object Prefs {
  type DSLVariable = String
  implicit val semiring = QNum
}
