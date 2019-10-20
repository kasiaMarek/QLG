package net.marek.kasia.qlg.parser.exceptions

class NotEnoughArgumentsException(gate: String, numOfArgs: Int) extends RuntimeException {
  override def getMessage: String = "Not enough arguments. Operator " + gate + " takes " + numOfArgs + " arguments."
}
