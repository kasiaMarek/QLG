package net.marek.kasia.qlg.parser.exceptions

class InputValueUsedAsControl(variableName: String) extends RuntimeException {
  override def getMessage: String = "variable \'" + variableName + "\' can not be used as input and control bit simultaneously"
}
