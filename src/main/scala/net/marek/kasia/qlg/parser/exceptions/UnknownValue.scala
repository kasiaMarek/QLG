package net.marek.kasia.qlg.parser.exceptions

class UnknownValue(param: String) extends RuntimeException {
  override def getMessage: String = "Unknown value " + param + " variables should be have value 1 or 0"

}
