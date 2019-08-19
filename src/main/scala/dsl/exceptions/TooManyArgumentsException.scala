package dsl.exceptions

class TooManyArgumentsException(gate: String, numOfArgs: Int) extends RuntimeException{
  override def getMessage: String = "Too many arguments. Operator " + gate + " takes " + numOfArgs + " arguments."
}
