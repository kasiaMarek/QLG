package parser.exceptions

class ArgumentAlreadyDefinedException(val arg: String) extends RuntimeException {
  override def getMessage: String = "Variable " + arg + " has already been defined in this scope."
}
