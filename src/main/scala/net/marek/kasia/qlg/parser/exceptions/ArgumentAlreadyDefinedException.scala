package net.marek.kasia.qlg.parser.exceptions

class ArgumentAlreadyDefinedException(val arg: String) extends RuntimeException {
  override def getMessage: String = "variable \'" + arg + "\' has already been defined in this scope."
}
