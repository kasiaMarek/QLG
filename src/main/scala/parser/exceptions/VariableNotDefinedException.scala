package parser.exceptions

class VariableNotDefinedException(arg: String) extends RuntimeException {
  override def getMessage: String = "Variable " + arg + " hasn't been declared in this scope"

}
