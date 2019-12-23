package net.marek.kasia.qlg.parser.exceptions

class VariableNotDefinedException(arg: String) extends RuntimeException {
  override def getMessage: String = "variable \'" + arg + "\' hasn't been declared in this scope"

}
