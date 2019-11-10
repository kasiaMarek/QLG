package net.marek.kasia.qlg.parser.exceptions

class UnknownCommand(param: String) extends RuntimeException {
  override def getMessage: String = "Unknown command: " + param

}
