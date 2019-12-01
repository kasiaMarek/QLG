package net.marek.kasia.qlg

class NoArgsProvidedException extends RuntimeException {
  override def getMessage: String = "An argument with file name should be provided."
}
