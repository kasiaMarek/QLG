package net.marek.kasia.qlg.parser.exceptions

class ClsGateNeedsToBeAssignToAValue extends RuntimeException {
  override def getMessage: String = "Classical gate needs to be assigned to a variable"

}
