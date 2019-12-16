package net.marek.kasia.qlg.parser.exceptions

class TooManyArgumentsException(gate: String, numOfArgs: Int) extends RuntimeException{
  override def getMessage: String = "too many arguments, operator \'" + gate + "\' takes: " + numOfArgs + " arguments"
}
