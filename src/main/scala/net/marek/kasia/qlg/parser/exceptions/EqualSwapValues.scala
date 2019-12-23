package net.marek.kasia.qlg.parser.exceptions

class EqualSwapValues(variable: String) extends RuntimeException{
  override def getMessage: String = "variable \'" + variable + "\' can not be used as both swap values"
}
