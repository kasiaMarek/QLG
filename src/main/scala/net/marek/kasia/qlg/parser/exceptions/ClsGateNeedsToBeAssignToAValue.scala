package net.marek.kasia.qlg.parser.exceptions

class ClsGateNeedsToBeAssignToAValue extends RuntimeException {
  override def getMessage: String = "classical gate needs to be assigned to a variable"

}
