package net.marek.kasia.qlg.parser.exceptions

class NotEnoughArgumentsException(gate: String, numOfArgs: Int) extends RuntimeException {
  override def getMessage: String = "not enough arguments, operator \'" + gate + "\' takes: " + numOfArgs + " arguments"
}
